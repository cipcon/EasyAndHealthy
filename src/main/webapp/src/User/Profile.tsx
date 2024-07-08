import { useNavigate } from "react-router-dom";
import { DeleteAccount, DeleteAccountProps } from "./DeleteAccount";
import { useState } from "react";
import { useUserContext } from "../Contexts/Context";


export const Profile = () => {

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
        <div className="profile">
            <DeleteAccount alertVisible={alertVisible} apiResponse={apiResponse} onClick={() => removeUser()} setAlertVisibility={setAlertVisibility} />
        </div>
    );
}
