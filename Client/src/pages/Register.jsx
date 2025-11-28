import React from 'react'
import '../styles/EnvComparison.css'
import Navbar from '../components/Navbar'
import SideBar from '../components/SideBar'
import RegisterComponent from '../components/RegisterComponent'
function Register() {
    return (
        <>
            <Navbar />
            <div className="home-container">
                <div className="side-bar-container">

                    <SideBar />
                </div>
                <div className="project-section-container">
                    <RegisterComponent />

                </div>

            </div>

        </>
    )
}

export default Register
