import React, { useEffect, useState } from "react";
import Card from "./Card";
import "../styles/ProjectSection.css";
import { Plus } from "lucide-react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Loader from "../components/Loader";
import AddProjectModal from "./AddProjectModal";
import { toast } from "react-toastify";

function ProjectSection({ searchText }) {
  const [allProject, setAllProjects] = useState([]);
  const [loading, setLoading] = useState(false);
  const [isModalOpen, setModalOpen] = useState(false);

  const navigate = useNavigate();

  useEffect(() => {
    getAllProjects();
  }, []);

  const getAllProjects = async () => {
    try {
      setLoading(true);
      const res = await axios.get("/api/projects");
      setAllProjects(res.data);
    } catch (error) {
      console.error(error);
      toast.error("Failed to fetch projects.");
    } finally {
      setLoading(false);
    }
  };

  const handleProjectClick = (id) => {
    navigate(`/latestReadme/${id}`);
    localStorage.setItem("currentProjectId", id);
  };

  const deleteProject = async (projectId) => {
    try {
      await axios.delete(`/api/projects/delete/${projectId}`);
      toast.success("Project deleted successfully!");
      getAllProjects();
    } catch (error) {
      console.error(error);
      toast.error("Failed to delete project.");
    }
  };

  const filteredProjects = allProject.filter((project) =>
    searchText
      ? project.title.toLowerCase().includes(searchText.toLowerCase())
      : true
  );

  return (
    <div className="project-container">
      <div className="project-header">
        <p className="project-heading">Welcome to Documate</p>
        <p className="project-subheading">
          DocuMate — Because great code deserves great documentation!
        </p>

        <p className="descrption-para">
          Select a project to start or create a new one
        </p>
      </div>

      {loading ? (
        <Loader />
      ) : (
        <div className="project-section">
          {filteredProjects.length > 0 ? (
            filteredProjects.map((project) => (
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
            ))
          ) : (
            <p
              style={{
                color: "#a855f7",
                marginLeft: "40%",
                marginTop: "15%",
                fontSize: "1.5rem"
              }}
            >
              No projects found
            </p>
          )}
        </div>
      )}

      <button
        className="floating-btn"
        onClick={() => {
          const username = localStorage.getItem("username");
          if (!username) {
            toast.error("You must register before creating a project.");
            return;
          }
          setModalOpen(true);
        }}
      >
        <Plus size={32} />
      </button>


      <AddProjectModal
        isOpen={isModalOpen}
        onClose={() => setModalOpen(false)}
        refreshProjects={() => {
          getAllProjects();
          toast.success("Project created successfully!");
        }}
      />
    </div>
  );
}

export default ProjectSection;
