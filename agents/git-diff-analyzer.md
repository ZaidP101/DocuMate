# Git Diff Analyzer Agent

## Purpose
Analyzes Git commit history and working-tree changes to produce structured diffs, enabling intelligent documentation updates and change review.

## Capabilities
- Computes diffs between arbitrary commits, branches, or HEAD vs working tree
- Classifies changes (added, modified, deleted, renamed files)
- Extracts changed method/class signatures from Java/JS files
- Detects breaking changes (API signature changes, dependency version bumps)
- Produces `GitDiffAnalysisDTO` with structured, LLM-friendly diff summaries

## Usage
Triggered on git push events (`GitPushEventDTO`) and manual regeneration requests. Feeds into README, Dockerfile, and env-file regeneration agents so they know what changed.

## Interface
- **Input:** Repository path, optional commit range, or push event payload
- **Output:** `GitDiffAnalysisDTO` with per-file diffs, change classification, and summary stats
- **Backing Service:** `GitDiffService.java`, `GitTriggerService.java`

## Dependencies
- JGit 6.7.0 — for all Git operations
- `GitHookService` — for post-receive hook integration
- `GitCredentialsService` — for authenticated repository access
