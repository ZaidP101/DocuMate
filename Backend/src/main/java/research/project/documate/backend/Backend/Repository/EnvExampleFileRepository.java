package research.project.documate.backend.Backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import research.project.documate.backend.Backend.Entity.EnvExampleFileEntity;

@Repository
public interface EnvExampleFileRepository extends JpaRepository<EnvExampleFileEntity, Long> {
}
