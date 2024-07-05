import { UserRecipes } from "../Recipes/UserRecipes";
import { DeleteAccount } from "./DeleteAccount";


export const Profile = () => {

    return (
        <>
            <UserRecipes />
            <hr />
            <DeleteAccount />
        </>
    )
}
