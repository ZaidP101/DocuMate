
import React, { useEffect, useRef, useState } from "react";
import "../styles/DocumateWorkflow.css";

export default function DocumateWorkflowComponent() {

    const leftNodes = [
        { id: "home", title: "Home", desc: "Project selector with +New and project cards." },
        { id: "p4", title: "P4", desc: "Project 4 (example card)." },
        { id: "p5", title: "P5", desc: "Project 5 (example card)." },
    ];

    const rightNodes = [
        { id: "repo", title: "Title / Name", desc: "Repo name, Git link and local path." },
        { id: "repo-meta", title: "Git repo Link", desc: "URL to the Git repository." },
        { id: "local", title: "Local address in PC", desc: "Local path on developer machine." },
    ];


    const containerRef = useRef(null);
    const centerRef = useRef(null);
    const leftRefs = useRef({});
    const rightRefs = useRef({});
    leftNodes.forEach((n) => { if (!leftRefs.current[n.id]) leftRefs.current[n.id] = React.createRef(); });
    rightNodes.forEach((n) => { if (!rightRefs.current[n.id]) rightRefs.current[n.id] = React.createRef(); });

    const [paths, setPaths] = useState([]);
    const [modal, setModal] = useState({ open: false, title: "", desc: "" });

    useEffect(() => {
        function computePaths() {
            const c = containerRef.current;
            const ctr = centerRef.current;
            if (!c || !ctr) return;
            const parentRect = c.getBoundingClientRect();
            const centerRect = ctr.getBoundingClientRect();

            const newPaths = [];


            leftNodes.forEach((n, idx) => {
                const el = leftRefs.current[n.id] && leftRefs.current[n.id].current;
                if (!el) return;
                const r = el.getBoundingClientRect();


                const start = {
                    x: centerRect.left - parentRect.left,
                    y: centerRect.top + centerRect.height * 0.25 - parentRect.top + idx * 6
                };

                const end = {
                    x: r.right - parentRect.left,
                    y: r.top + r.height / 2 - parentRect.top
                };


                const cp1 = { x: start.x - 100, y: start.y };
                const cp2 = { x: end.x + 100, y: end.y };

                newPaths.push({
                    id: `center->${n.id}`,
                    d: `M ${start.x} ${start.y} C ${cp1.x} ${cp1.y} ${cp2.x} ${cp2.y} ${end.x} ${end.y}`,
                });
            });


            rightNodes.forEach((n, idx) => {
                const el = rightRefs.current[n.id] && rightRefs.current[n.id].current;
                if (!el) return;
                const r = el.getBoundingClientRect();


                const start = {
                    x: centerRect.right - parentRect.left,
                    y: centerRect.top + centerRect.height * 0.25 - parentRect.top + idx * 6
                };

                const end = {
                    x: r.left - parentRect.left,
                    y: r.top + r.height / 2 - parentRect.top
                };

                const cp1 = { x: start.x + 100, y: start.y };
                const cp2 = { x: end.x - 100, y: end.y };

                newPaths.push({
                    id: `center->${n.id}`,
                    d: `M ${start.x} ${start.y} C ${cp1.x} ${cp1.y} ${cp2.x} ${cp2.y} ${end.x} ${end.y}`,
                });
            });

            setPaths(newPaths);
        }

        computePaths();
        window.addEventListener("resize", computePaths);
        const mo = new MutationObserver(computePaths);
        if (containerRef.current) mo.observe(containerRef.current, { childList: true, subtree: true });

        return () => {
            window.removeEventListener("resize", computePaths);
            mo.disconnect();
        };
    }, []);

    function openModal(item) {
        setModal({ open: true, title: item.title, desc: item.desc });
    }
    function closeModal() {
        setModal({ open: false, title: "", desc: "" });
    }

    return (
        <div className="dm-root">
            <div className="dm-canvas" ref={containerRef}>
                {/* SVG connectors behind nodes */}
                <svg className="dm-svg" width="100%" height="100%" preserveAspectRatio="none" aria-hidden>
                    <defs>
                        <linearGradient id="gAccent" x1="0%" x2="100%">
                            <stop offset="0%" stopColor="#a855f7" stopOpacity="0.95" />
                            <stop offset="100%" stopColor="#7e22ce" stopOpacity="0.95" />
                        </linearGradient>

                        <marker id="arrow" viewBox="0 0 10 10" refX="10" refY="5" markerWidth="6" markerHeight="6" orient="auto">
                            <path d="M 0 0 L 10 5 L 0 10 z" fill="#a855f7" />
                        </marker>
                    </defs>

                    {paths.map((p) => (
                        <path key={p.id} d={p.d} stroke="url(#gAccent)" strokeWidth="3" fill="none" strokeLinecap="round" markerEnd="url(#arrow)" className="dm-path" />
                    ))}
                </svg>

                {/* Left column */}
                <div className="dm-column left-column">
                    {leftNodes.map((n) => (
                        <div
                            key={n.id}
                            className="dm-box"
                            ref={leftRefs.current[n.id]}
                            onClick={() => openModal(n)}
                        >
                            <div className="dm-box-title">{n.title}</div>
                            <div className="dm-box-sub">Click for details</div>
                        </div>
                    ))}
                </div>

                {/* Center area with Documate */}
                <div className="dm-center">
                    <div className="dm-main" ref={centerRef} onClick={() => openModal({ title: "Documate Workflow", desc: "Core overview and orchestration node." })}>
                        <div className="dm-main-title">Documate Workflow</div>
                    </div>

                    {/* below center: template -> diff -> push chain */}
                    <div className="dm-stack">
                        <div className="dm-box stack-item" onClick={() => openModal({ title: "Select Template", desc: "Choose README template (T1..T4)." })}>
                            <div className="dm-box-title">Select Template</div>
                        </div>

                        <div className="dm-box stack-item" onClick={() => openModal({ title: "Diff", desc: "Old vs New README - Prompt or Push." })}>
                            <div className="dm-box-title">Diff (Old / New Readme)</div>
                        </div>

                        <div className="dm-box stack-item" onClick={() => openModal({ title: "Push", desc: "Push changes back to repo and return home." })}>
                            <div className="dm-box-title">Push / Finalize</div>
                        </div>
                    </div>
                </div>

                {/* Right column */}
                <div className="dm-column right-column">
                    {rightNodes.map((n) => (
                        <div
                            key={n.id}
                            className="dm-box"
                            ref={rightRefs.current[n.id]}
                            onClick={() => openModal(n)}
                        >
                            <div className="dm-box-title">{n.title}</div>
                            <div className="dm-box-sub">Click for details</div>
                        </div>
                    ))}
                </div>
            </div>

            {/* centered modal */}
            {modal.open && (
                <div className="dm-modal-overlay" onClick={closeModal}>
                    <div className="dm-modal" onClick={(e) => e.stopPropagation()}>
                        <div className="dm-modal-title">{modal.title}</div>
                        <div className="dm-modal-desc">{modal.desc}</div>
                        <div style={{ textAlign: "right", marginTop: 12 }}>
                            <button className="dm-btn" onClick={closeModal}>Close</button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}
