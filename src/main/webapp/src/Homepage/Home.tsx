import { useLocation } from "react-router-dom"
import { useUserContext } from "../Contexts/Context"

export const Home = () => {
    const location = useLocation();
    const { apiResponse } = location.state || { apiResponse: null };
    const { userCredentials } = useUserContext();

    const getResponse = apiResponse && apiResponse.deleted ? (
        <h1>{apiResponse.message}</h1>
    ) : (
        <h1>{userCredentials.name === '' ?
            "Welcome to the Home page" :
            "Hello " + userCredentials.name + ", how are you today"}
        </h1>
    )


    return (
        <>
            {getResponse}
        </>

    );
}