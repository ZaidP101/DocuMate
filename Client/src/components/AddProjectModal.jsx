
import React, { useState } from "react";
import "../styles/AddProjectModal.css";
import Input from "./Input";
import Button from "./Button";
import axios from "axios";
import Loader from "./Loader";

function AddProjectModal({ isOpen, onClose, refreshProjects }) {
  if (!isOpen) return null;

  const [formData, setFormData] = useState({
    title: "",
    gitRepoLink: "",
    localPath: "",
    template: "",
  });

  const [loading, setLoading] = useState(false)
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

  const handleProjectSubmit = async () => {
    try {
      setLoading(true)
      await axios.post("/api/projects", formData);
      if (refreshProjects) refreshProjects();
      setFormData({ title: "", gitRepoLink: "", localPath: "", template: "" });
    } catch (error) {
      console.error(error);
      setLoading(false)
    }
    finally {
      setLoading(false)
    }
  };

  const onSave = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  return (<div className="modal-overlay" onClick={onClose}>
    <div className="modal-content" onClick={(e) => e.stopPropagation()}>

      {
        loading ? (
          <Loader />
        ) : (
          <>
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
                onChange={onSave}
                placeholder="Enter local path"
                value={formData.localPath}
              />
            </div>

            <Button
              onClick={async () => {
                await handleProjectSubmit();
                onClose();
              }}
              text="Save and close"
            />
          </>

        )
      }
    </div>
  </div>


  );
}

export default AddProjectModal;
