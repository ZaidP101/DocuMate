
import React from 'react'
import '../styles/DockerComparison.css'
import Navbar from '../components/Navbar'
import SideBar from '../components/SideBar'
import FileCompare from '../components/FileCompare'

function DockerCompare() {
    return (
        <>
            <Navbar />
            <div className="home-container">
                <div className="side-bar-container">

                    <SideBar />
                </div>
                <div className="project-section-container">
                    <FileCompare FileName='Docker' isLimited={true} />

                </div>

            </div>
        </>
    )
}

export default DockerCompare
