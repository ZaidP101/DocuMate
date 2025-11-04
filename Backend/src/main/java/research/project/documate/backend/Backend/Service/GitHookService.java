package research.project.documate.backend.Backend.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import research.project.documate.backend.Backend.Entity.ProjectEntity;
import research.project.documate.backend.Backend.Repository.ProjectRepository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
            ProjectEntity project = projectRepository.findAll().stream()
                    .filter(p -> p.getLocalPath() != null && p.getLocalPath().equals(projectPath))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Project not found for path: " + projectPath));

            String hookFileName;
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                hookFileName = "post-push.bat";  // Add .bat extension for Windows
                log.info("Detected Windows OS, creating .bat hook file");
            } else {
                hookFileName = "post-push";  // No extension for Unix
                log.info("Detected Unix/Linux OS, creating standard hook file");
            }

            Path hookPath = Paths.get(projectPath, ".git", "hooks", hookFileName);

            String hookScript = createWindowsHookScript(project.getId(), projectPath);
            Files.write(hookPath, hookScript.getBytes(StandardCharsets.UTF_8));

            if (!System.getProperty("os.name").toLowerCase().contains("windows")) {
                hookPath.toFile().setExecutable(true);
            }
            log.info("✅ Git hook installed: {} for project: {}", hookFileName, project.getTitle());
        } catch (Exception e) {
            log.error("Failed to install git hook for: {}", projectPath, e);
        }
    }

    private String createWindowsHookScript(Long projectId, String projectPath) {
        return """
            @echo off
            echo DocuMate: Git push detected, triggering README update...
            curl -X POST http://localhost:8181/api/git/push-trigger ^
                 -H "Content-Type: application/json" ^
                 -d "{\\"projectId\\":%d,\\"projectPath\\":\\"%s\\",\\"commitHash\\":\\"%%1\\"}" ^
                 --connect-timeout 10 ^
                 --max-time 30
            if errorlevel 1 (
                echo DocuMate: Error triggering README update
            ) else (
                echo DocuMate: README update triggered successfully
            )
            """.formatted(projectId, projectPath.replace("\\", "\\\\"));
    }

    private String createUnixHookScript(Long projectId, String projectPath) {
        return """
            #!/bin/sh
            echo "DocuMate: Git push detected, triggering README update..."
            curl -X POST http://localhost:8181/api/git/push-trigger \\
                 -H "Content-Type: application/json" \\
                 -d "{\\"projectId\\":%d,\\"projectPath\\":\\"$(pwd)\\",\\"commitHash\\":\\"$1\\"}" \\
                 --connect-timeout 10 \\
                 --max-time 30
            echo "DocuMate: README update triggered"
            """.formatted(projectId);
    }
}
