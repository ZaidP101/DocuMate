import React from 'react'
import '../styles/TextArea.css'

function TextArea({ placeholder, value, onChange, readOnly }) {
    return (
        <div className='textarea-box'>

            <textarea
                className='textarea-input'
                placeholder={placeholder}
                value={value}
                onChange={onChange}
                readOnly={readOnly}
            ></textarea>
        </div>
    )
}

export default TextArea
