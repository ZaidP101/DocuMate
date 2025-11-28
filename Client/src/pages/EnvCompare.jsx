import React, { useState, useEffect } from 'react'
import '../styles/EnvComparison.css'
import Navbar from '../components/Navbar'
import SideBar from '../components/SideBar'
import FileCompare from '../components/FileCompare'
import axios from 'axios'
import Loader from '../components/Loader'
import { toast } from 'react-toastify'

function EnvCompare() {
    const [EnvData, setEnvData] = useState(null);
    const id = localStorage.getItem("currentProjectId");
    const [loading, setLoading] = useState(false);

    // Fetch .env file
    const fetchEnvFile = async () => {
        try {
            setLoading(true);
            const res = await axios.get(`/api/env-example/${id}`);
            if (res.data && res.data.content) {  // ✅ Check if file actually exists
                setEnvData(res.data);
                toast.success(".env file fetched successfully!");
            } else {
                toast.info(".env file does not exist for this project."); // Info if missing
            }
        } catch (error) {
            console.error(error);
            toast.error("Failed to fetch .env file.");
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        fetchEnvFile();
    }, []);

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
                            FileName='.env'
                            oldFileData={EnvData?.content}
                            isLimited={true}
                        />
                    )}
                </div>
            </div>
        </>
    )
}

export default EnvCompare;
