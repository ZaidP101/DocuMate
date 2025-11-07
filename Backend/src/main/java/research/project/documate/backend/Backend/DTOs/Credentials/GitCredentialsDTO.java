package research.project.documate.backend.Backend.DTOs.Credentials;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GitCredentialsDTO {
    private String username;
    private String token;
    private String email;
}
