import { useEffect, useState } from "react";
import { AddRecipeComponent, ApiResponse } from "./Components/AddRecipeComponent";
import { SearchRecipe } from "./Components/SearchRecipe";
import { AlertColor } from "../Ingredients/Components/AddIngredients";
import { useUserContext } from "../Contexts/Context";

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
    const { userCredentials } = useUserContext();
    const [recipes, setRecipe] = useState<Recipe[]>([]);
    const [search, setSearch] = useState<boolean>(false);
    const [recipeName, setRecipeName] = useState('');
    const [message, setMessage] = useState<string>('');
    const [alertColor, setAlertColor] = useState<AlertColor>();
    const [alertVisible, setAlertVisibility] = useState(false);


    useEffect(() => {
        fetchData();
    }, []);

    const handleRecipeAdd = async (recipeId: number, recipeName: string) => {
        const userId = userCredentials.id;
        try {
            const response = await fetch('/recipe/addRecipeToUser', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ recipeId, userId })
            });
            console.log(response);

            if (response.ok) {
                const data: ApiResponse = await response.json();
                setMessage(data.message);
                setRecipeName(recipeName);
                setAlertVisibility(true);
                setAlertColor('success')
            } else {
                setMessage('Failed to add recipe');
                setAlertColor('warning')
            }
        } catch (error) {
            console.error('Error adding recipe:', error);
            setMessage('Something went wrong, please try again later');
            setAlertColor('warning')
        }
    }

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
            <SearchRecipe setSearch={setSearch} />
            {!search &&
                <AddRecipeComponent
                    recipes={recipes}
                    alertVisible={alertVisible}
                    alertColor={alertColor}
                    message={message}
                    setAlertVisibility={setAlertVisibility}
                    recipeName={recipeName}
                    handleRecipeAdd={handleRecipeAdd} />}

        </>
    );
}
