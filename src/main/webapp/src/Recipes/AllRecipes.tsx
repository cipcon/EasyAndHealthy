import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export interface Ingredient {
    ingredient: string;
    ingredientId: number;
    quantity: number;
    unit: string;
}

export interface Recipe {
    recipeName: string;
    recipeId: number;
    ingredients: Ingredient[];
}

export const AllRecipes: React.FC = () => {
    const [recipes, setRecipe] = useState<Recipe[]>([]);
    const navigate = useNavigate();

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        try {
            const response = await fetch('/recipe/readAllRecipes');

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const data: Recipe[] = await response.json();
            setRecipe(data);
        } catch (error) {
            console.error("Error fetching recipes:", error);
        }
    };

    const handleClick = (recipe: Recipe) => {
        navigate('/recipeDetails', { state: { recipe } });
    }

    return (
        <div>
            <ul>
                {recipes.map((recipe) => (
                    <li key={recipe.recipeId}>
                        <div>
                            <a href="/recipeDetails" className="recipes" onClick={() => handleClick(recipe)}>
                                {recipe.recipeName}
                            </a>
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
}
