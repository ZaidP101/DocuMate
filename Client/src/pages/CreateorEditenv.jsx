import React, { useState, useEffect } from 'react'
import '../styles/EnvComparison.css'
import Navbar from '../components/Navbar'
import SideBar from '../components/SideBar'
import FileCompare from '../components/FileCompare'
import axios from 'axios'
import Loader from '../components/Loader'
import { toast } from 'react-toastify'

function CreateorEditenv() {
    const id = localStorage.getItem("currentProjectId");

    const [EnvData, setEnvData] = useState(null);
    const [loading, setLoading] = useState(false);

    // Generate .env file
    const generateEnvfile = async () => {
        try {
            setLoading(true)
            const res = await axios.post(`/api/env-example/${id}/generate`);
            setEnvData(res.data);
            toast.success(".env file generated successfully!");
            console.log(res.data);
        } catch (error) {
            console.error(error);
            toast.error("Failed to generate .env file.");
        } finally {
            setLoading(false)
        }
    }

    useEffect(() => {
        generateEnvfile()
    }, [])

    // Update .env file
    const updateEnvfile = async (userPrompt) => {
        try {
            setLoading(true)
            const res = await axios.post(`/api/env-example/${id}/regenerate`, {
                userPrompt,
                currentContent: EnvData.newContent,
                id,
            });
            setEnvData(res.data)
            toast.success(".env file updated successfully!"); // ✅ toast
            console.log(res.data);
        } catch (error) {
            console.error(error);
            toast.error("Failed to update .env file."); // ✅ toast
        } finally {
            setLoading(false)
        }
    }

    // Push .env file
    const pushEnvfile = async () => {
        try {
            setLoading(true)
            await axios.post(`/api/env-example/${id}/push`, {
                content: EnvData?.newContent || "",
            });
            toast.success(".env file pushed to local successfully!"); // ✅ toast
        } catch (error) {
            console.error(error);
            toast.error("Failed to push .env file."); // ✅ toast
        } finally {
            setLoading(false)
        }
    }

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
                            width='85%'
                            newFileData={EnvData?.newContent}
                            oldFileData={EnvData?.oldContent}
                            onSubmit={updateEnvfile}
                            onPush={pushEnvfile}
                        />
                    )}
                </div>
            </div>
        </>
    )
}

export default CreateorEditenv
