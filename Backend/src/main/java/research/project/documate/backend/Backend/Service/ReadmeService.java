package research.project.documate.backend.Backend.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.stereotype.Service;
import research.project.documate.backend.Backend.DTOs.ReadmeDiffDTO;
import research.project.documate.backend.Backend.DTOs.ReadmeGenerationResultDTO;
import research.project.documate.backend.Backend.DTOs.ReadmePushDTO;
import research.project.documate.backend.Backend.DTOs.RegenerateRequestDTO;
import research.project.documate.backend.Backend.Entity.ProjectEntity;
import research.project.documate.backend.Backend.Entity.ReadmeFileEntity;
import research.project.documate.backend.Backend.Repository.ProjectRepository;
import research.project.documate.backend.Backend.Repository.ReadmeFileRepository;

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

    public ReadmeDiffDTO getReadmeDiff(Long projectId) {
        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        ReadmeFileEntity currentReadme = readmeFileRepository.findLatestByProjectId(projectId)
                .orElseThrow(() -> new RuntimeException("No README found"));

        // This would be the newly generated README from git push
        ReadmeFileEntity newReadme = getNewlyGeneratedReadme(project, currentReadme.getId());

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

        ReadmeFileEntity currentReadme = readmeFileRepository.findLatestByProjectId(projectId)
                .orElseThrow(() -> new RuntimeException("No README found"));

        currentReadme.setContent(pushRequest.getContent());
        readmeFileRepository.save(currentReadme);

        writeReadmeToFile(project, pushRequest.getContent()); // Write to actual README.md file in project folder
        executeGitCommandsWithJGit(project); // Execute Git commands
    }

    private void executeGitCommandsWithJGit(ProjectEntity project) {
        try{
            FileRepositoryBuilder builder = new FileRepositoryBuilder();
            Repository repository = builder.setGitDir(new File(project.getLocalPath(), ".git"))
                    .readEnvironment()
                    .findGitDir()
                    .build();
            Git git = new Git(repository);
            git.add().addFilepattern(".").call(); // add
            git.commit()                            // commit
                    .setMessage("Readme Updated -automated")
                    .setAuthor("DocuMate", "documate@system")
                    .call();
            git.push().call(); // push

            git.close();
            log.info("JGit Completed Process Successfully ");
        } catch (Exception e) {
            log.info("Error executing JGit commands", e);
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
