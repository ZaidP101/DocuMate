package research.project.documate.backend.Backend.Controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import research.project.documate.backend.Backend.DTOs.Docker.DockerFileDiffDTO;
import research.project.documate.backend.Backend.DTOs.Docker.DockerFilePushDTO;
import research.project.documate.backend.Backend.DTOs.Docker.DockerFileResponseDTO;
import research.project.documate.backend.Backend.DTOs.Docker.DockerRegenerateRequestDTO;
import research.project.documate.backend.Backend.Service.DockerFileService;

@RestController
@RequestMapping("/api/docker")
@AllArgsConstructor
@Slf4j
public class DockerFileController {
    private final DockerFileService dockerFileService;

    @GetMapping("/{projectId}") // 1. Check current
    public ResponseEntity<DockerFileResponseDTO> getDockerFile(@PathVariable Long projectId) {
        try {
            return ResponseEntity.ok(dockerFileService.getCurrentDockerFile(projectId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{projectId}/generate") // 2. Generate new (creates PENDING)
    public ResponseEntity<DockerFileDiffDTO> generateDockerFile(@PathVariable Long projectId) {
        try {
            return ResponseEntity.ok(dockerFileService.generateDockerFile(projectId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{projectId}/push") // 4. Approve and write
    public ResponseEntity<String> pushDockerFile(@PathVariable Long projectId, @RequestBody DockerFilePushDTO pushRequest) {
        try {
            return ResponseEntity.ok(dockerFileService.approveAndWriteDockerFile(projectId, pushRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{projectId}/regenerate") // 5. Regenerate with prompt
    public ResponseEntity<DockerFileDiffDTO> regenerateDockerFile(@PathVariable Long projectId, @RequestBody DockerRegenerateRequestDTO request) {
        try {
            return ResponseEntity.ok(dockerFileService.regenerateWithPrompt(projectId, request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}