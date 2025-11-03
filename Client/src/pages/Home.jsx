import { React, useState } from 'react'
import '../styles/Home.css'
import { useNavigate } from 'react-router-dom';
import Navbar from '../components/Navbar';
import SideBar from '../components/SideBar';
import ProjectSection from '../components/ProjectSection';
import AddProjectModal from '../components/AddProjectModal';

function Home() {
    const navigate = useNavigate()
    const [isModalOpen, setIsModalOpen] = useState(false);



    return (
        <>
            <Navbar />
            <div className="home-container">
                <div className="side-bar-container">

                    <SideBar />
                </div>
                <div className="project-section-container">

                    <ProjectSection onAddClick={() => setIsModalOpen(true)} />
                </div>
                <AddProjectModal
                    isOpen={isModalOpen}
                    onClose={() => setIsModalOpen(false)}
                />
            </div>


        </>
    )
}

export default Home
