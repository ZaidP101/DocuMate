package research.project.documate.backend.Backend.DTOs.Docker;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DockerFileDiffDTO {
    private String oldContent;
    private String newContent;
    private String changeSummary;
    private Long projectId;
}
