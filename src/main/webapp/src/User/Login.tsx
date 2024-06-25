import { ChangeEvent, FormEvent, useState } from "react";
import { useNavigate } from "react-router-dom";
import { LoginForm } from "./components/LoginForm";

interface UserCredentials {
    username: string;
    password: string;
}

export const Login = () => {
    const [credentials, setCredentials] = useState<UserCredentials>({ username: "", password: "" });
    const [wrongCredentials, setWrongCredentials] = useState('');
    const navigate = useNavigate();

    const handleUsernameChange = (event: ChangeEvent<HTMLInputElement>) => {
        setCredentials({ ...credentials, username: event.target.value });
    };

    const handlePasswordChange = (event: ChangeEvent<HTMLInputElement>) => {
        setCredentials({ ...credentials, password: event.target.value });
    };

    const handleForm = async (event: FormEvent) => {
        event.preventDefault();
        try {
            const response = await fetch(`/login/${credentials.username}/${credentials.password}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) {
                setWrongCredentials('Wrong username or Password, please try again');
                throw new Error(`HTTP error! status: ${response.status}`);
            } else {
                setWrongCredentials('');
                navigate('/');
            }

            const data = await response.text(); // Change to response.text() for a plain text response
            console.log("Login response:", data);


        } catch (error) {
            console.error("Error during login:", error);
        }
    };

    return (
        <LoginForm
            handleForm={handleForm}
            handleUsernameChange={handleUsernameChange}
            handlePasswordChange={handlePasswordChange}
            username={credentials.username}
            password={credentials.password}
            wrongCredentials={wrongCredentials}
        />
    );
};
