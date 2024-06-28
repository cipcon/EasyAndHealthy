import { ChangeEvent, FormEvent, useState } from "react";
import { useNavigate } from "react-router-dom";
import { LoginForm } from "./components/LoginForm";
import { useUserContext } from "../Contexts/context";
import { generateToken } from "./components/GenerateToken";

interface UserCredentials {
    username: string;
    password: string;
}

interface LoginResponse {
    isAuthenticated: boolean;
    message: string;
    status: string;
    userId: number;
    username: string;
}


export const Login = () => {
    const [credentials, setCredentials] = useState<UserCredentials>({ username: "", password: "" });
    const [loginError, setLoginError] = useState('');
    const { setUserCredentials } = useUserContext();
    const navigate = useNavigate();

    const handleUsernameChange = (event: ChangeEvent<HTMLInputElement>) => {
        setCredentials({ ...credentials, username: event.target.value });
    };

    const handlePasswordChange = (event: ChangeEvent<HTMLInputElement>) => {
        setCredentials({ ...credentials, password: event.target.value });
    };

    const handleForm = async (event: FormEvent) => {
        event.preventDefault();

        // Validation logic
        if (!credentials.username && !credentials.password) {
            setLoginError('Username and password are required');
            return;
        }

        try {
            const response = await fetch(`/login`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                // what i send to the api
                body: JSON.stringify(credentials) // sending credentials in the request body (username and passowrd to api)
            });

            // what i receive from the api

            if (response.ok) {
                const data: LoginResponse = await response.json();
                const token = generateToken(data.userId);
                setUserCredentials({ id: data.userId, name: data.username, token })
                navigate('/');
            } else {
                setLoginError('Authentication failed. Please check your credentials.');
            }
        } catch (error) {
            console.error("Error during login:", error);
            setLoginError("Login failed due to a network or server issue. Please try again.");
        }
    };

    return (
        <LoginForm
            handleForm={handleForm}
            handleUsernameChange={handleUsernameChange}
            handlePasswordChange={handlePasswordChange}
            username={credentials.username}
            password={credentials.password}
            loginError={loginError}
        />
    );
};
