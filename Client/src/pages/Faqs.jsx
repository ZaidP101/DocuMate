import React from "react";
import '../styles/EnvComparison.css'
import Navbar from "../components/Navbar";
import SideBar from "../components/SideBar";
import FaqComponent from "../components/FaqComponent";


function Faqs() {
    return (
        <>
            <div>
                <Navbar />
                <div className="home-container">
                    <div className="side-bar-container">

                        <SideBar />
                    </div>
                    <div className="project-section-container">
                        <FaqComponent />

                    </div>

                </div>

            </div>

        </>
    );
}

export default Faqs;
