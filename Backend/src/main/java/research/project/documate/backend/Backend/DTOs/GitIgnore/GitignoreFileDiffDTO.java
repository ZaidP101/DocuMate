package research.project.documate.backend.Backend.DTOs.GitIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GitignoreFileDiffDTO {
    private String oldContent;
    private String newContent;
    private String changeSummary;
    private Long projectId;
}
