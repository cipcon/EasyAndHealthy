import { useUserContext } from "../context"

export const Home = () => {

    const user = useUserContext();
    return (
        <h1>{user.userCredentials.name === '' ?
            "Welcome to the Home page" :
            "Hello " + user.userCredentials.name + ", you are successfully registered"}
        </h1>
    )
}