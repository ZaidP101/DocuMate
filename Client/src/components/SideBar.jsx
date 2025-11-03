import { Book, Boxes, Cloud, Cog, GitBranch, HelpCircle, Home, Info, Workflow, FileText } from 'lucide-react'
import React from 'react'
import '../styles/SideBar.css'
import { useLocation, useNavigate } from 'react-router-dom'

function SideBar() {
    const navigate = useNavigate()
    const location = useLocation()

    const isHome = location.pathname === '/' || location.pathname === '/home'


    const handleGitClick = () => {
        navigate('/gitignore')
    }
    const handleEnvClick = () => {
        navigate('/env')
    }
    const handleDockerClick = () => {
        navigate('/docker')
    }
    const handleLatestReadmeClick = () => {
        navigate('/latestReadme')
    }
    const handleHomeClick = () => {
        navigate('/')
    }
    return (
        <div>
            <div className="sidebar">
                <div className="sidebar-section">
                    <ul>
                        <li onClick={handleHomeClick}><Home size={18} />Home</li>
                        {!isHome
                            &&
                            <>
                                <li onClick={handleGitClick}><GitBranch size={18} />GitComp</li>
                                <li onClick={handleDockerClick}><Cloud size={18} />DockerComp</li>
                                <li onClick={handleEnvClick}><Cog size={18} />EnvComp</li>
                                <li onClick={handleLatestReadmeClick}><FileText size={18} />View ReadMe</li>
                            </>
                        }
                        <li> <Boxes size={18} />Prerequisite</li>
                        <li><HelpCircle size={18} />FAQs</li>
                        <li><Workflow size={18} />WorkFlow</li>
                        <li><Info size={18} />About</li>

                    </ul>
                </div>
                <div className="sidebar-footer">
                    <p>© 2025 Documate <br /> All Rights reserved</p>
                </div>
            </div>

        </div>
    )
}

export default SideBar
