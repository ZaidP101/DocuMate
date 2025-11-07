package research.project.documate.backend.Backend.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import research.project.documate.backend.Backend.DTOs.Credentials.GitCredentialsDTO;
import research.project.documate.backend.Backend.DTOs.Credentials.GitCredentialsResponseDTO;
import research.project.documate.backend.Backend.Service.GitCredentialsService;

@RestController
@RequestMapping("/api/git-credentials")
@AllArgsConstructor
public class GitCredentialsController {
    private final GitCredentialsService credentialsService;

    @PostMapping
    public ResponseEntity<GitCredentialsResponseDTO> saveCredentials(@RequestBody GitCredentialsDTO gitCredentialsDTO) {
        try {
            GitCredentialsResponseDTO response = credentialsService.saveOrUpdateCredentials(gitCredentialsDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<GitCredentialsResponseDTO> getCredentials() {
        try {
            GitCredentialsResponseDTO response = credentialsService.getCredentials();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<GitCredentialsResponseDTO> updateCredentials(
            @RequestBody GitCredentialsDTO dto) {
        return saveCredentials(dto);
    }
}
