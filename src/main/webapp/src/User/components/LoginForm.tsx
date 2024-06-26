import React, { ChangeEvent, FormEvent } from "react"

interface LoginFormProps {
    handleForm: (event: FormEvent) => void;
    handleUsernameChange: (event: ChangeEvent<HTMLInputElement>) => void;
    handlePasswordChange: (event: ChangeEvent<HTMLInputElement>) => void;
    username: string;
    password: string;
    registrationError: string;
}

export const LoginForm: React.FC<LoginFormProps> = ({
    handleForm,
    handleUsernameChange,
    handlePasswordChange,
    username,
    password,
    registrationError
}) => {
    return (
        <form onSubmit={handleForm}>
            <div className="row mb-3">
                <label htmlFor="inputUsername" className="col-sm-2 col-form-label">Username</label>
                <div className="col-sm-10">
                    <input
                        type="text"
                        className="form-control"
                        id="inputUsername"
                        value={username}
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
                        value={password}
                        onChange={handlePasswordChange}
                    />
                </div>
            </div>
            <button type="submit" className="btn btn-primary">Sign in</button>
            <p>{registrationError}</p>
        </form>
    )
}