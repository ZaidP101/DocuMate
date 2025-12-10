# DocuMate

DocuMate is an intelligent documentation automation platform that generates and maintains essential project files like READMEs, Dockerfiles, .env examples, and .gitignore files automatically. It analyzes your codebase, detects changes through Git operations, and provides AI-powered suggestions to keep your documentation always in sync with your code.

## Key Features

**Smart README Generation**
Auto-generates comprehensive README.md files based on project analysis
Updates documentation on every Git push with change detection
AI-powered content generation with customizable prompts
Side-by-side diff view for reviewing changes
Version history for all documentation changes

**Dockerfile Automation**
Generates optimized, production-ready Dockerfiles
Multi-stage builds with security best practices
Automatic dependency detection and configuration
Customizable through AI prompts
One-click deployment to project

**Environment Configuration**
Auto-generates .env.example files with required variables
Detects environment dependencies from your codebase
Provides secure configuration templates
Keeps environment documentation updated

**Gitignore Management**
Smart .gitignore generation based on project type
Technology-specific ignore patterns
Custom rule addition through AI prompts
Automatic updates as project evolves

**Git Integration**
Automatic triggers on git push operations
Real-time diff analysis of code changes
Seamless integration with existing workflows
No disruption to developer workflow

## How It Works

1. Project Registration: Add your Git repository to DocuMate
2. Code Analysis: System analyzes project structure, dependencies, and code patterns
3. Auto-Generation: AI generates initial versions of README, Dockerfile, .env.example, and .gitignore
4. Git Integration: On every git push, DocuMate detects changes and generates updates
5. Review & Approve: Side-by-side diff view shows proposed changes
6. One-Click Deployment: Approve changes to automatically update files and commit

## Architecture

```bash
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   Frontend      │    │   Spring Boot    │    │   H2            │
│   (React)       │◄──►│   Backend        │◄──►│   Database      │
│                 │    │                  │    │                 │
└─────────────────┘    └──────────────────┘    └─────────────────┘
         │                       │                       │
         │                       │                       │
         ▼                       ▼                       ▼
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   Git Hooks     │    │   AI Service     │    │   File System   │
│   (Auto-trigger)│    │   (Gemini API)   │    │   Operations    │
└─────────────────┘    └──────────────────┘    └─────────────────┘
```

## Supported File Types

| File Type | Features | Auto-Update |
|-----------|----------|-------------|
| README.md | Project overview, installation, usage, API docs | On Git push |
| Dockerfile | Multi-stage builds, security, optimization | On project changes |
| .env.example | Environment variables, configuration templates | On project changes |
| .gitignore | Technology-specific patterns, custom rules | On project changes |

## Tech Stack

- Backend: Spring Boot, JPA/Hibernate, JGit
- AI Service: Google Gemini API
- Database: PostgreSQL with version history
- Git Integration: JGit for repository operations
- File Processing: Java NIO for filesystem operations
- API: RESTful endpoints for all operations

## Installation & Setup

### Prerequisites
- Java 17 or higher
- H2 Database
- Git
- Google Gemini API key

### Quick Start

1. Clone & Build:
```bash
git clone https://github.com/your-org/documate.git
cd documate
./mvnw clean install
```

2. Configuration:
Add to application.properties:
```bash
gemini.api.key=your_gemini_api_key
gemini.api.url=https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent
spring.datasource.url=jdbc:postgresql://localhost:5432/documate
```

## Usage

### 1. Add Your Project
POST /api/projects
```bash
{
"title": "My Awesome Project",
"gitRepoLink": "https://github.com/user/repo",
"localPath": "/path/to/your/project",
"template": "WEB_SOFTWARE"
}
```

### 2. Generate Documentation
```bash
Generate all files
- POST /api/projects/1/generate-all

Or generate specific files
- POST /api/docker/1/generate
- POST /api/readme/1/generate
```

### 3. Review & Approve Changes
```bash
View generated diff
- GET /api/readme/1/diff

Approve and deploy
- POST /api/readme/1/push

{
"content": "final_content"
}
```

## API Endpoints

### Projects
- POST /api/projects - Register new project
- GET /api/projects - List all projects
- GET /api/projects/{id} - Get project details
- DELETE /api/projects/{id} - Remove project

### README Management
- POST /api/readme/{projectId}/generate - Generate README
- GET /api/readme/{projectId}/diff - View changes
- POST /api/readme/{projectId}/push - Deploy README
- POST /api/readme/{projectId}/regenerate - AI regeneration

### Dockerfile Management
- POST /api/docker/{projectId}/generate - Generate Dockerfile
- POST /api/docker/{projectId}/push - Deploy Dockerfile
- POST /api/docker/{projectId}/regenerate - AI regeneration

### Git Integration
- POST /api/git/push-trigger - Manual Git trigger
- GET /api/git/{projectId}/hooks - Manage Git hooks

## Security Features

- Local-First: All processing happens on your infrastructure
- API Key Security: Secure Gemini API integration
- Git Security: Read-only repository access
- Data Privacy: No code sent to external services without consent
- Approval Workflow: No automatic file modifications

## Future Roadmap

- VS Code Extension - In-editor documentation assistance
- CI/CD Integration - Automated documentation in pipelines
- Team Collaboration - Multi-user review workflows
- Template Marketplace - Community documentation templates
- Local AI Models - Offline documentation generation
- Multi-language Support - Beyond English documentation

## Contributing

We welcome contributions! Please see our Contributing Guidelines for details.

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Maintainers

### Zaid Patel 
- Backend
- zpatel044@gmail.com
- LinkedIn: www.linkedin.com/in/zaid-patel-ba5950222

### Amir Khan
- Frontend
- amirkhan11691@gmail.com
- LinkedIn: www.linkedin.com/in/amir-khan-17159224a?utm_source=share_via&utm_content=profile&utm_medium=member_android

DocuMate - Because great code deserves great documentation!

