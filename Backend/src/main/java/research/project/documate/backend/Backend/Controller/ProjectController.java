package research.project.documate.backend.Backend.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import research.project.documate.backend.Backend.DTOs.ProjectRegistrationDTO;
import research.project.documate.backend.Backend.DTOs.ProjectResponseDTO;
import research.project.documate.backend.Backend.Service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@AllArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping
    public List<ProjectResponseDTO> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody ProjectRegistrationDTO dto) {
        try {
            ProjectResponseDTO response = projectService.createProject(dto);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error creating project: " + e.getMessage());
        }
    }
}
