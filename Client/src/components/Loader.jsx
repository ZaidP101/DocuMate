import React from "react";
import "../styles/Loader.css";

const Loader = () => {
    return (
        <div className="documate-loader-container">
            <div className="wave">
                <span></span>
                <span></span>
                <span></span>
                <span></span>
                <span></span>
            </div>
            <h2 className="loading-text">Loading DocuMate...</h2>
        </div>
    );
};

export default Loader;
