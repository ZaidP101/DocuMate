package research.project.documate.backend.Backend.Service.AiService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import research.project.documate.backend.Backend.DTOs.EnvExample.EnvExampleGenerationResultDTO;
import research.project.documate.backend.Backend.DTOs.Project.ProjectAnalysisDTO;
import research.project.documate.backend.Backend.Entity.ProjectEntity;

@Service
@Slf4j
@AllArgsConstructor
public class EnvExampleAiService {
    private final DocumateAiService documateAiService;

    public EnvExampleGenerationResultDTO generateEnvExample(ProjectEntity project, ProjectAnalysisDTO analysis, String currentContent) {
        String prompt = createEnvExamplePrompt(project, analysis, currentContent);
        String aiResponse = documateAiService.generateContent(prompt);
        return processEnvExampleResponse(project, aiResponse);
    }

    public EnvExampleGenerationResultDTO regenerateWithPrompt(ProjectEntity project, String currentContent, String userPrompt) {
        String prompt = String.format("""
            You are a DevOps expert. Modify the existing .env.example file based on the user's requirements.
            
            IMPORTANT: Keep all the original content and structure unless specifically asked to change it.
            Only make the changes requested by the user.
            
            Current .env.example:
            %s
            
            User requested changes:
            %s
            
            Instructions:
            1. Apply ONLY the changes requested by the user
            2. Preserve all other aspects of the .env.example file
            3. Maintain proper environment variable syntax and formatting
            4. Include helpful comments for each variable
            5. Return ONLY the complete .env.example content without any markdown formatting or explanations
            6. Do not wrap the response in code blocks or add any preamble
            
            Return the complete modified .env.example:
            """, currentContent, userPrompt);

        String aiResponse = documateAiService.generateContent(prompt);
        return processEnvExampleResponse(project, aiResponse);
    }

    private String createEnvExamplePrompt(ProjectEntity project, ProjectAnalysisDTO analysis, String currentContent) {
        return String.format("""
            Create an optimized .env.example file for this project:
            
            Project: %s
            Type: %s
            Main Language: %s
            Build Tool: %s
            Architecture: %s
            Key Files: %s
            Dependencies: %s
            Project Structure: %s
            
            Current .env.example (if exists):
            %s
            
            Please create a comprehensive .env.example file with:
            - Database configuration variables
            - API keys and external service configurations
            - Application-specific settings
            - Development, staging, and production environment variables
            - Security-related configurations
            - Port and host configurations
            - Feature flags and toggle variables
            
            Guidelines:
            1. Use clear, descriptive variable names
            2. Add helpful comments explaining each variable
            3. Include example values that show the expected format
            4. Group related variables together
            5. Mark required variables clearly
            6. Include common variables for the project type and dependencies
            
            Return only the .env.example content without explanations.
            Use proper environment file syntax with comments.
            """,
                project.getTitle(),
                analysis.getProjectType(),
                analysis.getMainLanguage(),
                analysis.getBuildTool(),
                analysis.getArchitectureType(),
                analysis.getKeyFiles(),
                analysis.getDependencies(),
                analysis.getFileStructure(),
                currentContent
        );
    }

    private EnvExampleGenerationResultDTO processEnvExampleResponse(ProjectEntity project, String aiResponse) {
        try {
            log.info("Raw EnvExample AI Response: {}", aiResponse);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(aiResponse);
            JsonNode textNode = rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text");

            String envExampleContent = textNode.asText()
                    .replaceAll("```env\\n?", "")
                    .replaceAll("```ini\\n?", "")
                    .replaceAll("```\\n?", "")
                    .trim();

            log.info("Extracted EnvExample content: {}", envExampleContent);

            return EnvExampleGenerationResultDTO.builder()
                    .projectId(project.getId())
                    .content(envExampleContent)
                    .changeSummary(".env.example generated based on project analysis")
                    .isAutoGenerated(true)
                    .build();

        } catch (Exception e) {
            log.error("Error processing EnvExample AI response", e);
            return createDefaultEnvExample(project);
        }
    }

    private EnvExampleGenerationResultDTO createDefaultEnvExample(ProjectEntity project) {
        String defaultContent = """
            # Database Configuration
            DB_HOST=localhost
            DB_PORT=5432
            DB_NAME=your_database_name
            DB_USER=your_username
            DB_PASSWORD=your_password
            
            # Application Settings
            APP_PORT=3000
            APP_ENV=development
            APP_DEBUG=true
            APP_URL=http://localhost:3000
            
            # API Keys and External Services
            API_KEY=your_api_key_here
            EXTERNAL_API_URL=https://api.example.com
            
            # Security
            JWT_SECRET=your_jwt_secret_key_here
            ENCRYPTION_KEY=your_encryption_key_here
            
            # Feature Flags
            FEATURE_NEW_UI=false
            FEATURE_EXPERIMENTAL_API=false
            
            # Logging
            LOG_LEVEL=info
            LOG_FILE=app.log
            """;

        return EnvExampleGenerationResultDTO.builder()
                .projectId(project.getId())
                .content(defaultContent)
                .changeSummary("Default .env.example created")
                .isAutoGenerated(true)
                .build();
    }
}
