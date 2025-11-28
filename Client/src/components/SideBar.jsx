import { Book, Boxes, Cloud, Cog, GitBranch, HelpCircle, Home, Info, Workflow, FileText, UserPlus2 } from 'lucide-react'
import React from 'react'
import '../styles/SideBar.css'
import { useLocation, useNavigate, useParams } from 'react-router-dom'

function SideBar() {
    const navigate = useNavigate();
    const location = useLocation();
    const currentProjectId = localStorage.getItem("currentProjectId");


    const registerVisiblePages = ['/', '/home', '/prerequisite', '/about', '/faqs', '/workflow'];
    const showRegister = registerVisiblePages.includes(location.pathname);



    const showProjectItems = !['/', '/home', '/about', '/faqs', '/workflow', '/register', '/prerequisite'].includes(location.pathname);

    return (
        <div>
            <div className="sidebar">
                <div className="sidebar-section">
                    <ul>
                        <li onClick={() => navigate('/')}><Home size={18} /> Home</li>

                        {showRegister && (
                            <li onClick={() => navigate('/register')}><UserPlus2 size={18} /> Register</li>
                        )}

                        {showProjectItems && (
                            <>
                                <li onClick={() => navigate('/gitignore')}><GitBranch size={18} /> GitComp</li>
                                <li onClick={() => navigate('/docker')}><Cloud size={18} /> DockerComp</li>
                                <li onClick={() => navigate('/env')}><Cog size={18} /> EnvComp</li>
                                <li onClick={() => {
                                    if (currentProjectId) navigate(`/latestReadme/${currentProjectId}`);
                                    else alert('No project selected! Please open a project first.');
                                }}><FileText size={18} /> View ReadMe</li>
                            </>
                        )}

                        <li onClick={() => navigate('/prerequisite')}><Boxes size={18} /> Prerequisite</li>
                        <li onClick={() => navigate('/faqs')}><HelpCircle size={18} /> FAQs</li>
                        <li onClick={() => navigate('/workflow')}><Workflow size={18} /> WorkFlow</li>
                        <li onClick={() => navigate('/about')}><Info size={18} /> About</li>
                    </ul>
                </div>
                <div className="sidebar-footer">
                    <p>© 2025 Documate <br /> All Rights reserved</p>
                </div>
            </div>
        </div>
    );
}


export default SideBar
