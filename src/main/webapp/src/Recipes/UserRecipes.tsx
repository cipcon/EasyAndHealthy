import React, { useEffect, useState } from "react";
import { UserRecipesComponent } from "../components/UserRecipesComponent";
import { useUserContext } from "../Contexts/Context";
import { Recipe } from "./AllRecipes";

export const UserRecipes: React.FC = () => {
    const [recipes, setRecipe] = useState<Recipe[]>([]);
    const { userCredentials } = useUserContext();

    const fetchUserData = async (id: number) => {
        try {
            if (!id) {
                return;
            }; // Don't fetch if there's no user id

            const response = await fetch('/recipe/recipesFromUser', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ id: id })
            });
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data: Recipe[] = await response.json();
            setRecipe(data);
        } catch (error) {
            console.error("Error fetching recipes: ", error)
        }
    }

    useEffect(() => {
        fetchUserData(userCredentials.id);
    }, [userCredentials.id]);



    return (
        <>
            <UserRecipesComponent recipes={recipes} />
        </>
    )
}