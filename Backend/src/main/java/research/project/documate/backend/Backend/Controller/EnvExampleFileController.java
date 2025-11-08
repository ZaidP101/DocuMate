package research.project.documate.backend.Backend.Controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import research.project.documate.backend.Backend.DTOs.EnvExample.EnvExampleFileDiffDTO;
import research.project.documate.backend.Backend.DTOs.EnvExample.EnvExampleFilePushDTO;
import research.project.documate.backend.Backend.DTOs.EnvExample.EnvExampleFileResponseDTO;
import research.project.documate.backend.Backend.DTOs.EnvExample.EnvExampleRegenerateRequestDTO;
import research.project.documate.backend.Backend.Service.EnvExampleFileService;

@RestController
@RequestMapping("/api/env-example")
@AllArgsConstructor
@Slf4j
public class EnvExampleFileController {
    private final EnvExampleFileService envExampleFileService;

    @GetMapping("/{projectId}")
    public ResponseEntity<EnvExampleFileResponseDTO> getEnvExample(@PathVariable Long projectId) {
        try {
            return ResponseEntity.ok(envExampleFileService.getCurrentEnvExample(projectId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{projectId}/generate")
    public ResponseEntity<EnvExampleFileDiffDTO> generateEnvExample(@PathVariable Long projectId) {
        try {
            return ResponseEntity.ok(envExampleFileService.generateEnvExample(projectId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{projectId}/diff")
    public ResponseEntity<EnvExampleFileDiffDTO> getEnvExampleDiff(@PathVariable Long projectId) {
        try {
            return ResponseEntity.ok(envExampleFileService.getEnvExampleDiff(projectId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{projectId}/push")
    public ResponseEntity<String> pushEnvExample(@PathVariable Long projectId, @RequestBody EnvExampleFilePushDTO pushRequest) {
        try {
            return ResponseEntity.ok(envExampleFileService.approveAndWriteEnvExample(projectId, pushRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{projectId}/regenerate")
    public ResponseEntity<EnvExampleFileDiffDTO> regenerateEnvExample(@PathVariable Long projectId, @RequestBody EnvExampleRegenerateRequestDTO request) {
        try {
            return ResponseEntity.ok(envExampleFileService.regenerateWithPrompt(projectId, request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
