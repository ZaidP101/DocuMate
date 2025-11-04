package research.project.documate.backend.Backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import research.project.documate.backend.Backend.Entity.ProjectEntity;
import research.project.documate.backend.Backend.Entity.ReadmeFileEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReadmeFileRepository extends JpaRepository<ReadmeFileEntity, Long> {

    @Query("SELECT r FROM ReadmeFileEntity r WHERE r.project.id = :projectId ORDER BY r.createdAt DESC LIMIT 1")
    Optional<ReadmeFileEntity> findLatestByProjectId(@Param("projectId") Long projectId);

    Optional<ReadmeFileEntity> findTopByProjectAndCommitHashNotOrderByCreatedAtDesc(ProjectEntity project, String initial);
    List<ReadmeFileEntity> findByProjectOrderByCreatedAtDesc(ProjectEntity project);

    @Query("SELECT r FROM ReadmeFileEntity r WHERE r.project.id = :projectId")
    Optional<ReadmeFileEntity> findByProjectId(@Param("projectId") Long projectId);

    List<ReadmeFileEntity> findByProjectIdOrderByCreatedAtDesc(Long projectId);


}
