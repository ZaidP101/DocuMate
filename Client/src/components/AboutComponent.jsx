import React from 'react'
import '../styles/AboutComponent.css'

function AboutComponent() {
    return (
        <>
            <div className="about-container">
                <div className="about-header">
                    <h1>About DocuMate</h1>
                    <p>Your Code’s Story, Automatically Told</p>
                </div>

                <div className="about-section">
                    <h2>Overview</h2>
                    <p>
                        DocuMate is a cross-platform desktop tool that automates project
                        documentation and configuration file generation. It integrates
                        seamlessly with Git operations, creating intelligent README and config
                        updates the moment you push your code.
                    </p>
                </div>

                <div className="about-section">
                    <h2>Key Features</h2>
                    <ul>
                        <li>Automatic README and config generation on Git push</li>
                        <li>9 category-based intelligent templates (AI/ML, Web Dev, DevOps, etc.)</li>
                        <li>Side-by-side diff viewer with editing support</li>
                        <li>One-click Git operations (add, commit, push)</li>
                        <li>Local file storage using an embedded H2 database</li>
                        <li>Generates Dockerfile, .env.example, and .gitignore automatically</li>
                    </ul>
                </div>

                <div className="about-section">
                    <h2>Technology Stack</h2>
                    <p>
                        <strong>Backend:</strong> Spring Boot (Java 17+) with JGit integration<br />
                        <strong>Frontend:</strong> React (Electron-based cross-platform UI)<br />
                        <strong>Database:</strong> Embedded H2 SQL (file-based)<br />
                        <strong>Template Engine:</strong> Thymeleaf<br />
                        <strong>Packaging:</strong> Maven Shade Plugin (Single JAR file)
                    </p>
                </div>

                <div className="about-section">
                    <h2>Why DocuMate?</h2>
                    <p>
                        By automating repetitive documentation and configuration tasks,
                        DocuMate helps developers focus on coding while ensuring consistent,
                        accurate, and intelligent project documentation — all from a single
                        unified interface.
                    </p>
                </div>
            </div>
        </>
    )
}

export default AboutComponent
