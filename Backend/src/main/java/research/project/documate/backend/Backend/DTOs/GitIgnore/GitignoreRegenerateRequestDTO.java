package research.project.documate.backend.Backend.DTOs.GitIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GitignoreRegenerateRequestDTO {
    private String userPrompt;
    private String currentContent;
    private Long projectId;
}
