import React, { useState, useEffect } from 'react'
import FileCompare from '../components/FileCompare'
import Navbar from '../components/Navbar'
import SideBar from '../components/SideBar'
import '../styles/ReadMeComparison.css'
import axios from 'axios'
import Loader from '../components/Loader'
import { toast } from 'react-toastify'

function ReadMeComparison() {
    const id = localStorage.getItem("currentProjectId");

    const [ReadmeFileData, setReadmeFileData] = useState(null);
    const [loading, setLoading] = useState(false);

    const getReadmeFileContent = async () => {
        try {
            setLoading(true);
            const res = await axios.get(`/api/readme/${id}/diff`);
            setReadmeFileData(res.data);
            toast.success("ReadMe comparison loaded successfully!");
        } catch (error) {
            console.log(error);
            toast.error("Failed to load ReadMe comparison.");
        } finally {
            setLoading(false);
        }
    };


    const updateReadmefile = async (userPrompt) => {
        try {
            setLoading(true);

            const res = await axios.post(`/api/readme/${id}/regenerate`, {
                userPrompt,
                currentContent: ReadmeFileData?.newContent,
            });

            setReadmeFileData({
                oldContent: res.data.oldContent,
                newContent: res.data.newContent
            });

            toast.success("ReadMe regenerated successfully!");
        } catch (error) {
            console.log(error);
            toast.error("Failed to regenerate ReadMe file.");
        } finally {
            setLoading(false);
        }
    };


    const pushReadmefile = async () => {
        try {
            setLoading(true);
            await axios.post(`/api/readme/${id}/push`, {
                content: ReadmeFileData?.newContent || "",
            });

            toast.success("ReadMe pushed to GitHub & saved locally!");
        } catch (error) {
            console.error(error);
            toast.error("Failed to push ReadMe file.");
        } finally {
            setLoading(false);
        }
    };


    useEffect(() => {
        getReadmeFileContent();
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
                            FileName="ReadMe"
                            width="85%"
                            oldFileData={ReadmeFileData?.oldContent}
                            newFileData={ReadmeFileData?.newContent}
                            onSubmit={updateReadmefile}
                            fileType="readme"
                            onPush={pushReadmefile}
                        />
                    )}
                </div>
            </div>
        </>
    )
}

export default ReadMeComparison;
