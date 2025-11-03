import React from 'react'
import '../styles/Input.css'

function Input({
    label,
    type = "text",
    name,
    value,
    onChange,
    placeholder,
    className = "",
    icon

}) {
    return (
        <div className={`input-container ${className}`}>

            {label && <label className="input-label">{label}:</label>}

            <div className="input-wrapper">
                {icon && <span className="input-icon">{icon}</span>}
                <input

                    type={type}
                    name={name}
                    value={value}
                    onChange={onChange}
                    placeholder={placeholder}
                    className={`input-field ${icon ? 'with-icon' : ''}`}


                />
            </div>
        </div>
    )
}

export default Input
