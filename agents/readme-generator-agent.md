# README Generator Agent

## Purpose
Generates and maintains comprehensive `README.md` files for projects by analyzing the codebase structure, dependencies, and configurations.

## Capabilities
- Scans project directories to infer tech stack (languages, frameworks, build tools)
- Extracts key metadata from `package.json`, `pom.xml`, `Cargo.toml`, etc.
- Generates standard README sections: Description, Installation, Usage, Contributing, License
- Adapts tone and detail level based on project type (library, CLI, web app, etc.)
- Version-aware — tracks README history and diffs via the backend's `ReadmeFileEntity`

## Usage
Invoke this agent when a new project is registered in DocuMate or when the existing README needs regeneration after significant code changes.

## Interface
- **Input:** Project repository path or analysis DTO (`ProjectAnalysisDTO`)
- **Output:** Generated README content (stored as `ReadmeFileEntity`) with diff preview (`ReadmeDiffDTO`)
- **Endpoints:** `ReadmeController`, `ReadmeAiService`

## Dependencies
- `ProjectAnalysisService` — for codebase analysis
- `ReadmeAiService` — for LLM-powered generation via Groq API
- `GitDiffService` — for diff tracking against previous versions
