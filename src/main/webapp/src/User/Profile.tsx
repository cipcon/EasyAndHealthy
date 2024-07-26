import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { useUserContext } from "../Contexts/Context";
import { AlertColor } from "../Ingredients/Components/AddIngredients";
import { DeleteAccountProps, DeleteAccount } from "./DeleteAccount";

export const Profile = () => {

    const { userCredentials, setUserCredentials } = useUserContext();
    const [apiResponse, setApiResponse] = useState<DeleteAccountProps | null>(null);
    const navigate = useNavigate();
    const [alertVisible, setAlertVisibility] = useState(false);
    const [alertColor, setAlertColor] = useState<AlertColor>();

    const removeUser = async () => {
        const isConfirmed = window.confirm(`Are you sure you want to delete your Account? This action cannot be undone.`)

        if (!isConfirmed) return;

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
            if (!data.deleted) {
                setApiResponse({ deleted: data.deleted, message: data.message });
                setAlertColor('warning');
            }
            if (data.deleted) {
                setUserCredentials({ id: 0, name: '', token: null });
                navigate('/', { state: { apiResponse: data } });
            }
        } catch (error) {
            console.error("Error during deletion:", error);
            setApiResponse({ deleted: false, message: 'There was an error processing your request. Please try again later.' });
            setAlertColor('warning');
        }
        setAlertVisibility(true);
    };

    return (
        <>
            <DeleteAccount alertVisible={alertVisible} alertColor={alertColor} apiResponse={apiResponse} onClick={() => removeUser()} setAlertVisibility={setAlertVisibility} />
        </>
    );
}
