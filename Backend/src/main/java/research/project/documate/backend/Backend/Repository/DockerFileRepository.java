package research.project.documate.backend.Backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import research.project.documate.backend.Backend.Entity.DockerFileEntity;
import research.project.documate.backend.Backend.Entity.ProjectEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface DockerFileRepository extends JpaRepository<DockerFileEntity, Long> {

    @Query("SELECT d FROM DockerFileEntity d WHERE d.project.id = :projectId ORDER BY d.createdAt DESC LIMIT 1")
    Optional<DockerFileEntity> findLatestByProjectId(@Param("projectId") Long projectId);

    @Query("SELECT d FROM DockerFileEntity d WHERE d.project.id = :projectId AND d.commitHash LIKE CONCAT(:prefix, '%') ORDER BY d.createdAt DESC LIMIT 1")
    Optional<DockerFileEntity> findByProjectIdAndCommitHashStartingWith(@Param("projectId") Long projectId, @Param("prefix") String prefix);

    List<DockerFileEntity> findByProjectOrderByCreatedAtDesc(ProjectEntity project);

    @Query("SELECT d FROM DockerFileEntity d WHERE d.project.id = :projectId " +
            "AND (d.commitHash LIKE 'APPROVED_%' OR " +
            "(d.commitHash NOT LIKE 'PENDING_%' AND d.commitHash IS NOT NULL)) " +
            "ORDER BY d.createdAt DESC LIMIT 1")
    Optional<DockerFileEntity> findActiveByProjectId(@Param("projectId") Long projectId);
}
