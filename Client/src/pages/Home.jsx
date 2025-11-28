import { React, useState } from 'react'
import '../styles/Home.css'
import Navbar from '../components/Navbar';
import SideBar from '../components/SideBar';
import ProjectSection from '../components/ProjectSection';


function Home() {

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [searchText, setSearchText] = useState("")




    return (
        <>
            <Navbar setSearchText={setSearchText} />
            <div className="home-container">
                <div className="side-bar-container">

                    <SideBar />
                </div>
                <div className="project-section-container">

                    <ProjectSection searchText={searchText} onAddClick={() => setIsModalOpen(true)} />
                </div>

            </div>


        </>
    )
}

export default Home
