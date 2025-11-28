import React from "react";
import "../styles/Button.css";

const Button = ({ text, onClick, type = "button", disabled = false, className }) => {
    return (
        <button
            className={`documate-btn ${disabled ? "disabled" : ""} ${className}`}
            onClick={onClick}
            type={type}
            disabled={disabled}
        >
            {text}
        </button>
    );
};

export default Button;
