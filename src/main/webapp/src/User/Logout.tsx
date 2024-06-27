import { useNavigate } from "react-router-dom";
import { useUserContext } from "../Contexts/context";

export const Logout = () => {
    const navigate = useNavigate();
    const userContext = useUserContext();

    const handleLogout = () => {
        localStorage.removeItem('user');
        localStorage.removeItem('token');
        userContext.setUserCredentials({ id: 0, name: '', token: '' });
        navigate('/');
    };

    return (
        <button onClick={handleLogout} style={{ marginLeft: 10, border: "none" }}>Logout</button>
    );
};
