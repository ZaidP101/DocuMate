import React from "react";
import { Trash2 } from "lucide-react";
import "../styles/Card.css";

function Card({
  title,
  modified,
  repolink,
  localink,
  template,
  onDelete,
  onClick,
}) {
  return (
    <div className="card" role="group">
      <div className="card-content" onClick={onClick}>
        <h3>{title}</h3>
        <p>
          <strong>Template:</strong> {template}
        </p>
        <p className="links">
          <span>GitHub:</span>{" "}
          <a href={repolink} target="_blank" rel="noreferrer">
            {repolink}
          </a>
        </p>
        <p className="links">
          <span>Local:</span> {localink}
        </p>
      </div>

      <div className="card-footer">
        <div className="date-wrapper">
          <p className="date-modified">{modified}</p>
        </div>

        <button
          className="delete-btn"
          onClick={onDelete}
          aria-label={`Delete ${title}`}
          title="Delete"
        >
          <Trash2 size={16} />
          <span className="delete-text">Delete</span>
        </button>
      </div>
    </div>
  );
}

export default Card;
