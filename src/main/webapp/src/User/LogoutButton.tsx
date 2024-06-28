import React from "react";
import { useNavigate } from "react-router-dom";
import { useUserContext } from "../Contexts/context";

export const LogoutButton: React.FC = () => {
    const navigate = useNavigate();
    const { logout } = useUserContext();

    const handleLogout = () => {
        logout();
        navigate('/');
    };

    return (
        <button onClick={handleLogout} style={{ marginLeft: 10, border: "none" }}>Logout</button>
    );
};
