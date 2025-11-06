package research.project.documate.backend.Backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import research.project.documate.backend.Backend.Entity.ProjectEntity;
import research.project.documate.backend.Backend.Entity.ReadmeFileEntity;

import java.util.List;
import java.util.Optional;

import static org.apache.naming.SelectorContext.prefix;

@Repository
public interface ReadmeFileRepository extends JpaRepository<ReadmeFileEntity, Long> {

    @Query("SELECT r FROM ReadmeFileEntity r WHERE r.project.id = :projectId ORDER BY r.createdAt DESC LIMIT 1")
    Optional<ReadmeFileEntity> findLatestByProjectId(@Param("projectId") Long projectId);

    Optional<ReadmeFileEntity> findTopByProjectAndCommitHashNotOrderByCreatedAtDesc(ProjectEntity project, String initial);
    List<ReadmeFileEntity> findByProjectOrderByCreatedAtDesc(ProjectEntity project);

    @Query("SELECT r FROM ReadmeFileEntity r WHERE r.project.id = :projectId AND r.commitHash NOT LIKE 'PENDING_%' ORDER BY r.createdAt DESC LIMIT 1")
    Optional<ReadmeFileEntity> findByProjectId(@Param("projectId") Long projectId);

    List<ReadmeFileEntity> findByProjectIdOrderByCreatedAtDesc(Long projectId);

    @Query("SELECT r FROM ReadmeFileEntity r WHERE r.project.id = :projectId AND r.commitHash LIKE CONCAT(:prefix, '%') ORDER BY r.createdAt DESC LIMIT 1")
    Optional<ReadmeFileEntity> findByProjectIdAndCommitHashStartingWith(@Param("projectId") Long projectId, @Param("prefix") String prefix);
}
