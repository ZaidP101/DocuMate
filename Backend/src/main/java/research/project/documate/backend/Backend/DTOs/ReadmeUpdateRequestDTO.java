package research.project.documate.backend.Backend.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReadmeUpdateRequestDTO {
    private Long projectId;
    private String content; // Final content after user edits
    private Boolean approve;
}
