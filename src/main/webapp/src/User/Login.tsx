import { ChangeEvent, FormEvent, useState } from "react";
import { useNavigate } from "react-router-dom";
import { LoginForm } from "./components/LoginForm";
import { useUserContext } from "../context";

interface UserCredentials {
    username: string;
    password: string;
}

interface LoginResponse {
    username: string;
    userId: number;
    isAuthenticated: boolean;
    message: string;
}

export const Login = () => {
    const [credentials, setCredentials] = useState<UserCredentials>({ username: "", password: "" });
    const [registrationError, setRegistrationError] = useState('');
    const userContext = useUserContext();
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
        if (!credentials.username) {
            setRegistrationError('Username is required');
        }

        if (!credentials.password) {
            setRegistrationError('Password is required');
        }

        try {
            const response = await fetch(`/login/`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(credentials) // sending credentials in the request body (username and passowrd to api)
            });

            const data: LoginResponse = await response.json()

            if (response.ok) {
                const data: LoginResponse = await response.json();
                if (data.isAuthenticated) {
                    userContext.setUserCredentials({ id: data.userId, name: data.username });
                    navigate('/');
                } else {
                    setRegistrationError(data.message || 'Authentication failed. Please check your credentials.');
                }
            } else {
                setRegistrationError(data.message || 'An error occurred during login. Please try again.');
            }
            console.log("Login response:", data);
        } catch (error) {
            console.error("Error during login:", error);
            setRegistrationError("Login failed due to a network or server issue. Please try again.");
        }
    };

    return (
        <LoginForm
            handleForm={handleForm}
            handleUsernameChange={handleUsernameChange}
            handlePasswordChange={handlePasswordChange}
            username={credentials.username}
            password={credentials.password}
            registrationError={registrationError}
        />
    );
};
