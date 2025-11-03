import React from 'react'
import Navbar from '../components/Navbar'
import '../styles/ViewReadme.css'
import SideBar from '../components/SideBar'
import LatestReadMe from '../components/LatestReadMe'

function ViewReadme() {
    return (
        <>
            <Navbar />
            <div className="home-container">
                <div className="side-bar-container">

                    <SideBar />
                </div>
                <div className="project-section-container">
                    <LatestReadMe title='Java Project' />

                </div>

            </div>
        </>
    )
}

export default ViewReadme
