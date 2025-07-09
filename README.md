# ReadMePilot

**ReadMePilot** is a cross-platform desktop application that automates the process of updating your project's `README.md` file based on changes detected during Git operations. It intelligently analyzes file diffs, suggests contextual updates, and allows developers to review and approve changes through a built-in interface. The goal is to make documentation frictionless, consistent, and version-controlled without disrupting the developer's workflow.

---

## Key Features

- Detects Git commands like `add`, `commit`, and `push`
- Analyzes project file changes using Git diff logic
- Generates intelligent `README.md` suggestions based on context
- Shows a side-by-side diff of current and proposed README
- In-app editing and approval workflow before changes are applied
- Version history stored locally in PostgreSQL
- Fully local setup with no external API calls or cloud dependency

---

## Requirements

- Java 17 or higher (for Git integration, file watcher, UI)
- Python 3.9+ (for Django backend)
- PostgreSQL (for persistent data and README history)
- Git (installed and configured)
- OS: Windows, macOS, or Linux

---

## How It Works

1. User performs a Git operation such as `commit`.
2. A Git hook (pre-commit or post-commit) is triggered via the Java Git engine.
3. The Java service analyzes the Git diff using JGit and generates a suggested update for the `README.md` file.
4. The system pulls the last saved version of the README from the PostgreSQL database via the Django backend.
5. A desktop GUI (JavaFX) opens, showing a side-by-side comparison of the old and proposed README content.
6. The user can edit the proposed content or approve it directly.
7. Upon approval, the `README.md` file is updated, and a version snapshot is saved to the database.
8. If rejected, no changes are made, and the Git operation proceeds normally.

---

## Architecture Overview

- **Java (JGit)**: Handles Git operations, watches for Git activity, and generates diffs
- **JavaFX GUI**: Displays the diff and manages user approval/editing
- **Django (Python)**: Hosts API endpoints and manages PostgreSQL ORM
- **PostgreSQL**: Stores registered projects, README history, and metadata
- **REST API**: Facilitates communication between the Java client and Django backend
- **System Tray Service**: Java daemon runs in background, triggered by Git hooks or file system events

---

## Tech Stack

| Component        | Technology     |
|------------------|----------------|
| Git Engine       | Java + JGit    |
| Background Logic | Java Daemon    |
| UI               | JavaFX         |
| Backend API      | Django (Python)|
| Database         | PostgreSQL     |
| Communication    | REST (JSON)    |
| Markdown Diff    | RichTextFX / MarkdownFX |

---

## How to Use

1. Install the application on your system.
2. Open the application and register your local Git project folders.
3. Perform Git operations (`add`, `commit`, `push`) as usual.
4. When changes are detected, the app automatically launches a side-by-side diff view.
5. Review and optionally edit the suggested README update.
6. Approve to apply the change or skip to leave it untouched.
7. All accepted changes are versioned and stored locally.

---

## Security

- **Local Storage Only**: All data is stored locally on your system; no cloud sync or external API calls.
- **Explicit Approval**: No files are modified without user confirmation.
- **Scoped Access**: The tool only reads Git metadata and the `README.md` file; no other project files are accessed.
- **Process Isolation**: Git logic, UI rendering, and data storage run in isolated processes for better security and stability.

---

## Future Enhancements

- AI-generated README suggestions using local models or optional API integrations
- CLI version for headless environments or automation pipelines
- Team-based collaboration features with change suggestions and approvals
- Auto-generation of Dockerfiles and `docker-compose.yml` based on tech stack
- GitHub/GitLab integration via webhooks or API for shared repositories

---

## License

This project is licensed under the MIT License.

---

## Maintainers

Developed and maintained by:

- **Zaid Patel**
- Contact: `zpatel044@gmail.com`

