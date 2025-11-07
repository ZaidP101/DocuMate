package research.project.documate.backend.Backend.DTOs.Project;

import lombok.Data;
import research.project.documate.backend.Backend.Entity.ProjectTemplate;

import java.time.LocalDateTime;

@Data
public class ProjectResponseDTO {
    private Long id;
    private String title;
    private String gitRepoLink;
    private String localPath;
    private ProjectTemplate template;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
