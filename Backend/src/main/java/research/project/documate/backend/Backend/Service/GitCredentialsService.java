package research.project.documate.backend.Backend.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import research.project.documate.backend.Backend.DTOs.Credentials.GitCredentialsDTO;
import research.project.documate.backend.Backend.DTOs.Credentials.GitCredentialsResponseDTO;
import research.project.documate.backend.Backend.Entity.GitCredentials;
import research.project.documate.backend.Backend.Repository.GitCredentialsRepository;

import java.time.LocalDateTime;
import java.util.Base64;

@Service
@Slf4j
public class GitCredentialsService {
    private final GitCredentialsRepository credentialsRepository;

    @Value("${encryption.secret-key}")
    private String secretKey;

    public GitCredentialsService(GitCredentialsRepository credentialsRepository, @Value("${encryption.secret-key}") String secretKey) {
        this.credentialsRepository = credentialsRepository;
        this.secretKey = secretKey;
    }

    public GitCredentialsResponseDTO saveOrUpdateCredentials(GitCredentialsDTO dto) {
        String encryptedToken = encryptToken(dto.getToken());

        GitCredentials credentials = credentialsRepository.findById(1L)
                .orElse(GitCredentials.builder().id(1L).build());

        credentials.setUsername(dto.getUsername());
        credentials.setEncryptedToken(encryptedToken);
        credentials.setEmail(dto.getEmail());
        credentials.setUpdatedAt(LocalDateTime.now());

        if (credentials.getCreatedAt() == null) {
            credentials.setCreatedAt(LocalDateTime.now());
        }

        GitCredentials saved = credentialsRepository.save(credentials);
        log.info("Git credentials saved/updated for user: {}", dto.getUsername());

        return mapToResponseDTO(saved);
    }

    public GitCredentialsResponseDTO getCredentials() {
        return credentialsRepository.findById(1L)
                .map(this::mapToResponseDTO)
                .orElseThrow(() -> new RuntimeException("No Git credentials found"));
    }

    public String getDecryptedToken() {
        GitCredentials credentials = credentialsRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("No Git credentials found"));
        return decryptToken(credentials.getEncryptedToken());
    }

    public GitCredentials getGitCredentials() {
        return credentialsRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("No Git credentials found"));
    }

    private String encryptToken(String token) {
        return Base64.getEncoder().encodeToString(token.getBytes()); // Simple base64 for demo
    }

    private String decryptToken(String encryptedToken) {
        return new String(Base64.getDecoder().decode(encryptedToken));
    }

    private GitCredentialsResponseDTO mapToResponseDTO(GitCredentials credentials) {
        return GitCredentialsResponseDTO.builder()
                .username(credentials.getUsername())
                .email(credentials.getEmail())
                .createdAt(credentials.getCreatedAt())
                .updatedAt(credentials.getUpdatedAt())
                .build();
    }
}
