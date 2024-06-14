import { ChangeEvent, FormEvent, useState } from "react";
import { useNavigate } from "react-router-dom";

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

    const handleSigninButton = async (event: FormEvent) => {
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
        <form onSubmit={handleSigninButton}>
            <div className="row mb-3">
                <label htmlFor="inputUsername" className="col-sm-2 col-form-label">Username</label>
                <div className="col-sm-10">
                    <input
                        type="text"
                        className="form-control"
                        id="inputUsername"
                        value={credentials.username}
                        onChange={handleUsernameChange}
                    />
                </div>
            </div>
            <div className="row mb-3">
                <label htmlFor="inputPassword" className="col-sm-2 col-form-label">Password</label>
                <div className="col-sm-10">
                    <input
                        type="password"
                        className="form-control"
                        id="inputPassword"
                        value={credentials.password}
                        onChange={handlePasswordChange}
                    />
                </div>
            </div>
            <button type="submit" className="btn btn-primary">Sign in</button>
            <p>{wrongCredentials}</p>
        </form>
    );
};
