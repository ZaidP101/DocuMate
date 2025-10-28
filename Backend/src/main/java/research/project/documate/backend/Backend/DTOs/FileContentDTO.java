package research.project.documate.backend.Backend.DTOs;

import lombok.Data;

@Data
public class FileContentDTO {
    private String oldContent;
    private String newContent;
    private String changeSummary;
    private String commitMessage;
}
