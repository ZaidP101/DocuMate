import React, { useEffect, useState } from "react";
import Button from "../components/Button";
import TextArea from "../components/TextArea";
import "../styles/LatestReadme.css";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import ReactMarkdown from "react-markdown";
import { UserPen } from "lucide-react";

function LatestReadMe({ title }) {
  const navigate = useNavigate();
  const [currentReadme, setCurrentReadme] = useState({});
  const { id } = useParams();

  useEffect(() => {
    if (id) fetchReadme(id);
  }, [id]);
  console.log(id);
  const fetchReadme = async (id) => {
    try {
      const res = await axios.get(`/api/projects/${id}`);
      setCurrentReadme(
        res.data.currentReadme || { content: "No README content found" }
      );
      console.log(currentReadme);
    } catch (error) {
      console.log(error);
    }
  };

  const handleEnvClick = () => navigate("/editenv");
  const handleDockerClick = () => navigate("/editdocker");
  const handleGitClick = () => navigate("/editgit");

  return (
    <div className="project-editor-container">
      <div className="readme-section">
        <h3 className="section-title">Latest Readme (view)</h3>

        {currentReadme ? (
          <div className="markdown-preview">
            <ReactMarkdown>{currentReadme?.content}</ReactMarkdown>
          </div>
        ) : (
          <p>Loading README content...</p>
        )}
      </div>

      <div id="divider"></div>

      <div className="edit-section">
        <h3 className="project-title">{currentReadme?.title}</h3>
        <div className="button-group">
          <Button text="Edit or create Docker" onClick={handleDockerClick} />
          <Button text="Edit or create .env.example" onClick={handleEnvClick} />
          <Button text="Edit or create .gitignore" onClick={handleGitClick} />
        </div>
      </div>
    </div>
  );
}

export default LatestReadMe;
