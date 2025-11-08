package research.project.documate.backend.Backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import research.project.documate.backend.Backend.Entity.EnvExampleFileEntity;
import research.project.documate.backend.Backend.Entity.ProjectEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnvExampleFileRepository extends JpaRepository<EnvExampleFileEntity, Long> {
    @Query("SELECT e FROM EnvExampleFileEntity e WHERE e.project.id = :projectId ORDER BY e.createdAt DESC LIMIT 1")
    Optional<EnvExampleFileEntity> findLatestByProjectId(@Param("projectId") Long projectId);

    @Query("SELECT e FROM EnvExampleFileEntity e WHERE e.project.id = :projectId AND e.commitHash LIKE CONCAT(:prefix, '%') ORDER BY e.createdAt DESC LIMIT 1")
    Optional<EnvExampleFileEntity> findByProjectIdAndCommitHashStartingWith(@Param("projectId") Long projectId, @Param("prefix") String prefix);

    List<EnvExampleFileEntity> findByProjectOrderByCreatedAtDesc(ProjectEntity project);

    @Query("SELECT e FROM EnvExampleFileEntity e WHERE e.project.id = :projectId " +
            "AND (e.commitHash LIKE 'APPROVED_%' OR " +
            "(e.commitHash NOT LIKE 'PENDING_%' AND e.commitHash IS NOT NULL)) " +
            "ORDER BY e.createdAt DESC LIMIT 1")
    Optional<EnvExampleFileEntity> findActiveByProjectId(@Param("projectId") Long projectId);
}
