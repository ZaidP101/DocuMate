package research.project.documate.backend.Backend.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;
import research.project.documate.backend.Backend.DTOs.Readme.ReadmeDiffDTO;
import research.project.documate.backend.Backend.DTOs.Readme.ReadmeGenerationResultDTO;
import research.project.documate.backend.Backend.DTOs.Readme.ReadmePushDTO;
import research.project.documate.backend.Backend.DTOs.Readme.RegenerateRequestDTO;
import research.project.documate.backend.Backend.Entity.GitCredentials;
import research.project.documate.backend.Backend.Entity.ProjectEntity;
import research.project.documate.backend.Backend.Entity.ReadmeFileEntity;
import research.project.documate.backend.Backend.Repository.ProjectRepository;
import research.project.documate.backend.Backend.Repository.ReadmeFileRepository;
import research.project.documate.backend.Backend.Service.AiService.ReadmeAiService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ReadmeService {
    private final ReadmeAiService readmeAiService;
    private final ReadmeFileRepository readmeFileRepository;
    private final ProjectRepository projectRepository;
    private final GitCredentialsService gitCredentialsService;

    public ReadmeDiffDTO getReadmeDiff(Long projectId) {
        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        ReadmeFileEntity currentReadme = readmeFileRepository.findByProjectId(projectId)
                .orElseThrow(() -> new RuntimeException("No README found"));
        log.info("CURRENT README - ID: {}, Content length: {}",
                currentReadme.getId(),
                currentReadme.getContent() != null ? currentReadme.getContent().length() : "null");

        ReadmeFileEntity newReadme = getNewlyGeneratedReadme(project, currentReadme.getId()); // the newly generated README from git push
        log.info("NEW README - ID: {}, Content length: {}",
                newReadme.getId(),
                newReadme.getContent() != null ? newReadme.getContent().length() : "null");

        return ReadmeDiffDTO.builder()
                .oldContent(currentReadme.getContent())
                .newContent(newReadme.getContent())
                .changeSummary(newReadme.getChangeSummary())
                .projectId(projectId)
                .build();
    }

    public void approveAndPushReadme(Long projectId, ReadmePushDTO pushRequest) {
        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        ReadmeFileEntity pendingReadme = readmeFileRepository.findByProjectIdAndCommitHashStartingWith(projectId, "PENDING_")
                .orElseThrow(() -> new RuntimeException("No pending README found"));

        pendingReadme.setContent(pushRequest.getContent());
        readmeFileRepository.save(pendingReadme);

        writeReadmeToFile(project, pushRequest.getContent()); // Write to actual README.md file in project folder
        executeGitCommandsWithJGit(project); // Execute Git commands

        pendingReadme.setCommitHash("APPROVED_" + System.currentTimeMillis()); // Mark as approved
        readmeFileRepository.save(pendingReadme);
        log.info("README approved and pushed successfully for project: {}", project.getTitle());
    }

    private void executeGitCommandsWithJGit(ProjectEntity project) {
        try{
            GitCredentials credentials = gitCredentialsService.getGitCredentials();
            String decryptedToken = gitCredentialsService.getDecryptedToken();

            FileRepositoryBuilder builder = new FileRepositoryBuilder();
            Repository repository = builder.setGitDir(new File(project.getLocalPath(), ".git"))
                    .readEnvironment()
                    .findGitDir()
                    .build();

            Git git = new Git(repository);
            git.add().addFilepattern(".").call(); // add
            git.commit()                            // commit
                    .setMessage("Readme Updated -automated")
                    .setAuthor(credentials.getUsername(), credentials.getEmail())
                    .call();
            git.push()
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(
                            credentials.getUsername(), decryptedToken))
                    .call(); // push

            git.close();
            log.info("JGit Completed Process Successfully ");
        } catch (Exception e) {
            log.error("Error executing JGit commands", e);
            throw new RuntimeException("JGit operation failed",e);
        }
    }

    private void writeReadmeToFile(ProjectEntity project, String content) {
        try {
            Path readmePath = Paths.get(project.getLocalPath(), "README.md");
            Files.write(readmePath, content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            log.info("README.md file updated for project: {}", project.getTitle());
        } catch (IOException e) {
            throw new RuntimeException("Failed to write README.md file for project: " + project.getTitle(), e);
        }
    }

    private ReadmeFileEntity getNewlyGeneratedReadme(ProjectEntity project, Long currentReadmeId) {
        List<ReadmeFileEntity> allReadmes = readmeFileRepository.findByProjectOrderByCreatedAtDesc(project);

        log.info("Available READMEs for project {}: {}", project.getId(),
                allReadmes.stream()
                        .map(r -> "ID: " + r.getId() + ", Commit: " + r.getCommitHash() + ", Created: " + r.getCreatedAt())
                        .collect(Collectors.toList()));

        Optional<ReadmeFileEntity> pendingReadme = allReadmes.stream() // Look for PENDING_* commit hashes (newly generated READMEs)
                .filter(r -> r.getCommitHash().startsWith("PENDING_"))
                .findFirst();

        if (pendingReadme.isPresent()) {
            return pendingReadme.get();
        }
        // Fallback: find most recent that's not current
        return allReadmes.stream()
                .filter(r -> !r.getId().equals(currentReadmeId))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("No newly generated README found. Current README ID: {}", currentReadmeId);
                    return new RuntimeException("No newly generated README found. Please make a git push first.");
                });
    }

    public ReadmeDiffDTO regenerateWithPrompt(Long projectId, RegenerateRequestDTO request) {
        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        String enhancedPrompt = request.getCurrentContent() + // Send to Gemini with user prompt
                "\n\nUser requested changes: " + request.getUserPrompt();

        ReadmeGenerationResultDTO newResult = readmeAiService.regenerateWithPrompt(
                project, enhancedPrompt);

        return ReadmeDiffDTO.builder() // Return updated diff for frontend
                .oldContent(request.getCurrentContent())
                .newContent(newResult.getContent())
                .changeSummary("Regenerated based on user request: " + request.getUserPrompt())
                .projectId(projectId)
                .build();
    }
}
