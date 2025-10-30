package research.project.documate.backend.Backend.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import research.project.documate.backend.Backend.DTOs.ReadmeDiffDTO;
import research.project.documate.backend.Backend.DTOs.ReadmeGenerationResultDTO;
import research.project.documate.backend.Backend.DTOs.ReadmePushDTO;
import research.project.documate.backend.Backend.DTOs.RegenerateRequestDTO;
import research.project.documate.backend.Backend.Entity.ProjectEntity;
import research.project.documate.backend.Backend.Entity.ReadmeFileEntity;
import research.project.documate.backend.Backend.Repository.ProjectRepository;
import research.project.documate.backend.Backend.Repository.ReadmeFileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

        ReadmeFileEntity currentReadme = readmeFileRepository.findByProject(project)
                .orElseThrow(() -> new RuntimeException("No README found"));

        // This would be the newly generated README from git push
        ReadmeFileEntity newReadme = getNewlyGeneratedReadme(project);

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

        // 1. Update the README in database with final content
        ReadmeFileEntity currentReadme = readmeFileRepository.findByProject(project)
                .orElseThrow(() -> new RuntimeException("No README found"));

        currentReadme.setContent(pushRequest.getContent());
        readmeFileRepository.save(currentReadme);

        // 2. Write to actual README.md file in project folder
        writeReadmeToFile(project, pushRequest.getContent());

        // 3. Git add, commit, push would happen here
        // You'll need to implement this part
    }

    private void writeReadmeToFile(ProjectEntity project, String content) {
        try {
            Path readmePath = Paths.get(project.getLocalPath(), "README.md");
            Files.write(readmePath, content.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to write README.md file", e);
        }
    }

    private ReadmeFileEntity getNewlyGeneratedReadme(ProjectEntity project) {
        // Get the most recently generated README (not yet approved)
        return readmeFileRepository.findTopByProjectAndCommitHashNotOrderByCreatedAtDesc(project, "INITIAL")
                .orElseThrow(() -> new RuntimeException("No newly generated README found"));
    }

    public ReadmeDiffDTO regenerateWithPrompt(Long projectId, RegenerateRequestDTO request) {
        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Send to Gemini with user prompt
        String enhancedPrompt = request.getCurrentContent() +
                "\n\nUser requested changes: " + request.getUserPrompt();

        ReadmeGenerationResultDTO newResult = readmeAiService.regenerateWithPrompt(
                project, enhancedPrompt);

        // Return updated diff for frontend
        return ReadmeDiffDTO.builder()
                .oldContent(request.getCurrentContent())
                .newContent(newResult.getContent())
                .changeSummary("Regenerated based on user request: " + request.getUserPrompt())
                .projectId(projectId)
                .build();
    }
}
