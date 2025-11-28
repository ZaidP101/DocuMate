import React from 'react'
import '../styles/TextArea.css'

function TextArea({ placeholder, value, onChange, readOnly, className }) {
    return (
        <>
            <div className='textarea-box'>

                <textarea
                    className={`textarea-input ${className}`}
                    placeholder={placeholder}
                    value={value}
                    onChange={onChange}
                    readOnly={readOnly}
                ></textarea>
            </div>
        </>
    )
}

export default TextArea
