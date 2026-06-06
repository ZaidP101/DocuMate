# Markdown Formatting Skill

## Description
Ensures all generated documentation follows valid, consistent, and readable Markdown syntax. Supports CommonMark compliance and GitHub-flavored Markdown (GFM) extensions.

## Rules
- Use ATX headings (`# ` to `###### `) with space after `#`
- Separate headings from body text with one blank line
- Use fenced code blocks with language identifiers (```java, ```json, ```bash)
- Tables use aligned dashes (`| --- | --- |`); no inline styles inside cells
- Links use reference-style for repeated URLs: `[text][ref]` then `[ref]: url`
- Lists: `-` for unordered, `1.` for ordered; indent nested items with 2 spaces

## Linting
- Validate output with the README Validator agent post-generation
- Check for: broken links, unclosed fences, trailing spaces, inconsistent list markers

## Application in DocuMate
Applied by all generation agents. The CommonMark library is used for parsing and rendering.
