package research.project.documate.backend.Backend.DTOs.Readme;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GitPushEventDTO {
    private Long projectId;
    private String projectPath;
    private String commitHash;
}
