package research.project.documate.backend.Backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String gitRepoLink;
    private String localPath;

    @Enumerated(EnumType.STRING)
    private ProjectTemplate template;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

//    @OneToOne(cascade = CascadeType.ALL) // Cascade - when delete project, all project related gets deleted
//    @JoinColumn(name = "readme_file_id")
//    private ReadmeFileEntity currentReadme;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "docker_file_id")
//    private DockerFileEntity currentDockerFile;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "env_example_file_id")
//    private EnvExampleFileEntity currentEnvExample;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "gitignore_file_id")
//    private GitignoreFileEntity currentGitignore;
}
