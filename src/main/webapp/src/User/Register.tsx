import { ChangeEvent, FormEvent, useState } from "react";
import { useNavigate } from "react-router-dom";
import { RegisterForm } from "./Components/RegisterForm";
import { useUserContext } from "../Contexts/Context";
import { generateToken } from "../components/GenerateToken";

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
            setRegistrationError('Username and password are required.');
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


            if (response.ok) {
                const data: RegisterResponse = await response.json();
                const token = generateToken(data.userId);
                setUserCredentials({ id: data.userId, name: data.username, token });
                navigate('/');
            } else {
                setRegistrationError('Registration failed. Please check your credentials.');
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