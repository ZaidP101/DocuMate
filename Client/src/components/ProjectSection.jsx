import React, { useEffect, useState } from "react";
import Card from "./Card";
import "../styles/ProjectSection.css";
import { Plus } from "lucide-react";
import { data, useNavigate, useParams } from "react-router-dom";
import axios from "axios";

function ProjectSection({ onAddClick }) {
  const [allProject, setAllProjects] = useState([]);
  useEffect(() => {
    getAllProjects();
  }, []);
  const navigate = useNavigate();

  const handleProjectClick = (id) => {
    navigate(`/latestReadme/${id}`);
    console.log(id);
    console.log("card clicked");
  };
  const getAllProjects = async () => {
    try {
      const res = await axios.get("/api/projects");
      setAllProjects(res.data);
      console.log(allProject);
    } catch (error) {
      console.error(error);
    }
  };
  const deleteProject = async (projectId) => {
    try {
      await axios.delete(`/api/projects/delete/${projectId}`);
      console.log("Project deleted:", projectId);
      getAllProjects(); // 👈 Refresh list after delete
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className="project-container">
      <div className="project-header">
        <p className="project-heading">Welcome to Documate</p>
        <p className="descrption-para">
          Select a project to start or create a new one
        </p>
      </div>

      <div className="project-section">
        {allProject.map((project) => (
          <Card
            key={project.id}
            title={project.title}
            modified={project.createdAt}
            repolink={project.gitRepoLink}
            localink={project.localPath}
            template={project.template}
            onDelete={() => deleteProject(project.id)}
            onClick={() => handleProjectClick(project.id)}
          />
        ))}
      </div>

      <button className="floating-btn" onClick={onAddClick}>
        <Plus size={32} />
      </button>
    </div>
  );
}

export default ProjectSection;
