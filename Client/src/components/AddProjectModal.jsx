import React, { useEffect, useState } from "react";
import "../styles/AddProjectModal.css";
import Input from "./Input";
import Button from "./Button";
import axios from "axios";

function AddProjectModal({ isOpen, onClose }) {
  if (!isOpen) return null;

  const [allProject, setAllProjects] = useState([]);

  const getAllProjects = async () => {
    try {
      const res = await axios.get("/api/projects");
      setAllProjects(res.data);
      console.log(allProject);
    } catch (error) {
      console.error(error);
    }
  };

  const [formData, setFormData] = useState({
    title: "",
    gitRepoLink: "",
    localPath: "",
    template: "",
  });
  const handleProjectSubmit = async () => {
    try {
      const post = await axios.post("/api/projects", formData);
      console.log(formData);
      getAllProjects();
    } catch (error) {
      console.error(error);
    }
  };
  const onSave = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };
  const templates = [
    "AI_ML",
    "DATA_SCIENCE",
    "WEB_MOBILE",
    "CLOUD_DEVOPS",
    "CYBERSECURITY",
    "SOFTWARE_MANAGEMENT",
    "BLOCKCHAIN",
    "GAME_DEV",
    "ED_TECH",
  ];

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <h2>Add New Project</h2>

        <Input
          label="Project Name"
          name="title"
          onChange={onSave}
          placeholder="Enter project name"
          value={formData.title}
        />

        <div className="input-group">
          <label htmlFor="template">Template:</label>
          <select
            id="template"
            name="template"
            onChange={onSave}
            value={formData.template}
          >
            <option value="" disabled>
              Choose a template
            </option>
            {templates.map((template, index) => (
              <option key={index} value={template}>
                {template}
              </option>
            ))}
          </select>
          <Input
            label="GitHub Link"
            name="gitRepoLink"
            onChange={onSave}
            placeholder="Enter GitHub repository link"
            value={formData.gitRepoLink}
          />
          <Input
            label="LocalPath"
            name="localPath"
            value={formData.localPath}
            onChange={onSave}
            placeholder="Enter local path"
          />
        </div>

        <Button
          onClick={() => {
            handleProjectSubmit();
            onClose();
          }}
          text="Save and close"
        />
      </div>
    </div>
  );
}

export default AddProjectModal;
