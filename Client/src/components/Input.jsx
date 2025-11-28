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
    icon,
    onKeyDown

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
                    onKeyDown={onKeyDown}
                    placeholder={placeholder}
                    className={`input-field ${icon ? 'with-icon' : ''}`}
                    required

                />
            </div>
        </div>
    )
}

export default Input
