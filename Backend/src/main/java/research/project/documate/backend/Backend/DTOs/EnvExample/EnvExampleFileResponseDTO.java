package research.project.documate.backend.Backend.DTOs.EnvExample;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EnvExampleFileResponseDTO {
    private Long id;
    private String content;
    private Boolean exists;
    private String status;
    private LocalDateTime createdAt;
}
