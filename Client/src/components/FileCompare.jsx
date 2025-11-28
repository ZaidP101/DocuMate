import React, { useState } from 'react';
import TextArea from './TextArea';
import Input from './Input';
import '../styles/FileCompare.css';
import ReactMarkdown from 'react-markdown';

function FileCompare({ FileName, isLimited, width = "60%", oldFileData, newFileData, fileType, onSubmit, onPush }) {
    const [showPromptInput, setShowPromptInput] = useState(false);
    const [promptText, setPromptText] = useState('');

    const handlePromptClick = () => setShowPromptInput(true);
    const handlePromptSubmit = () => {
        console.log('Prompt submitted:', promptText);
        if (promptText === '') {
            setShowPromptInput(false);
            return;
        }

        if (onSubmit) {
            onSubmit(promptText)
        }
        setShowPromptInput(false);
        setPromptText('');
    };

    const renderContent = (data) => {
        if (fileType === "readme") {
            return (
                <div className="markdown-preview">
                    <ReactMarkdown >{data || "No File content"}</ReactMarkdown>
                </div>
            );
        }
        return (
            <TextArea
                className="textarea-animate"
                placeholder="No File content"
                value={data || ""}
                readOnly
            />
        );
    };

    return (
        <div className="file-container">
            <div className="file-section" style={{ width }}>
                <div className="old-file">
                    <h3>{FileName}</h3>
                    {renderContent(oldFileData)}
                </div>

                {!isLimited && (
                    <>
                        <div className="divider"></div>
                        <div className="new-file">
                            <h3>New {FileName}</h3>
                            {renderContent(newFileData)}

                            <div className="btn-grp">
                                {!showPromptInput ? (
                                    <>
                                        <button className="btn" onClick={handlePromptClick}>
                                            Prompt
                                        </button>
                                        <button className="btn" onClick={onPush} >Push</button>
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
