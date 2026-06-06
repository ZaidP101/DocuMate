# Code Structure Analyzer Agent

## Purpose
Analyzes a project's codebase to extract structural metadata: directory tree, file types, dependency graph, framework detection, and build configuration.

## Capabilities
- Recursively walks the project directory (excluding `node_modules`, `.git`, `target/`)
- Detects programming languages and frameworks (React, Spring Boot, etc.)
- Parses dependency files (`pom.xml`, `package.json`, `requirements.txt`)
- Classifies project type (web app, CLI tool, library, monorepo)
- Produces a structured `ProjectAnalysisDTO` consumed by other agents

## Usage
Runs automatically on project registration. Also triggered on git push events to detect structural changes.

## Interface
- **Input:** Repository path or project ID
- **Output:** `ProjectAnalysisDTO` containing language stats, dependency list, directory tree, and inferred metadata
- **Backing Service:** `ProjectAnalysisService.java`

## Dependencies
- Apache Commons IO for file traversal
- JGit for repository-level analysis
- Custom heuristics for framework detection (e.g., presence of `vite.config.js` → Vite/React)
