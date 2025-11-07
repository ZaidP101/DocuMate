package research.project.documate.backend.Backend.Controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import research.project.documate.backend.Backend.DTOs.Readme.GitPushEventDTO;
import research.project.documate.backend.Backend.Service.GitTriggerService;

@RestController
@RequestMapping("/api/git")
@Slf4j
@AllArgsConstructor
public class GitTriggerController {
    private final GitTriggerService gitTriggerService;

    @PostMapping("/push-trigger")
    public ResponseEntity<String> handleGitPush(@RequestBody GitPushEventDTO pushEvent) {
        log.info("Git push detected for project: {}", pushEvent.getProjectPath());

        try {
            gitTriggerService.processGitPush(pushEvent);
            return ResponseEntity.ok("Processing README update...");
        } catch (Exception e) {
            log.error("Error processing git push", e);
            return ResponseEntity.status(500).body("Error processing push");
        }
    }
}
