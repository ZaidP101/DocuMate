import React, { useEffect, useState } from 'react'
import '../styles/GitIgnoreComparison.css'
import Navbar from '../components/Navbar'
import SideBar from '../components/SideBar'
import FileCompare from '../components/FileCompare'
import axios from 'axios'
import Loader from '../components/Loader'
import { toast } from 'react-toastify'

function GitIgnoreCompare() {
    const [gitData, setGitData] = useState(null);
    const id = localStorage.getItem("currentProjectId")
    const [loading, setLoading] = useState(false)

    // Fetch .gitignore file
    const fetchGitFile = async () => {
        try {
            setLoading(true)
            const res = await axios.get(`/api/gitignore/${id}`)
            if (res.data && res.data.content) { // ✅ Only show success if file exists
                setGitData(res.data)
                toast.success(".gitignore file fetched successfully!");
            } else {
                toast.info(".gitignore file does not exist for this project."); // ✅ Info if missing
            }
        } catch (error) {
            console.error(error);
            toast.error("Failed to fetch .gitignore file.");
        } finally {
            setLoading(false)
        }
    }

    useEffect(() => {
        fetchGitFile();
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
                            FileName='.gitignore'
                            oldFileData={gitData?.content}
                            isLimited={true}
                        />
                    )}
                </div>
            </div>
        </>
    )
}

export default GitIgnoreCompare
