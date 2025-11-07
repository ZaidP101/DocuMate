package research.project.documate.backend.Backend.DTOs.Docker;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DockerRegenerateRequestDTO {
    private String userPrompt;
    private String currentContent;
    private Long projectId;
}