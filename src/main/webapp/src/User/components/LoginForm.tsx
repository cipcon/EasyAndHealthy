import React, { ChangeEvent, FormEvent } from "react"

interface LoginFormProps {
    handleForm: (event: FormEvent) => void;
    handleUsernameChange: (event: ChangeEvent<HTMLInputElement>) => void;
    handlePasswordChange:  (event: ChangeEvent<HTMLInputElement>) => void;
    username: string;
    password: string;
    wrongCredentials: string;
}

export const LoginForm: React.FC<LoginFormProps> = (props) => {
    return (
        <form onSubmit={props.handleForm}>
            <div className="row mb-3">
                <label htmlFor="inputUsername" className="col-sm-2 col-form-label">Username</label>
                <div className="col-sm-10">
                    <input
                        type="text"
                        className="form-control"
                        id="inputUsername"
                        value={props.username}
                        onChange={props.handleUsernameChange}
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
                        value={props.password}
                        onChange={props.handlePasswordChange}
                    />
                </div>
            </div>
            <button type="submit" className="btn btn-primary">Sign in</button>
            <p>{props.wrongCredentials}</p>
        </form>
    )
}