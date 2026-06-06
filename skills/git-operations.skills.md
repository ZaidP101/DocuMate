# Git Operations Skill

## Description
Performs Git repository operations — cloning, fetching, diffing, committing, and hook management — required for DocuMate's automated documentation workflow.

## Commands & Patterns
- Clone with auth: `Git.cloneRepository().setURI(url).setCredentialsProvider(provider).call()`
- Diff between commits: `git.diff().setOldTree(oldTree).setNewTree(newTree).call()`
- Post-receive hook: Script placed in `.git/hooks/post-receive` to trigger analysis on push

## Safety Rules
- Never force-push or rewrite history
- Always verify remote connectivity before clone/fetch
- Sanitize credentials in logs and error messages
- Respect `.gitignore` during file traversal

## Application in DocuMate
Powers `GitDiffService`, `GitTriggerService`, and `GitHookService`. Uses JGit 6.7.0 for all operations. Credentials are stored encrypted via `GitCredentialsEntity`.
