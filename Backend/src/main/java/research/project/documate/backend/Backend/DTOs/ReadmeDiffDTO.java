package research.project.documate.backend.Backend.DTOs;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReadmeDiffDTO {
    private String oldContent;
    private String newContent;
    private String changeSummary;
    private Long projectId;
}
