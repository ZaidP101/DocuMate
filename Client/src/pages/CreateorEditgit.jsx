import React, { useState, useEffect } from 'react'
import '../styles/EnvComparison.css'
import Navbar from '../components/Navbar'
import SideBar from '../components/SideBar'
import FileCompare from '../components/FileCompare'
import axios from 'axios'
import Loader from '../components/Loader'
import { toast } from 'react-toastify'

function CreateorEditgit() {
    const id = localStorage.getItem("currentProjectId");

    const [gitData, setGitData] = useState(null);
    const [loading, setLoading] = useState(false);

    // Generate .gitignore file
    const generateGitfile = async () => {
        try {
            setLoading(true)
            const res = await axios.post(`/api/gitignore/${id}/generate`);
            setGitData(res.data);
            toast.success(".gitignore file generated successfully!");
            console.log(res.data);
        } catch (error) {
            console.error(error);
            toast.error("Failed to generate .gitignore file.");
        } finally {
            setLoading(false)
        }
    }

    useEffect(() => {
        generateGitfile()
    }, [])


    const updateGitfile = async (userPrompt) => {
        try {
            setLoading(true)
            const res = await axios.post(`/api/gitignore/${id}/regenerate`, {
                userPrompt,
                currentContent: gitData.newContent,
                id,
            })
            setGitData(res.data)
            toast.success(".gitignore file updated successfully!");
            console.log(res.data);
        } catch (error) {
            console.error(error);
            toast.error("Failed to update .gitignore file.");
        } finally {
            setLoading(false)
        }
    }

    // Push .gitignore file
    const pushGitfile = async () => {
        try {
            setLoading(true)
            await axios.post(`/api/gitignore/${id}/push`, {
                content: gitData?.newContent || "",
            })
            toast.success(".gitignore file pushed to local successfully!"); // ✅ toast
        } catch (error) {
            console.error(error);
            toast.error("Failed to push .gitignore file."); // ✅ toast
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
                            FileName='Git '
                            width='85%'
                            newFileData={gitData?.newContent}
                            oldFileData={gitData?.oldContent}
                            onSubmit={updateGitfile}
                            onPush={pushGitfile}
                        />
                    )}
                </div>
            </div>
        </div>
    )
}

export default CreateorEditgit
