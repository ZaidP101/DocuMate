import React from "react";
import { FileCode2, GitCommit, Workflow } from "lucide-react";
import "../styles/DocuMateSections.css";

const About = () => {
    return (
        <section className="doc-section">
            <h2 className="doc-section-title">About DocuMate</h2>
            <p className="doc-text">
                <strong>DocuMate</strong> — “Your Code’s Story, Automatically Told” — is an intelligent documentation
                tool that automates README, Dockerfile, and configuration file generation whenever Git operations occur.
                It’s a standalone cross-platform desktop app powered by <strong>Spring Boot</strong> and{" "}
                <strong>Electron (React)</strong>, requiring no manual setup or external dependencies.
            </p>

            <div className="doc-grid">
                <div className="doc-card centered">
                    <FileCode2 className="doc-icon-large" />
                    <h4 className="doc-card-title">Smart File Generation</h4>
                    <p className="doc-card-desc">
                        Automatically creates Dockerfile, .env.example, and .gitignore based on project templates.
                    </p>
                </div>

                <div className="doc-card centered">
                    <GitCommit className="doc-icon-large" />
                    <h4 className="doc-card-title">Seamless Git Integration</h4>
                    <p className="doc-card-desc">
                        Auto-triggers on Git push events, generating intelligent diffs and commits with one click.
                    </p>
                </div>

                <div className="doc-card centered">
                    <Workflow className="doc-icon-large" />
                    <h4 className="doc-card-title">Template Intelligence</h4>
                    <p className="doc-card-desc">
                        Offers 9 predefined project templates — from AI/ML to EdTech — for tailored documentation.
                    </p>
                </div>
            </div>
        </section>
    );
};

export default About;
