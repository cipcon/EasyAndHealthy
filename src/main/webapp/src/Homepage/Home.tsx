import { useUserContext } from "../Contexts/context"

export const Home = () => {

    const user = useUserContext();
    return (
        <h1>{user.userCredentials.name === '' ?
            "Welcome to the Home page" :
            "Hello " + user.userCredentials.name + ", how are you today"}
        </h1>
    )
}