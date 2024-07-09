import { useLocation } from "react-router-dom"
import { Ingredient } from "./AllRecipes";

export const RecipeDetails = () => {
    const location = useLocation();
    const { recipe } = location.state || {};
    if (!recipe) {
        return <h1>No recipe data available</h1>;
    }

    return (
        <div style={{ textAlign: "center" }}>
            <h1>{recipe.recipeName}</h1>
            <ul style={{ listStyleType: "none", padding: 0 }}>
                {recipe.ingredients.map((ingredient: Ingredient) =>
                    <li key={ingredient.ingredientId}>
                        {ingredient.ingredientName}: {ingredient.quantity} {ingredient.unit}
                    </li>
                )}
            </ul>
        </div>
    )
}