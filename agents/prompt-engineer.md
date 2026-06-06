# Prompt Engineer Agent

## Purpose
Constructs, optimizes, and manages LLM prompts used across DocuMate's AI services for documentation generation and analysis.

## Capabilities
- Builds domain-specific prompts for README, Dockerfile, `.env.example`, and `.gitignore` generation
- Injects contextual data (code structure, git diffs, previous versions) into prompt templates
- Optimizes prompt length and structure for token efficiency and output quality
- Supports model-specific formatting (currently tuned for `llama-3.3-70b-versatile` via Groq)
- Maintains prompt versioning for reproducibility and A/B testing

## Usage
Used internally by all `*AiService` classes (`ReadmeAiService`, `DockerAiService`, etc.) when crafting LLM requests. Not directly invoked by users.

## Interface
- **Input:** Context data (DTOs), generation type, optional style parameters
- **Output:** Formatted prompt string ready for LLM API submission
- **Pattern:** Template method pattern — base prompt structure with injectable sections

## Dependencies
- `DocumateAiService` — base AI service with shared prompt logic
- CommonMark library — for Markdown-aware prompt construction
- Model configuration from `application.properties`
