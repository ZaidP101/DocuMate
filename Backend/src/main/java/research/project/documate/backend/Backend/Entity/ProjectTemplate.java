package research.project.documate.backend.Backend.Entity;

public enum ProjectTemplate {
    AI_ML("Artificial Intelligence & Machine Learning"),
    DATA_SCIENCE("Data Science & Analytics"),
    WEB_MOBILE("Web & Mobile Development"),
    CLOUD_DEVOPS("Cloud Computing & DevOps"),
    CYBERSECURITY("Cybersecurity & Network Security"),
    SOFTWARE_MANAGEMENT("Software Management System"),
    BLOCKCHAIN("Blockchain & FinTech"),
    GAME_DEV("Game Development"),
    ED_TECH("Educational / EdTech");

    private final String displayName;

    ProjectTemplate(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
