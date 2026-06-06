# JSON Extractor Skill

## Description
Parses and extracts structured data from JSON responses returned by the LLM API, as well as from project configuration files like `package.json`.

## Capabilities
- Parses LLM responses that include JSON blocks within Markdown
- Extracts specific fields using configurable JSONPath-like queries
- Validates JSON schema against expected DTO structures
- Handles malformed JSON with fallback parsing (truncation repair, comment stripping)
- Maps extracted values to Java DTOs (`ReadmeGenerationResultDTO`, `DockerGenerationResultDTO`, etc.)

## Usage
Applied after every LLM API call to transform raw text responses into typed objects. Also used when reading `package.json` for project analysis.

## Error Handling
- Attempt repair for common issues (trailing commas, unquoted keys)
- Log warnings for recoverable parse failures
- Throw `JsonParseException` for unrecoverable errors

## Dependencies
- Jackson (`ObjectMapper`) for standard JSON parsing
- Custom regex-based extractor for Markdown-wrapped JSON blocks
