import React, { useState } from 'react'
import TextArea from './TextArea'
import Input from './Input'
import '../styles/FileCompare.css'

function FileCompare({ FileName, isLimited, width = "60%" }) {
    const [oldFile, setOldFile] = useState('');
    const [newFile, setNewFile] = useState('');
    const [showPromptInput, setShowPromptInput] = useState(false);
    const [promptText, setPromptText] = useState('');

    const handlePromptClick = () => setShowPromptInput(true);
    const handlePromptSubmit = () => {
        console.log('Prompt submitted:', promptText);
        setShowPromptInput(false);
        setPromptText('');
    };

    return (
        <div className="file-container">
            <div className="file-section" style={{ width }}>
                <div className="old-file">
                    <h3>{FileName}</h3>
                    <TextArea
                        placeholder="Old file content"
                        value={oldFile}
                        onChange={(e) => setOldFile(e.target.value)}
                        readOnly={true}
                    />
                </div>

                {!isLimited && (
                    <>
                        <div className="divider"></div>
                        <div className="new-file">
                            <h3>New {FileName}</h3>
                            <TextArea
                                placeholder="New file content"
                                value={newFile}
                                onChange={(e) => setNewFile(e.target.value)}
                            />

                            <div className="btn-grp">
                                {!showPromptInput ? (
                                    <>
                                        <button className="btn" onClick={handlePromptClick}>
                                            Prompt
                                        </button>
                                        <button className="btn">Push</button>
                                    </>
                                ) : (
                                    <div className="prompt-input-container">
                                        <Input
                                            placeholder="Enter your prompt..."
                                            value={promptText}
                                            onChange={(e) => setPromptText(e.target.value)}
                                        />
                                        <button className="btn" onClick={handlePromptSubmit}>
                                            Submit
                                        </button>
                                    </div>
                                )}
                            </div>
                        </div>
                    </>
                )}
            </div>
        </div>
    );
}

export default FileCompare;
