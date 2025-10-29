package research.project.documate.backend.Backend.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import research.project.documate.backend.Backend.DTOs.ProjectAnalysisDTO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class ProjectAnalysisService {
    public ProjectAnalysisDTO analyzeProject(String localPath) {
        try {
            ProjectAnalysisDTO analysis = new ProjectAnalysisDTO();
            analysis.setFileStructure(scanProjectStructure(localPath));
            analysis.setDependencies(extractDependencies(localPath));
            analysis.setProjectType(detectProjectType(localPath));
            analysis.setKeyFiles(identifyKeyFiles(localPath));
            analysis.setMainLanguage(detectMainLanguage(localPath));
            analysis.setBuildTool(detectBuildTool(localPath));
            analysis.setArchitectureType(detectArchitecture(localPath));
            analysis.setSubProjects(identifySubProjects(localPath));

            return analysis;
        } catch (Exception e) {
            log.error("Error analyzing project at path: {}", localPath, e);
            throw new RuntimeException("Failed to analyze project", e);
        }
    }
    private String detectArchitecture(String localPath) throws IOException {
        boolean hasBackend = hasBackendStructure(localPath);
        boolean hasFrontend = hasFrontendStructure(localPath);

        if (hasBackend && hasFrontend) return "FULL_STACK";
        if (hasBackend) return "BACKEND_ONLY";
        if (hasFrontend) return "FRONTEND_ONLY";
        return "MONOLITHIC";
    }

    private boolean hasBackendStructure(String localPath) {
        String[] backendIndicators = {
                "src/main/java", "pom.xml", "build.gradle", "package.json",
                "requirements.txt", "app.py", "main.go", "Dockerfile",
                "src/", "controllers/", "services/", "models/"
        };
        return checkFilesExist(localPath, backendIndicators);
    }

    private boolean hasFrontendStructure(String localPath) {
        String[] frontendIndicators = {
                "package.json", "src/components/", "public/", "src/App.js",
                "src/App.tsx", "index.html", "styles/", "assets/",
                "vue.config.js", "angular.json"
        };
        return checkFilesExist(localPath, frontendIndicators);
    }

    private List<String> identifySubProjects(String localPath) throws IOException {
        List<String> subProjects = new ArrayList<>();

        // Check for common sub-project directories
        String[] possibleSubProjects = {"backend", "frontend", "server", "client", "api", "web"};

        for (String dir : possibleSubProjects) {
            Path subPath = Paths.get(localPath, dir);
            if (Files.exists(subPath) && Files.isDirectory(subPath)) {
                // Analyze the sub-project
                String subType = detectProjectType(subPath.toString());
                if (!"UNKNOWN".equals(subType)) {
                    subProjects.add(dir + " (" + subType + ")");
                }
            }
        }
        return subProjects;
    }

    private boolean checkFilesExist(String localPath, String[] files) {
        for (String file : files) {
            if (Files.exists(Paths.get(localPath, file))) {
                return true;
            }
        }
        return false;
    }

    private Map<String, String> scanProjectStructure(String localPath) throws IOException {
        Map<String, String> structure = new HashMap<>();
        Files.walk(Paths.get(localPath))
                .filter(path -> !path.toString().contains(".git"))
                .filter(path -> !path.toString().contains("node_modules"))
                .forEach(path -> {
                    String relativePath = Paths.get(localPath).relativize(path).toString();
                    if (!relativePath.isEmpty()) {
                        structure.put(relativePath, Files.isDirectory(path) ? "DIR" : "FILE");
                    }
                });
        return structure;
    }

    private List<String> extractDependencies(String localPath) {
        List<String> dependencies = new ArrayList<>();

        // Check package.json
        Path packageJson = Paths.get(localPath, "package.json");
        if (Files.exists(packageJson)) {
            dependencies.add("Node.js project - check package.json for specific dependencies");
        }

        // Check pom.xml
        Path pomXml = Paths.get(localPath, "pom.xml");
        if (Files.exists(pomXml)) {
            dependencies.add("Java project - check pom.xml for specific dependencies");
        }

        // Check requirements.txt
        Path requirements = Paths.get(localPath, "requirements.txt");
        if (Files.exists(requirements)) {
            dependencies.add("Python project - check requirements.txt for specific dependencies");
        }

        return dependencies;
    }

    private String detectProjectType(String localPath) {
        if (Files.exists(Paths.get(localPath, "package.json"))) return "NODE_JS";
        if (Files.exists(Paths.get(localPath, "pom.xml"))) return "JAVA";
        if (Files.exists(Paths.get(localPath, "build.gradle"))) return "JAVA";
        if (Files.exists(Paths.get(localPath, "requirements.txt"))) return "PYTHON";
        if (Files.exists(Paths.get(localPath, "Dockerfile"))) return "DOCKER";
        if (Files.exists(Paths.get(localPath, "go.mod"))) return "GO";
        return "GENERAL";
    }

    private List<String> identifyKeyFiles(String localPath) {
        List<String> keyFiles = new ArrayList<>();
        String[] importantFiles = {
                "README.md", "package.json", "pom.xml", "build.gradle",
                "requirements.txt", "Dockerfile", "docker-compose.yml",
                ".env", "src/", "main.py", "app.js", "index.js", "Main.java",
                "application.properties", "config/", "public/", "src/main/", "src/test/"
        };

        for (String file : importantFiles) {
            Path filePath = Paths.get(localPath, file);
            if (Files.exists(filePath)) {
                keyFiles.add(file);
            }
        }
        return keyFiles;
    }

    private String detectMainLanguage(String localPath) {
        if (Files.exists(Paths.get(localPath, "package.json"))) return "JavaScript/TypeScript";
        if (Files.exists(Paths.get(localPath, "pom.xml")) || Files.exists(Paths.get(localPath, "build.gradle"))) return "Java";
        if (Files.exists(Paths.get(localPath, "requirements.txt"))) return "Python";
        if (Files.exists(Paths.get(localPath, "go.mod"))) return "Go";
        if (Files.exists(Paths.get(localPath, "Cargo.toml"))) return "Rust";
        return "Unknown";
    }

    private String detectBuildTool(String localPath) {
        if (Files.exists(Paths.get(localPath, "package.json"))) return "npm/yarn";
        if (Files.exists(Paths.get(localPath, "pom.xml"))) return "Maven";
        if (Files.exists(Paths.get(localPath, "build.gradle"))) return "Gradle";
        if (Files.exists(Paths.get(localPath, "requirements.txt"))) return "pip";
        return "Unknown";
    }
}
