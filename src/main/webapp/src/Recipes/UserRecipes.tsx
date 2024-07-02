import React, { useEffect, useState } from "react"
import { UserRecipesComponent } from "./UserRecipesComponent";
import { useUserContext } from "../Contexts/context";
import { Recipe } from "./AllRecipes";

export const UserRecipes: React.FC = () => {
    const [recipes, setRecipe] = useState<Recipe[]>([]);

    const { userCredentials } = useUserContext();

    useEffect(() => {
        fetchUserData();
    }, [userCredentials.id]);

    const fetchUserData = async () => {
        try {
            if (!userCredentials.id) {
                return
            }; // Don't fetch if there's no user id

            const response = await fetch(`/recipe/recipesFromUser/${userCredentials.id}`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data: Recipe[] = await response.json();
            setRecipe(data);
        } catch (error) {
            console.error("Error fetching recipes: ", error)
        }
    }

    return (
        <div>
            <h1>Your saved recipes</h1>
            <UserRecipesComponent recipes={recipes} />
        </div>
    )
}
