import React from 'react'
import FileCompare from '../components/FileCompare'
import Navbar from '../components/Navbar'
import SideBar from '../components/SideBar'
import '../styles/ReadMeComparison.css'

function ReadMeComparison() {
    return (
        <>
            <Navbar />
            <div className="home-container">
                <div className="side-bar-container">

                    <SideBar />
                </div>
                <div className="project-section-container">
                    <FileCompare FileName='ReadMe' />

                </div>

            </div>

        </>
    )
}

export default ReadMeComparison
