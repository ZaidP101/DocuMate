import React, { useState } from "react";
import "../styles/Register.css";
import Input from "../components/Input";
import Button from "./Button";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import Loader from "./Loader";

function RegisterComponent() {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        email: "",
        username: "",
        token: "",
    });
    const [loading, setLoading] = useState(false);

    const handleChange = (field, value) => {
        setFormData({ ...formData, [field]: value });
    };

    const handleRegisterSubmit = async (e) => {
        e.preventDefault();
        try {
            setLoading(true);
            await axios.post(`/api/git-credentials`, {
                email: formData.email,
                username: formData.username,
                token: formData.token
            });


            localStorage.setItem("username", formData.username);

            toast.success("Registered successfully!");
            navigate('/');
        } catch (error) {
            console.error(error);
            toast.error("Registration failed. Please try again.");
        } finally {
            setLoading(false);
        }
    };


    return (
        <div className="register-container">
            <div className="register-content">
                <div className="register-header">
                    <h2>Register to DocuMate</h2>
                    <p>Connect GitHub and begin your automated documentation journey.</p>
                </div>

                {loading ? (
                    <Loader />
                ) : (
                    <form className="register-form" onSubmit={handleRegisterSubmit}>
                        <div className="input-group">
                            <Input
                                label="Email"
                                placeholder="Enter your email"
                                type="email"
                                value={formData.email}
                                onChange={(e) => handleChange("email", e.target.value)}
                            />
                        </div>
                        <div className="input-group">
                            <Input
                                label="GitHub Username"
                                placeholder="Enter your GitHub username"
                                type="text"
                                value={formData.username}
                                onChange={(e) => handleChange("username", e.target.value)}
                            />
                        </div>

                        <div className="input-group">
                            <Input
                                label="Access Token"
                                placeholder="Enter your GitHub Access Token"
                                type="password"
                                value={formData.token}
                                onChange={(e) => handleChange("token", e.target.value)}
                            />
                        </div>

                        <Button
                            text='Register'
                            type="submit"
                            className="custom-btn"
                            disabled={loading}
                        />
                    </form>
                )
                }
            </div >
        </div >
    );
}

export default RegisterComponent;
