package research.project.documate.backend.Backend.DTOs.Project;

import lombok.Data;
import research.project.documate.backend.Backend.Entity.ProjectTemplate;

@Data
public class ProjectRegistrationDTO {
    private String title;
    private String gitRepoLink;
    private String localPath;
    private ProjectTemplate template;
}
