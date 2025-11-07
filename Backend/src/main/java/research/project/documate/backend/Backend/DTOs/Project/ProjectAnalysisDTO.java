package research.project.documate.backend.Backend.DTOs.Project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectAnalysisDTO {
    private Map<String, String> fileStructure;
    private List<String> dependencies;
    private String projectType;
    private List<String> keyFiles;
    private String mainLanguage;
    private String buildTool;
    private String architectureType; // FULL_STACK, BACKEND_ONLY, FRONTEND_ONLY, MONOLITHIC
    private List<String> subProjects; // ["backend (JAVA)", "frontend (NODE_JS)"]
}
