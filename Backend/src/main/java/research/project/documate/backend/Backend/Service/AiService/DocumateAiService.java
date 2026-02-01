package research.project.documate.backend.Backend.Service.AiService;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class DocumateAiService {
    @Value("${ai.api.url}")
    private String aiApiUrl;

    @Value("${ai.api.key}")
    private String aiApiKey;

    @Value("${ai.model}")
    private String aiModel;

    private final WebClient webClient;

    public DocumateAiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String generateContent(String prompt) {
        // Using Groq/OpenAI JSON structure
        Map<String, Object> requestBody = Map.of(
                "model", aiModel,
                "messages", new Object[]{
                        Map.of("role", "user", "content", prompt)
                }
        );

        try {
            return webClient.post()
                    .uri(aiApiUrl)
                    .header("Authorization", "Bearer " + aiApiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            return "Error while communicating with Groq API: " + e.getMessage();
        }
    }
}
