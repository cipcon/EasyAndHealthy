import { useLocation } from "react-router-dom"
import { Ingredient } from "./AllRecipes";
import React, { useEffect, useState } from "react";

interface RecipeDataProps {
    ingredientId: number;
    ingredientName: string;
    unit: string;
    quantity: number;
}

export const RecipeDetails: React.FC = () => {
    const location = useLocation();
    const { recipe } = location.state || {};
    const { recipeDetails } = location.state || {};
    const [apiResponse, setApiResponse] = useState<RecipeDataProps[]>();

    useEffect(() => {
        if (recipeDetails && !recipe) {
            fetchIngredients(recipeDetails.recipeId);
        }
    }, [recipeDetails])

    if (!recipe && !recipeDetails) {
        return <h1>No recipe data available</h1>;
    }

    const fetchIngredients = async (recipeId: number) => {
        try {
            const response = await fetch('/ingredients/recipeIngredients', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(recipeId)
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data: RecipeDataProps[] = await response.json();
            setApiResponse(data);
        } catch (error) {
            console.error("Error fetching recipe data:", error)
        }

    }

    return (
        <div style={{ textAlign: "center" }}>
            {
                recipe
                    ?
                    <div>
                        <h1>{recipe.recipeName}</h1>
                        <h5>{recipe.servings} Servings</h5>
                        <ul style={{ listStyleType: "none", padding: 0 }}>
                            {recipe.ingredients.map((ingredient: Ingredient) =>
                                <li key={ingredient.ingredientId}>
                                    {ingredient.ingredientName}: {ingredient.quantity} {ingredient.unit}
                                </li>
                            )}
                        </ul>
                    </div>
                    :
                    <div>
                        <h1>{recipeDetails.recipeName}</h1>
                        <h5>{recipeDetails.servings} Servings</h5>
                        <ul style={{ listStyleType: "none", padding: 0 }}>
                            {apiResponse?.map((ingredient: Ingredient) =>
                                <li key={ingredient.ingredientId}>
                                    {ingredient.ingredientName}: {ingredient.quantity} {ingredient.unit}
                                </li>
                            )}
                        </ul>
                    </div>
            }

        </div>
    )
}