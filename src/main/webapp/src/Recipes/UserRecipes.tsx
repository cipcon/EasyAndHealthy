import React, { useEffect, useState } from "react";
import { useUserContext } from "../Contexts/Context";
import { UserRecipesComponent } from "./Components/UserRecipesComponent";


export interface Ingredient {
    ingredientName: string;
    ingredientId: number;
    quantity: number;
    unit: string;
}

export interface Recipe {
    recipeName: string;
    recipeId: number;
    servings: number;
    ingredients: Ingredient[];
}

export const UserRecipes: React.FC = () => {
    const [recipes, setRecipe] = useState<Recipe[]>([]);
    const { userCredentials } = useUserContext();

    useEffect(() => {
        fetchUserData(userCredentials.id);
    }, [userCredentials.id]);

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
                body: JSON.stringify(id)
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
    return (
        <>
            <UserRecipesComponent recipes={recipes} />
        </>
    )
}