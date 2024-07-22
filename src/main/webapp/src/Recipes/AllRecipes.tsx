import { useEffect, useState } from "react";
import { AddRecipeComponent } from "./Components/AddRecipeComponent";
import { SearchRecipe } from "./Components/SearchRecipe";

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

export const AllRecipes: React.FC = () => {
    const [recipes, setRecipe] = useState<Recipe[]>([]);

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

    return (
        <>
            <SearchRecipe />
            <AddRecipeComponent recipes={recipes} />
        </>
    );
}
