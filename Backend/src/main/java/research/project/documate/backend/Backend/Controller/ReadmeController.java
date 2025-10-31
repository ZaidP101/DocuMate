package research.project.documate.backend.Backend.Controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import research.project.documate.backend.Backend.DTOs.ReadmeDiffDTO;
import research.project.documate.backend.Backend.DTOs.ReadmePushDTO;
import research.project.documate.backend.Backend.DTOs.RegenerateRequestDTO;
import research.project.documate.backend.Backend.Service.ReadmeService;

@RestController
@RequestMapping("/api/readme")
@Slf4j
@AllArgsConstructor
public class ReadmeController {
    private final ReadmeService readmeService;

    @GetMapping("/{projectId}/diff")
    public ResponseEntity<ReadmeDiffDTO> getReadmeDiff(@PathVariable Long projectId) { // Returns old vs new README for diff view
        ReadmeDiffDTO diff = readmeService.getReadmeDiff(projectId);
        return ResponseEntity.ok(diff);
    }

    @PostMapping("/{projectId}/push")
    public ResponseEntity<String> pushReadme(@PathVariable Long projectId, @RequestBody ReadmePushDTO pushRequest) {
        readmeService.approveAndPushReadme(projectId, pushRequest); // User approves - write to actual README.md file
        return ResponseEntity.ok("README updated successfully");
    }

    @PostMapping("/{projectId}/regenerate")
    public ResponseEntity<ReadmeDiffDTO> regenerateReadme(@PathVariable Long projectId, @RequestBody RegenerateRequestDTO request) {
        ReadmeDiffDTO updatedDiff = readmeService.regenerateWithPrompt(projectId, request);// User requests changes via prompt
        return ResponseEntity.ok(updatedDiff);
    }
}
