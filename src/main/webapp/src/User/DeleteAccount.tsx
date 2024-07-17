import React from "react";
import { Alert } from "../components/Alert";
import Button from "../components/Button";
import { AlertColor } from "../Ingredients/Components/AddIngredients";

export interface DeleteAccountProps {
    deleted: boolean;
    message: string;
}


interface Props {
    onClick: () => void;
    alertVisible: boolean;
    apiResponse: DeleteAccountProps | null;
    setAlertVisibility: React.Dispatch<React.SetStateAction<boolean>>;
    alertColor: AlertColor;
}

export const DeleteAccount: React.FC<Props> = ({ alertVisible, alertColor, apiResponse, onClick, setAlertVisibility }) => {

    return (
        <div className="container">
            <h2 className="center-h1">Your Profile</h2>
            <div className="same-line" style={{ marginBottom: 10 }} >
                <p className="vertical-align">Delete Account</p>
                <Button color="danger" children='Delete' onClick={onClick} type='submit' />
            </div>
            {alertVisible && apiResponse && (
                <Alert
                    color={alertColor}
                    message={apiResponse.message}
                    onClose={() => setAlertVisibility(false)}
                    type='button'
                />
            )}
        </div>
    );
};