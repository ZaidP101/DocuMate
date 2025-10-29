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

    public String getDomainGroup() {
        switch (this) {
            case AI_ML:
            case DATA_SCIENCE:
                return "AI_DATA";
            case WEB_MOBILE:
            case SOFTWARE_MANAGEMENT:
            case ED_TECH:
                return "WEB_SOFTWARE";
            case CLOUD_DEVOPS:
            case CYBERSECURITY:
                return "CLOUD_INFRA";
            case BLOCKCHAIN:
            case GAME_DEV:
                return "EMERGING_TECH";
            default:
                return "WEB_SOFTWARE";
        }
    }
}
