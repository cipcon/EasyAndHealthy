import { ChangeEvent, FormEvent, useState } from "react";
import { useNavigate } from "react-router-dom";
import { RegisterForm } from "./components/RegisterForm";

interface UserCredentials {
    username: string;
    password: string;
    userId: number;
}

export const Register = () => {
    const [credentials, setCredentials] = useState<UserCredentials>({ username: "", password: "", userId: 0 });
    const [registrationError, setRegistrationError] = useState('');

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

            const data = await response.text(); // Change to response.text() for a plain text response

            if (!response.ok) {
                setRegistrationError(data);
                throw new Error(`HTTP error! status: ${response.status}`);
            } else {
                setRegistrationError('');
                navigate('/');
            }

        } catch (error) {
            console.error("Error during registration:", error);
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