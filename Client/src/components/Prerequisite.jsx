import React from "react";
import "../styles/Prerequisite.css";
import { GitBranch, TerminalSquare, FileText, Shield, Settings } from "lucide-react";

function Prerequisite() {
    return (
        <div className="prerequisite-container">
            <div className="prerequisite-header">
                <h1>Prerequisites Before Using DocuMate</h1>
                <p>
                    Ensure the following setup and requirements are completed before launching DocuMate
                    to experience a seamless automated documentation workflow.
                </p>
            </div>

            <div className="prerequisite-sections">

                <div className="prerequisite-card">
                    <GitBranch size={32} color="#a855f7" />
                    <h2>1. GitHub Account Setup</h2>
                    <p>
                        Register your GitHub account within DocuMate by providing:
                    </p>
                    <ul>
                        <li>Your <strong>Email address</strong></li>
                        <li><strong>GitHub Username</strong></li>
                        <li><strong>Personal Access Token</strong> (with repo permissions)</li>
                    </ul>
                    <p>
                        This step allows DocuMate to authenticate securely and automate Git operations.
                    </p>
                </div>

                <div className="prerequisite-card">
                    <FileText size={32} color="#a855f7" />
                    <h2>2. Repository File Requirements</h2>
                    <p>
                        Your GitHub repository must include the following files before using DocuMate:
                    </p>
                    <ul>
                        <li>.gitignore</li>
                        <li>.env or .env.example</li>
                        <li>README.md</li>
                        <li>Dockerfile</li>
                    </ul>
                    <p>
                        DocuMate uses these to analyze and auto-update configuration and documentation intelligently.
                    </p>
                </div>

                <div className="prerequisite-card">
                    <TerminalSquare size={32} color="#a855f7" />
                    <h2>3. Repository Linking</h2>
                    <p>
                        You must provide both:
                    </p>
                    <ul>
                        <li><strong>GitHub Repository Link</strong> (remote URL)</li>
                        <li><strong>Local Path</strong> (on your system)</li>
                    </ul>
                    <p>
                        This helps DocuMate verify your repo, install Git hooks, and track changes automatically.
                    </p>
                </div>

                <div className="prerequisite-card">
                    <Settings size={32} color="#a855f7" />
                    <h2>4. System & Environment Setup</h2>
                    <ul>
                        <li>Ensure <strong>Java 17+</strong> is installed to run the <code>documate.jar</code> file.</li>
                        <li>Git should be initialized (<code>git init</code>) and synced with GitHub.</li>
                        <li>DocuMate stores data locally at <code>~/.documate/</code>.</li>
                    </ul>
                    <p>
                        On the first run, post-push Git hooks are installed automatically to detect push events.
                    </p>
                </div>

                <div className="prerequisite-card">
                    <Shield size={32} color="#a855f7" />
                    <h2>5. Security & Privacy</h2>
                    <ul>
                        <li>DocuMate stores all data locally for privacy.</li>
                        <li>Files are only modified after your confirmation.</li>
                        <li>No internet is required except when pushing to GitHub.</li>
                    </ul>
                </div>

            </div>
        </div>
    );
}

export default Prerequisite;
