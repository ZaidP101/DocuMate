package research.project.documate.backend.Backend.Controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import research.project.documate.backend.Backend.DTOs.Docker.DockerFileDiffDTO;
import research.project.documate.backend.Backend.DTOs.Docker.DockerFilePushDTO;
import research.project.documate.backend.Backend.DTOs.Docker.DockerFileResponseDTO;
import research.project.documate.backend.Backend.DTOs.Docker.DockerRegenerateRequestDTO;
import research.project.documate.backend.Backend.Entity.DockerFileEntity;
import research.project.documate.backend.Backend.Repository.DockerFileRepository;
import research.project.documate.backend.Backend.Service.DockerFileService;

@RestController
@RequestMapping("/api/docker")
@AllArgsConstructor
@Slf4j
public class DockerFileController {
    private final DockerFileService dockerFileService;

    // 1. Check current
    @GetMapping("/{projectId}")
    public ResponseEntity<DockerFileResponseDTO> getDockerFile(@PathVariable Long projectId) {
        try {
            return ResponseEntity.ok(dockerFileService.getCurrentDockerFile(projectId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 2. Generate new (creates PENDING)
    @PostMapping("/{projectId}/generate")
    public ResponseEntity<DockerFileDiffDTO> generateDockerFile(@PathVariable Long projectId) {
        try {
            return ResponseEntity.ok(dockerFileService.generateDockerFile(projectId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 4. Approve and write
    @PostMapping("/{projectId}/push")
    public ResponseEntity<String> pushDockerFile(@PathVariable Long projectId, @RequestBody DockerFilePushDTO pushRequest) {
        try {
            return ResponseEntity.ok(dockerFileService.approveAndWriteDockerFile(projectId, pushRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 5. Regenerate with prompt
    @PostMapping("/{projectId}/regenerate")
    public ResponseEntity<DockerFileDiffDTO> regenerateDockerFile(@PathVariable Long projectId, @RequestBody DockerRegenerateRequestDTO request) {
        try {
            return ResponseEntity.ok(dockerFileService.regenerateWithPrompt(projectId, request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}