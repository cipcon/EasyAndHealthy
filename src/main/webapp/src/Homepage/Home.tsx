import { useUserContext } from "../Contexts/Context"
import { NoIdeaMode } from "./NoIdeaMode";

export interface UserProps {
    userId: number;
    userName: string;
}


export const Home = () => {
    const { userCredentials } = useUserContext();
    return (
        <>
            <div>
                {userCredentials.name === '' ?
                    <h4>Welcome to the Home page, please login or register</h4>
                    :
                    <div>
                        <NoIdeaMode userId={userCredentials.id} userName={userCredentials.name} />
                    </div>
                }
            </div>
        </>

    );
}