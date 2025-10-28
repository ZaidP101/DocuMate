package research.project.documate.backend.Backend.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import research.project.documate.backend.Backend.DTOs.ProjectRegistrationDTO;
import research.project.documate.backend.Backend.DTOs.ProjectResponseDTO;
import research.project.documate.backend.Backend.Entity.ProjectEntity;
import research.project.documate.backend.Backend.Repository.ProjectRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public List<ProjectResponseDTO> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ProjectResponseDTO convertToDTO(ProjectEntity projectEntity) {
        ProjectResponseDTO dto = new ProjectResponseDTO();
        dto.setId(projectEntity.getId());
        dto.setTitle(projectEntity.getTitle());
        dto.setGitRepoLink(projectEntity.getGitRepoLink());
        dto.setLocalPath(projectEntity.getLocalPath());
        dto.setTemplate(projectEntity.getTemplate());
        dto.setCreatedAt(projectEntity.getCreatedAt());
        dto.setUpdatedAt(projectEntity.getUpdatedAt());
        return dto;
    }

    public ProjectResponseDTO createProject(ProjectRegistrationDTO dto) {
        validateGitRepository(dto.getLocalPath());

        ProjectEntity project = new ProjectEntity();
        project.setTitle(dto.getTitle());
        project.setGitRepoLink(dto.getGitRepoLink());
        project.setLocalPath(dto.getLocalPath());
        project.setTemplate(dto.getTemplate());
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());

        ProjectEntity savedProject = projectRepository.save(project);

        return convertToDTO(savedProject);
    }

    // Validate Git repository path
    private void validateGitRepository(String localPath) {
        Path gitPath = Paths.get(localPath, ".git");
        if (!Files.exists(gitPath) || !Files.isDirectory(gitPath)) {
            throw new IllegalArgumentException("Not a valid Git repository: .git folder not found");
        }
    }
}
