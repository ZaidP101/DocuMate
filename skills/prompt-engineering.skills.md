# Prompt Engineering Skill

## Description
Designs effective LLM prompts tailored to documentation generation tasks. Focuses on context injection, output structuring, and iterative refinement.

## Techniques
- **Role prompting:** Assign the LLM a persona (e.g., "You are a senior technical writer")
- **Few-shot examples:** Include example inputs and expected outputs
- **Structured output:** Request Markdown or JSON with explicit formatting instructions
- **Context sandwich:** Place the most critical instructions at the beginning and end

## Best Practices
- Keep prompts concise to reduce token waste
- Inject dynamic context (diffs, project structure) via template variables
- Use negative instructions sparingly ("Do not include...")
- Validate outputs against schema post-generation

## Application in DocuMate
Defines the prompt templates used by `DocumateAiService` and all domain-specific `*AiService` classes.
