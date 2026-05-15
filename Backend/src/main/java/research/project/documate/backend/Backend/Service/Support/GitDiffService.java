package research.project.documate.backend.Backend.Service.Support;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.springframework.stereotype.Service;
import research.project.documate.backend.Backend.DTOs.Readme.GitDiffAnalysisDTO;
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
            if (head == null) {
                log.warn("No HEAD commit found - repository might be empty");
                return createEmptyDiffAnalysis(commitHash);
            }
            RevWalk revWalk = new RevWalk(repository); // Get previous commit (parent of HEAD)
            RevCommit currentCommit = revWalk.parseCommit(head);

            String commitMessage = currentCommit.getFullMessage();
            if (commitMessage.contains("-automated")) {
                log.info("Automated system commit detected ({}). Skipping diff analysis to prevent hook loop.", commitHash);
                revWalk.close();
                git.close();
                // Returning an empty diff analysis means filesChanged = 0.
                // You should also update GitTriggerService to abort if filesChanged == 0
                return createEmptyDiffAnalysis(commitHash);
            }

            if (currentCommit.getParentCount() == 0) {
                log.info("No parent commit found - this might be the initial commit");
                revWalk.close();
                git.close();
                return createEmptyDiffAnalysis(commitHash);
            }
            //RevCommit parentCommit = currentCommit.getParent(0);
            //ObjectId previousHead = repository.resolve("HEAD~1");

            RevCommit parentCommit = currentCommit.getParent(0);
            if (parentCommit == null) {
                log.warn("Parent commit is null");
                revWalk.close();
                git.close();
                return createEmptyDiffAnalysis(commitHash);
            }

            ObjectId previousHead = parentCommit.getId();
            if (previousHead == null) {
                log.warn("Previous commit ID is null");
                revWalk.close();
                git.close();
                return createEmptyDiffAnalysis(commitHash);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            DiffFormatter diffFormatter = new DiffFormatter(outputStream);
            diffFormatter.setRepository(repository);

            List<DiffEntry> diffs = git.diff()
                    .setOldTree(prepareTreeParser(repository, previousHead))
                    .setNewTree(prepareTreeParser(repository, head))
                    .call();
            revWalk.close();
            git.close();

            return GitDiffAnalysisDTO.builder()
                    .filesChanged(diffs.size())
                    .changeSummary(extractChangeSummary(diffs))
                    .modifiedFiles(extractModifiedFiles(diffs))
                    .commitHash(commitHash)
                    .build();

        } catch (IncorrectObjectTypeException e) {
            log.error("Git repository corruption detected for project: {}", project.getTitle(), e);
            return createEmptyDiffAnalysis(commitHash); // Repair Git repo or return empty analysis
        }catch (Exception e) {
            log.error("Error analyzing git diff for project: {}",project.getTitle(), e);
            return createEmptyDiffAnalysis(commitHash);
        }
    }

    private GitDiffAnalysisDTO createEmptyDiffAnalysis(String commitHash) {
        return GitDiffAnalysisDTO.builder()
                .filesChanged(0)
                .changeSummary("No changes detected or initial commit")
                .modifiedFiles(new ArrayList<>())
                .commitHash(commitHash)
                .build();
    }

    private AbstractTreeIterator prepareTreeParser(Repository repository, ObjectId commitId) throws IOException {
        try (RevWalk walk = new RevWalk(repository)) {
            RevObject obj = walk.parseAny(commitId);

            if (!(obj instanceof RevCommit)) {
                log.warn("Object {} is not a commit (found {}). Returning empty tree.", commitId.name(), obj.getClass().getSimpleName());
                return new EmptyTreeIterator(); // Prevent crash
            }

            RevCommit commit = (RevCommit) obj;
            RevTree tree = commit.getTree();

            CanonicalTreeParser treeParser = new CanonicalTreeParser();
            try (ObjectReader reader = repository.newObjectReader()) {
                treeParser.reset(reader, tree);
            }

            walk.dispose();
            return treeParser;
        }
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
