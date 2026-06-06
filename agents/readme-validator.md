# README Validator Agent

## Purpose
Validates generated and user-provided `README.md` files for structural completeness, formatting correctness, and content quality.

## Capabilities
- Checks presence of required sections (Description, Installation, Usage, License)
- Validates Markdown syntax (broken links, unclosed code fences, malformed tables)
- Flags placeholder content ("TODO", "Coming Soon", lorem ipsum)
- Verifies consistency between README and actual project structure
- Suggests improvements for clarity, tone, and discoverability

## Usage
Runs after every README generation cycle. Can also be invoked manually to validate an existing README.

## Interface
- **Input:** README content (string or `FileContentDTO`) and optional `ProjectAnalysisDTO`
- **Output:** Validation report with warnings, errors, and suggestions
- **Integration:** Hooks into `ReadmeService` post-generation pipeline

## Validation Rules
| Rule | Description |
|------|-------------|
| Required sections | Title, Description, Installation, Usage, License |
| Link integrity | All `[text](url)` links are reachable |
| Code fences | All ` ``` ` blocks are properly closed |
| Placeholder check | No "TODO" or filler content |
| Consistency | Listed tech matches `ProjectAnalysisDTO` |
