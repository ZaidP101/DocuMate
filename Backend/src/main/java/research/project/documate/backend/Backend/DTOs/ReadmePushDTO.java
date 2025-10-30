package research.project.documate.backend.Backend.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReadmePushDTO {
    private String content; // Final content after user edits
    private String action; // APPROVE or EDIT
}
