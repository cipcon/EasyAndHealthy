import { ChangeEvent, FormEvent, useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { RegisterForm } from "./components/RegisterForm";
import { useUserContext } from "../Contexts/context";
import { generateToken } from "./components/GenerateToken";

interface UserCredentials {
    username: string;
    password: string;
}

interface RegisterResponse {
    isAuthenticated: boolean;
    message: string;
    status: string;
    userId: number;
    username: string;
}

export const Register = () => {
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
            userContext.setUserCredentials({ id: JSON.parse(storedUser).id, name: JSON.parse(storedUser).name, token: storedToken })
            navigate('/');
        }
    }, [navigate, userContext]);

    const handleForm = async (event: FormEvent) => {
        event.preventDefault();

        // Validation logic
        if (!credentials.username) {
            setRegistrationError('Username is required.');
            return;
        }

        if (!credentials.password) {
            setRegistrationError('Password is required.');
            return;
        }
        try {
            const response = await fetch('/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(credentials) // Sending credentials in the request body
            });

            const data: RegisterResponse = await response.json();

            if (response.ok) {
                if (data.isAuthenticated) {
                    const token = generateToken(data.userId);
                    userContext.setUserCredentials({ id: data.userId, name: data.username, token });
                    localStorage.setItem('user', JSON.stringify({ id: data.userId, name: data.username }))
                    localStorage.setItem('token', token);
                    navigate('/');
                } else {
                    setRegistrationError(data.message || 'Registration failed. Please check your credentials.');
                }

            } else {
                setRegistrationError(data.message || 'An error occurred during registration. Please try again.');
            }
        } catch (error) {
            console.error("Error during registration:", error);
            setRegistrationError("Registration failed due to a network or server issue. Please try again.");

        }
    };

    return (
        <RegisterForm
            handleForm={handleForm}
            handleUsernameChange={handleUsernameChange}
            handlePasswordChange={handlePasswordChange}
            username={credentials.username}
            password={credentials.password}
            registrationError={registrationError}
        />
    )
}