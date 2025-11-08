package research.project.documate.backend.Backend.Controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import research.project.documate.backend.Backend.DTOs.GitIgnore.GitignoreFileDiffDTO;
import research.project.documate.backend.Backend.DTOs.GitIgnore.GitignoreFilePushDTO;
import research.project.documate.backend.Backend.DTOs.GitIgnore.GitignoreFileResponseDTO;
import research.project.documate.backend.Backend.DTOs.GitIgnore.GitignoreRegenerateRequestDTO;
import research.project.documate.backend.Backend.Service.GitignoreFileService;

@RestController
@RequestMapping("/api/gitignore")
@AllArgsConstructor
@Slf4j
public class GitignoreFileController {
    private final GitignoreFileService gitignoreFileService;

    @GetMapping("/{projectId}")
    public ResponseEntity<GitignoreFileResponseDTO> getGitignore(@PathVariable Long projectId) {
        try {
            return ResponseEntity.ok(gitignoreFileService.getCurrentGitignore(projectId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{projectId}/generate")
    public ResponseEntity<GitignoreFileDiffDTO> generateGitignore(@PathVariable Long projectId) {
        try {
            return ResponseEntity.ok(gitignoreFileService.generateGitignore(projectId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{projectId}/diff")
    public ResponseEntity<GitignoreFileDiffDTO> getGitignoreDiff(@PathVariable Long projectId) {
        try {
            return ResponseEntity.ok(gitignoreFileService.getGitignoreDiff(projectId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{projectId}/push")
    public ResponseEntity<String> pushGitignore(@PathVariable Long projectId, @RequestBody GitignoreFilePushDTO pushRequest) {
        try {
            return ResponseEntity.ok(gitignoreFileService.approveAndWriteGitignore(projectId, pushRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{projectId}/regenerate")
    public ResponseEntity<GitignoreFileDiffDTO> regenerateGitignore(@PathVariable Long projectId, @RequestBody GitignoreRegenerateRequestDTO request) {
        try {
            return ResponseEntity.ok(gitignoreFileService.regenerateWithPrompt(projectId, request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
