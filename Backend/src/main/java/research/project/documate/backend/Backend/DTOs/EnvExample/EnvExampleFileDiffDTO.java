package research.project.documate.backend.Backend.DTOs.EnvExample;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnvExampleFileDiffDTO {
    private String oldContent;
    private String newContent;
    private String changeSummary;
    private Long projectId;
}
