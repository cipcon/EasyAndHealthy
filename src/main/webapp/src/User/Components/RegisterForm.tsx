import React from 'react'

interface RegisterFormProps {
    handleForm: (event: React.FormEvent) => void;
    handleUsernameChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
    handlePasswordChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
    username: string;
    password: string;
    registrationError: string;
}

export const RegisterForm: React.FC<RegisterFormProps> = ({
    handleForm,
    handleUsernameChange,
    handlePasswordChange,
    username,
    password,
    registrationError,
}) => (
    <>
        <h2>Create a new Account</h2>
        <form action="POST" onSubmit={handleForm}>
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
                        minLength={8}
                        pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                        title="Must contain at least one  number and one uppercase and lowercase letter, and at least 8 or more characters"
                    />
                </div>
            </div>
            <button type="submit" className="btn btn-primary">Register</button>
            <p>{registrationError}</p>
        </form>
    </>
)
