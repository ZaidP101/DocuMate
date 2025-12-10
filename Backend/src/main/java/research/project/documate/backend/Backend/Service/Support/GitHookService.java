package research.project.documate.backend.Backend.Service.Support;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import research.project.documate.backend.Backend.Entity.ProjectEntity;
import research.project.documate.backend.Backend.Repository.ProjectRepository;

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
            String hookScript;

            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                hookFileName = "post-commit.bat";  // Use post-commit instead of post-push
                hookScript = createWindowsHookScript(project.getId(), projectPath);
                log.info("Detected Windows OS, creating .bat hook file");
            } else {
                hookFileName = "post-commit";  // Use post-commit for Unix
                hookScript = createUnixHookScript(project.getId(), projectPath);
                log.info("Detected Unix/Linux OS, creating standard hook file");
            }

            Path hookPath = Paths.get(projectPath, ".git", "hooks", hookFileName);
            Files.write(hookPath, hookScript.getBytes(StandardCharsets.UTF_8));

            if (!System.getProperty("os.name").toLowerCase().contains("windows")) {
                hookPath.toFile().setExecutable(true);
            }

            log.info("Git hook installed successfully: {} for project: {}", hookPath, project.getTitle());
        } catch (Exception e) {
            log.error("Failed to install git hook for: {}", projectPath, e);
        }
    }

    private String createWindowsHookScript(Long projectId, String projectPath) {
        // Fixed Windows batch script
        return """
            @echo off
            echo DocuMate: Git commit detected, triggering README update...
            
            REM Get the latest commit hash
            for /f "delimiter=" %%H in ('git rev-parse HEAD') do set COMMIT_HASH=%%H
            
            curl -X POST http://localhost:8181/api/git/push-trigger ^
                 -H "Content-Type: application/json" ^
                 -d "{\\"projectId\\":%d,\\"projectPath\\":\\"%s\\",\\"commitHash\\":\\"%%COMMIT_HASH%%\\"}"
            
            if errorlevel 1 (
                echo DocuMate: Error triggering README update
            ) else (
                echo DocuMate: README update triggered successfully
            )
            """.formatted(projectId, projectPath.replace("\\", "\\\\"));
    }

    private String createUnixHookScript(Long projectId, String projectPath) {
        return """
            #!/bin/bash
            echo "DocuMate: Git commit detected, triggering README update..."
            
            COMMIT_HASH=$(git rev-parse HEAD)
            
            curl -X POST http://localhost:8181/api/git/push-trigger \\
                 -H "Content-Type: application/json" \\
                 -d "{\\"projectId\\":%d,\\"projectPath\\":\\"%s\\",\\"commitHash\\":\\"$COMMIT_HASH\\"}"
            
            echo "DocuMate: README update triggered for commit: $COMMIT_HASH"
            """.formatted(projectId, projectPath);
    }
}
