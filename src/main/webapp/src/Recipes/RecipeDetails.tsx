import { useLocation } from "react-router-dom"
import { Ingredient } from "./AllRecipes";
import React, { useEffect, useState } from "react";

export const RecipeDetails: React.FC = () => {
    const location = useLocation();
    const { recipe } = location.state || {};
    const { recipeDetails } = location.state || {};
    const [ingredients, setIngredients] = useState<Ingredient[]>([]);

    useEffect(() => {
        if (recipeDetails) {
            fetchIngredients(recipeDetails.recipeId);
        }
        // eslint-disable-next-line
    }, [])

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
            const data: Ingredient[] = await response.json();
            setIngredients(data);
        } catch (error) {
            console.error("Error fetching ingredients:", error)
        }
        console.log(ingredients);
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
                            {ingredients?.map((ingredient: Ingredient) =>
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