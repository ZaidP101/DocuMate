package research.project.documate.backend.Backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import research.project.documate.backend.Backend.Entity.ProjectEntity;
import research.project.documate.backend.Backend.Entity.ReadmeFileEntity;

import java.util.Optional;

@Repository
public interface ReadmeFileRepository extends JpaRepository<ReadmeFileEntity, Long> {
    Optional<ReadmeFileEntity> findByProject(ProjectEntity project);

    Optional<ReadmeFileEntity> findTopByProjectAndCommitHashNotOrderByCreatedAtDesc(ProjectEntity project, String initial);
}
