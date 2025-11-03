import React from 'react'
import Input from './Input'
import { Search } from 'lucide-react'
import '../styles/Navbar.css'
import { useLocation } from 'react-router-dom'

function Navbar() {
    const location = useLocation();

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
