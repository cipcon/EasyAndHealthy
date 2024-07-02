import { useLocation } from "react-router-dom"
import { Ingredient } from "./AllRecipes";

export const RecipeDetails = () => {
    const location = useLocation();
    const { recipe } = location.state || {};

    if (!recipe) {
        return <h1>No recipe data available</h1>;
    }

    return (
        <div>
            <h1>{recipe.recipeName}</h1>
            <ul>
                {recipe.ingredients.map((ingredient: Ingredient) =>
                    <li key={ingredient.ingredientId}>
                        {ingredient.ingredient}: {ingredient.quantity} {ingredient.unit}
                    </li>
                )}
            </ul>
        </div>
    )
}