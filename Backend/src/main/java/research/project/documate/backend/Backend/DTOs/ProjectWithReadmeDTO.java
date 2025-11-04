package research.project.documate.backend.Backend.DTOs;

import lombok.Builder;
import lombok.Data;
import research.project.documate.backend.Backend.Entity.ProjectTemplate;

import java.time.LocalDateTime;

@Builder
@Data
public class ProjectWithReadmeDTO {
    private Long id;
    private String title;
    private String gitRepoLink;
    private String localPath;
    private ProjectTemplate template;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String currentReadmeContent;
    private String currentReadmeCommitHash;
}
