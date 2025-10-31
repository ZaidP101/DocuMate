package research.project.documate.backend.Backend.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
            Path hookPath = Paths.get(projectPath, ".git", "hooks", "post-push");
            String hookScript = createHookScript();

            Files.write(hookPath, hookScript.getBytes());
            hookPath.toFile().setExecutable(true);

            log.info("Git hook installed for project: {}", projectPath);
        } catch (IOException e) {
            log.error("Failed to install git hook for: {}", projectPath, e);
        }
    }

    private String createHookScript() {
        return """
            #!/bin/bash
            # Trigger DocuMate on successful push
            curl -X POST http://localhost:8181/api/git/push-trigger \\
                 -H "Content-Type: application/json" \\
                 -d '{"projectPath": "'"$(pwd)"'", "commitHash": "'"$1"'"}'
            """;
    }
}
