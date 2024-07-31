import { useEffect } from "react";
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
            {userCredentials.token === 'null' ?
                <div>
                    <h4>Welcome to the Home page, please login or register</h4>
                </div>
                :
                <div>
                    {userCredentials && <NoIdeaMode userId={userCredentials.id} userName={userCredentials.name} />}

                </div>
            }
        </>

    );

}