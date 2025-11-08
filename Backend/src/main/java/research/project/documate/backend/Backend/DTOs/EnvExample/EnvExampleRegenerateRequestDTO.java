package research.project.documate.backend.Backend.DTOs.EnvExample;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnvExampleRegenerateRequestDTO {
    private String userPrompt;
    private String currentContent;
    private Long projectId;
}
