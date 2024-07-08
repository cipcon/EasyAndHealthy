import React from "react";
import { Alert } from "../components/Alert";
import Button from "../components/Button";

export interface DeleteAccountProps {
    deleted: boolean;
    message: string;
}

interface Props {
    onClick: () => void;
    alertVisible: boolean;
    apiResponse: DeleteAccountProps | null;
    setAlertVisibility: React.Dispatch<React.SetStateAction<boolean>>;

}

export const DeleteAccount: React.FC<Props> = ({ alertVisible, apiResponse, onClick, setAlertVisibility }) => {

    return (
        <>
            <h2>Your Profile</h2>
            <div className="same-line" style={{ marginBottom: 10 }} >
                <p className="vertical-center-align">Delete Account</p>
                <Button color="danger" onClick={onClick} type="Delete" />
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