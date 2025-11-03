import React from 'react'
import '../styles/GitIgnoreComparison.css'
import Navbar from '../components/Navbar'
import SideBar from '../components/SideBar'
import FileCompare from '../components/FileCompare'

function GitIgnoreCompare() {
    return (
        <>
            <Navbar />
            <div className="home-container">
                <div className="side-bar-container">

                    <SideBar />
                </div>
                <div className="project-section-container">
                    <FileCompare FileName='.gitignore' isLimited={true} />

                </div>

            </div>
        </>
    )
}

export default GitIgnoreCompare
