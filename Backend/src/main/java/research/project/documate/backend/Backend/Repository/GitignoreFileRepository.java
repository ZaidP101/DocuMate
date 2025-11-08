package research.project.documate.backend.Backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import research.project.documate.backend.Backend.Entity.GitignoreFileEntity;
import research.project.documate.backend.Backend.Entity.ProjectEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface GitignoreFileRepository extends JpaRepository<GitignoreFileEntity, Long> {
    @Query("SELECT g FROM GitignoreFileEntity g WHERE g.project.id = :projectId ORDER BY g.createdAt DESC LIMIT 1")
    Optional<GitignoreFileEntity> findLatestByProjectId(@Param("projectId") Long projectId);

    @Query("SELECT g FROM GitignoreFileEntity g WHERE g.project.id = :projectId AND g.commitHash LIKE CONCAT(:prefix, '%') ORDER BY g.createdAt DESC LIMIT 1")
    Optional<GitignoreFileEntity> findByProjectIdAndCommitHashStartingWith(@Param("projectId") Long projectId, @Param("prefix") String prefix);

    List<GitignoreFileEntity> findByProjectOrderByCreatedAtDesc(ProjectEntity project);

    @Query("SELECT g FROM GitignoreFileEntity g WHERE g.project.id = :projectId " +
            "AND (g.commitHash LIKE 'APPROVED_%' OR " +
            "(g.commitHash NOT LIKE 'PENDING_%' AND g.commitHash IS NOT NULL)) " +
            "ORDER BY g.createdAt DESC LIMIT 1")
    Optional<GitignoreFileEntity> findActiveByProjectId(@Param("projectId") Long projectId);
}
