package research.project.documate.backend.Backend.DTOs;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GitDiffAnalysisDTO {
    private int filesChanged;
    private String changeSummary;
    private List<String> modifiedFiles;
    private String commitHash;
}
