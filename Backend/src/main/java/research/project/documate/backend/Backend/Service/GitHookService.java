package research.project.documate.backend.Backend.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import research.project.documate.backend.Backend.Entity.ProjectEntity;
import research.project.documate.backend.Backend.Repository.ProjectRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
@AllArgsConstructor
public class GitHookService {
    private final ProjectRepository projectRepository;

    public void installGitHook(String projectPath) {
        try {
            // Find the project to get its ID
            ProjectEntity project = projectRepository.findAll().stream()
                    .filter(p -> p.getLocalPath() != null && p.getLocalPath().equals(projectPath))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Project not found for path: " + projectPath));

            Path hookPath = Paths.get(projectPath, ".git", "hooks", "post-push");

            // Include projectId in the hook script
            String hookScript = """
                #!/bin/sh
                # Trigger DocuMate on successful push
                curl -X POST http://localhost:8181/api/git/push-trigger \\
                     -H "Content-Type: application/json" \\
                     -d "{\\"projectId\\":%d,\\"projectPath\\":\\""$(pwd)"\\",\\"commitHash\\":\\"$1\\"}" \\
                     --connect-timeout 10 \\
                     --max-time 30
                echo "DocuMate: Push processing completed"
                """.formatted(project.getId());

            Files.write(hookPath, hookScript.getBytes());
            hookPath.toFile().setExecutable(true);

            log.info("Git hook installed for project: {} (ID: {})", project.getTitle(), project.getId());
        } catch (IOException e) {
            log.error("Failed to install git hook for: {}", projectPath, e);
        }
    }
}
