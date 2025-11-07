package research.project.documate.backend.Backend.DTOs.Docker;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DockerFileResponseDTO {
    private Long id;
    private String content;
    private Boolean exists;
    private String status;
    private LocalDateTime createdAt;
}
