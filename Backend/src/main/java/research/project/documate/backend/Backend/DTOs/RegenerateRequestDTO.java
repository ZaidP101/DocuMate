package research.project.documate.backend.Backend.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegenerateRequestDTO {
    private String userPrompt;
    private String currentContent;
    private Long projectId;
}
