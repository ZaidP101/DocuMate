import React from 'react'
import '../styles/EnvComparison.css'
import Navbar from '../components/Navbar'
import SideBar from '../components/SideBar'
import DocumateWorkflowComponent from '../components/DocumateWorkflowComponent'
function DocumateWorkflow() {
    return (
        <>
            <Navbar />
            <div className="home-container">
                <div className="side-bar-container">

                    <SideBar />
                </div>
                <div className="project-section-container">
                    <DocumateWorkflowComponent />

                </div>

            </div>

        </>
    )
}

export default DocumateWorkflow
