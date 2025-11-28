import React, { useState } from 'react'
import Input from './Input'
import { Search } from 'lucide-react'
import '../styles/Navbar.css'
import { useLocation } from 'react-router-dom'

function Navbar({ searchText, setSearchText }) {
    const location = useLocation();
    const [inputValue, setInputValue] = useState("")

    const handleKeyDown = (e) => {
        if (e.key === "Enter") {
            setSearchText(inputValue);
        }
    };

    const isHome = location.pathname === '/' || location.pathname === '/home'
    return (
        <>
            <div className="navbar">

                <div className="left-navbar">
                    <h1>Documate</h1>
                    <p>Your Code's Story,<br /> Automatically Told</p>
                    <p className="with-line">Projects</p>

                </div>
                {isHome &&
                    <div className="right-navbar">
                        <Input
                            type='text'
                            name='search'
                            value={inputValue}
                            onChange={(e) => setInputValue(e.target.value)}
                            onKeyDown={handleKeyDown}
                            placeholder="Search projects..."
                            icon={<Search size={15} />}
                        />
                    </div>
                }

            </div>
        </>
    )
}

export default Navbar
