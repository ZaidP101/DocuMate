package research.project.documate.backend.Backend.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.springframework.stereotype.Service;
import research.project.documate.backend.Backend.DTOs.GitDiffAnalysisDTO;
import research.project.documate.backend.Backend.Entity.ProjectEntity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class GitDiffService {
    public GitDiffAnalysisDTO analyzeChanges(ProjectEntity project, String commitHash) {
        try {
            FileRepositoryBuilder builder = new FileRepositoryBuilder();
            Repository repository = builder.setGitDir(new File(project.getLocalPath(), ".git"))
                    .readEnvironment()
                    .findGitDir()
                    .build();

            Git git = new Git(repository);

            // Get changes from last commit to current
            ObjectId head = repository.resolve("HEAD");
            ObjectId previousHead = repository.resolve("HEAD~1");

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            DiffFormatter diffFormatter = new DiffFormatter(outputStream);
            diffFormatter.setRepository(repository);

            List<DiffEntry> diffs = git.diff()
                    .setOldTree(prepareTreeParser(repository, previousHead))
                    .setNewTree(prepareTreeParser(repository, head))
                    .call();

            return GitDiffAnalysisDTO.builder()
                    .filesChanged(diffs.size())
                    .changeSummary(extractChangeSummary(diffs))
                    .modifiedFiles(extractModifiedFiles(diffs))
                    .commitHash(commitHash)
                    .build();

        } catch (Exception e) {
            log.error("Error analyzing git diff", e);
            throw new RuntimeException("Git diff analysis failed");
        }
    }
    private AbstractTreeIterator prepareTreeParser(Repository repository, ObjectId objectId) throws IOException {
        CanonicalTreeParser treeParser = new CanonicalTreeParser();
        try (ObjectReader reader = repository.newObjectReader()) {
            treeParser.reset(reader, objectId);
        }
        return treeParser;
    }

    private String extractChangeSummary(List<DiffEntry> diffs) {
        List<String> changes = new ArrayList<>();
        for (DiffEntry diff : diffs) {
            String changeType = getChangeType(diff.getChangeType());
            changes.add(changeType + ": " + diff.getNewPath());
        }
        return String.join(", ", changes);
    }

    private List<String> extractModifiedFiles(List<DiffEntry> diffs) {
        return diffs.stream()
                .map(DiffEntry::getNewPath)
                .filter(path -> !path.equals("/dev/null")) // Filter out deletions
                .collect(Collectors.toList());
    }

    private String getChangeType(DiffEntry.ChangeType changeType) {
        switch (changeType) {
            case ADD: return "Added";
            case MODIFY: return "Modified";
            case DELETE: return "Deleted";
            case RENAME: return "Renamed";
            case COPY: return "Copied";
            default: return "Changed";
        }
    }
}
