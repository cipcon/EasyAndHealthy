import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useUserContext } from "../Contexts/Context";

export const LogoutButton: React.FC = () => {
    const navigate = useNavigate();
    const { onLogout } = useUserContext();
    const { userCredentials } = useUserContext();

    const handleLogout = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
        event.preventDefault();
        onLogout();
        navigate('/');
    };

    return (
        <>
            {userCredentials.id === 0 ? '' : <button type="button" onClick={handleLogout} className="btn btn-warning" style={{ marginLeft: "auto" }}>Logout</button>}        </>
    );
};
