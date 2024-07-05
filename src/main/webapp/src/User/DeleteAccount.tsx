import React, { useState } from "react";
import { useUserContext } from "../Contexts/Context";
import { useNavigate } from "react-router-dom";
import { Alert } from "../components/Alert";
import Button from "../components/Button";

export interface DeleteAccountProps {
    deleted: boolean;
    message: string;
}

export const DeleteAccount: React.FC = () => {
    const { userCredentials, setUserCredentials } = useUserContext();
    const [apiResponse, setApiResponse] = useState<DeleteAccountProps | null>(null);
    const navigate = useNavigate();
    const [alertVisible, setAlertVisibility] = useState(false);

    const removeUser = async () => {
        try {
            const response = await fetch('/delete', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(userCredentials.id)
            });
            console.log("API response status:", response.status);

            const data: DeleteAccountProps = await response.json();
            console.log("API response data:", data);
            setApiResponse(data);
            setAlertVisibility(true);

            if (data.deleted) {
                setUserCredentials({ id: 0, name: '', token: '' });
                navigate('/', { state: { apiResponse: data } });
            }
        } catch (error) {
            console.error("Error during deletion:", error);
            setApiResponse({ deleted: false, message: 'There was an error processing your request. Please try again later.' });
            setAlertVisibility(true);
        }
    };

    return (
        <>
            <div className="same-line" style={{ marginBottom: 10 }}>
                <p className="vertical-center-align">Delete Account</p>
                <Button color="danger" onClick={removeUser} type="Delete" />
            </div>
            {alertVisible && apiResponse && (
                <Alert
                    message={apiResponse.message}
                    onClose={() => setAlertVisibility(false)}
                    type={apiResponse.deleted ? "success" : "primary"}
                />
            )}
        </>
    );
};