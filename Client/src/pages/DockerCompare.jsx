import React, { useState, useEffect } from 'react'
import '../styles/DockerComparison.css'
import Navbar from '../components/Navbar'
import SideBar from '../components/SideBar'
import FileCompare from '../components/FileCompare'
import axios from 'axios'
import Loader from '../components/Loader'
import { toast } from 'react-toastify'

function DockerCompare() {
    const [DockerData, setDockerData] = useState(null);
    const id = localStorage.getItem("currentProjectId")
    const [loading, setLoading] = useState(false)

    // Fetch Dockerfile
    const fetchDockerFile = async () => {
        try {
            setLoading(true)
            const res = await axios.get(`/api/docker/${id}`)
            if (res.data && res.data.content) {  // ✅ Check if file exists
                setDockerData(res.data)
                toast.success("Dockerfile fetched successfully!");
            } else {
                toast.info("Dockerfile does not exist for this project."); // Info if missing
            }
        } catch (error) {
            console.error(error);
            toast.error("Failed to fetch Dockerfile.");
        } finally {
            setLoading(false)
        }
    }

    useEffect(() => {
        fetchDockerFile();
    }, [])

    return (
        <>
            <Navbar />
            <div className="home-container">
                <div className="side-bar-container">
                    <SideBar />
                </div>
                <div className="project-section-container">
                    {loading ? (
                        <Loader />
                    ) : (
                        <FileCompare
                            FileName='Docker'
                            oldFileData={DockerData?.content}
                            isLimited={true}
                        />
                    )}
                </div>
            </div>
        </>
    )
}

export default DockerCompare
