import React, { useState } from "react";
import "../styles/Faqs.css";

const faqs = [
    {
        question: "What is DocuMate?",
        answer:
            "DocuMate is a standalone desktop tool that automates README and configuration file generation whenever you push code to Git."
    },
    {
        question: "How do I set it up?",
        answer:
            "Simply download the documate.jar file, launch it with Java 17+, and register your project repository. It installs Git hooks automatically."
    },
    {
        question: "Which project types are supported?",
        answer:
            "DocuMate supports 9 intelligent template categories including AI/ML, Web Dev, Cloud, DevOps, Cybersecurity, and more."
    },
    {
        question: "Does it work offline?",
        answer:
            "Yes, DocuMate is designed as a fully offline tool with all data stored locally in your system’s home directory."
    },
    {
        question: "What happens after I push my code?",
        answer:
            "DocuMate detects the Git push event, analyzes changes, and generates updated files. You can review diffs before confirming the push."
    },
    {
        question: "Can I customize the templates?",
        answer:
            "Yes, templates can be extended or replaced in future versions. Community template contributions are part of the roadmap."
    },
    {
        question: "Do I need to install Git separately?",
        answer:
            "No, DocuMate uses JGit — a pure Java implementation of Git — which means it does not depend on any external Git installation."
    },
    {
        question: "Where is my data stored?",
        answer:
            "All project data is stored locally in your system’s home directory under the path ~/.documate/. DocuMate never sends data to external servers."
    },
    {
        question: "Can I edit generated files manually?",
        answer:
            "Yes, every generated file (like README, Dockerfile, or .env.example) can be reviewed and edited within the app before being committed and pushed."
    },
    {
        question: "What technologies power DocuMate?",
        answer:
            "DocuMate is built using Spring Boot for the backend and React (Electron) for the desktop frontend, with an embedded H2 database for local storage."
    },
    {
        question: "Is DocuMate open source?",
        answer:
            "Currently, DocuMate is a private project, but future versions aim to provide community-driven template contributions and plugin support."
    },
    {
        question: "Can I integrate it with my CI/CD pipeline?",
        answer:
            "Not yet, but future updates plan to include CI/CD and project management tool integrations for automated documentation in team workflows."
    },
    {
        question: "Does DocuMate support multiple projects?",
        answer:
            "Yes, the home dashboard displays all your registered repositories, allowing you to switch, manage, and generate files for multiple projects easily."
    },
    {
        question: "What system requirements are needed?",
        answer:
            "You just need Java 17 or higher installed. DocuMate runs on Windows, macOS, and Linux with zero external dependencies."
    },
    {
        question: "Is my code secure while using DocuMate?",
        answer:
            "Absolutely. DocuMate never accesses files outside registered project directories and performs only safe Git operations — no rebases or force pushes."
    },
    {
        question: "Can I revert changes made by DocuMate?",
        answer:
            "Yes, DocuMate maintains a version history of generated files in its local database, allowing you to compare and roll back previous file states."
    }
];


function FaqComponent() {
    const [activeIndex, setActiveIndex] = useState(null);

    const toggleFAQ = (index) => {
        setActiveIndex(activeIndex === index ? null : index);
    };

    return (
        <div className="faqs-container">
            <h1 className="faqs-title">Frequently Asked Questions</h1>
            {faqs.map((faq, index) => (
                <div
                    key={index}
                    className={`faq-item ${activeIndex === index ? "active" : ""}`}
                    onClick={() => toggleFAQ(index)}
                >
                    <div className="faq-question">{faq.question}</div>
                    <div className="faq-answer">{faq.answer}</div>
                </div>
            ))}
        </div>
    );
}

export default FaqComponent;
