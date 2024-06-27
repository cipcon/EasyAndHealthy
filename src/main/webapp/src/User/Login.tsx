import { ChangeEvent, FormEvent, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { LoginForm } from "./components/LoginForm";
import { useUserContext } from "../Contexts/context";

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

const generateToken = (userId: number) => {
    return `${userId}-${Math.random().toString(36).substring(2)}`
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

    useEffect(() => {
        const storedUser = localStorage.getItem('user');
        const storedToken = localStorage.getItem('token');
        if (storedUser && storedToken) {
            userContext.setUserCredentials({ id: JSON.parse(storedUser).id, name: JSON.parse(storedUser).name, token: storedToken });
            navigate('/');
        }
    }, [navigate, userContext]);

    const handleForm = async (event: FormEvent) => {
        event.preventDefault();

        // Validation logic
        if (!credentials.username) {
            setRegistrationError('Username is required');
            return;
        }

        if (!credentials.password) {
            setRegistrationError('Password is required');
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
            const data: LoginResponse = await response.json();

            if (response.ok) {
                if (data.isAuthenticated) {
                    const token = generateToken(data.userId);
                    userContext.setUserCredentials({ id: data.userId, name: data.username, token });
                    localStorage.setItem('user', JSON.stringify({ id: data.userId, name: data.username }));
                    localStorage.setItem('token', token)
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
