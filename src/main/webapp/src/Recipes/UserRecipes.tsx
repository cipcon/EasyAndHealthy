import React, { useEffect, useState } from "react"
import { UserRecipesComponent } from "./UserRecipesComponent";
import { useUserContext } from "../Contexts/context";

export interface RecipeRequest {
    recipeName: string;
    recipeId: number;
}

export const UserRecipes: React.FC = () => {
    const [recipes, setRecipe] = useState<RecipeRequest[]>([]);

    const { userCredentials } = useUserContext();

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                if (!userCredentials.id) {
                    return
                }; // Don't fetch if there's no user id

                const response = await fetch(`/recipe/recipesFromUser/${userCredentials.id}`);
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                const data: RecipeRequest[] = await response.json();
                setRecipe(data);
            } catch (error) {
                console.error("Error fetching recipes: ", error)
            }
        }
        fetchUserData();
    }, [userCredentials.id]);

    return (
        <div>
            <h1>Your saved recipes</h1>
            <UserRecipesComponent recipes={recipes} />
        </div>
    )
}