import React, { useState, useEffect } from 'react'
import '../styles/EnvComparison.css'
import Navbar from '../components/Navbar'
import SideBar from '../components/SideBar'
import FileCompare from '../components/FileCompare'
import axios from 'axios'
import Loader from '../components/Loader'
import { toast } from 'react-toastify'

function CreateorEditdocker() {
    const id = localStorage.getItem("currentProjectId");

    const [dockerData, setDockerData] = useState(null);
    const [loading, setLoading] = useState(false);

    const generateDockerfile = async () => {
        try {
            setLoading(true)
            const res = await axios.post(`/api/docker/${id}/generate`);
            setDockerData(res.data);
            toast.success("Dockerfile generated successfully!");
            console.log(res.data);
        } catch (error) {
            console.error(error);
            toast.error("Failed to generate Dockerfile."); t
        } finally {
            setLoading(false)
        }
    }

    useEffect(() => {
        generateDockerfile()
    }, [])


    const updateDockerfile = async (userPrompt) => {
        try {
            setLoading(true)
            const res = await axios.post(`/api/docker/${id}/regenerate`, {
                userPrompt,
                currentContent: dockerData.newContent,
                id,
            });
            setDockerData(res.data)
            toast.success("Dockerfile updated successfully!");
            console.log(res.data);
        } catch (error) {
            console.error(error);
            toast.error("Failed to update Dockerfile.");
        } finally {
            setLoading(false)
        }
    }

    // Push Dockerfile
    const pushDockerfile = async () => {
        try {
            setLoading(true)
            await axios.post(`/api/docker/${id}/push`, {
                content: dockerData?.newContent || "",
            });
            toast.success("Dockerfile pushed to local successfully!");
        } catch (error) {
            console.error(error);
            toast.error("Failed to push Dockerfile.");
        } finally {
            setLoading(false)
        }
    }

    return (
        <div>
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
                            FileName='Docker '
                            width='85%'
                            newFileData={dockerData?.newContent}
                            oldFileData={dockerData?.oldContent}
                            onSubmit={updateDockerfile}
                            onPush={pushDockerfile}
                        />
                    )}
                </div>
            </div>
        </div>
    )
}

export default CreateorEditdocker
