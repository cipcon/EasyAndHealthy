import { useUserContext } from "../Contexts/context"

export const Home = () => {

    const { userCredentials } = useUserContext();
    return (
        <h1>{userCredentials.name === '' ?
            "Welcome to the Home page" :
            "Hello " + userCredentials.name + ", how are you today"}
        </h1>
    )
}