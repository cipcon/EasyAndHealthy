import { ChangeEvent, FormEvent, useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import { RegisterForm } from "./components/RegisterForm";
import { useUserContext } from "../Contexts/context";

interface UserCredentials {
    username: string;
    password: string;
}

interface RegisterResponse {
    status: string;
    message: string;
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

            if (!response.ok) {
                setRegistrationError(data.message);
            } else {
                userContext.setUserCredentials({ id: data.userId, name: data.username, token: '' });
                navigate('/');
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