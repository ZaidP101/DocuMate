import React from 'react'
import '../styles/EnvComparison.css'
import Navbar from '../components/Navbar'
import SideBar from '../components/SideBar'
import Prerequisite from '../components/Prerequisite'
function PrerequisitePage() {
    return (
        <>
            <Navbar />
            <div className="home-container">
                <div className="side-bar-container">

                    <SideBar />
                </div>
                <div className="project-section-container">
                    <Prerequisite />

                </div>

            </div>

        </>
    )
}

export default PrerequisitePage
