import React, { useState } from "react";
import { Recipe } from "../Recipes/AllRecipes"
import { useNavigate } from "react-router-dom";
import Button from "./Button";
import { Alert } from "./Alert";
import { useUserContext } from "../Contexts/Context";

interface Props {
    recipes: Recipe[];
}

interface ApiResponse {
    recipeAdded: boolean;
    message: string;
}


export const AddRecipeComponent: React.FC<Props> = ({ recipes }) => {
    const navigate = useNavigate();
    const [alertVisible, setAlertVisibility] = useState(false);
    const { userCredentials } = useUserContext();
    const [recipeName, setRecipeName] = useState('');
    const [apiResponse, setApiResponse] = useState<ApiResponse>({ recipeAdded: false, message: '' });

    const handleClick = (recipe: Recipe) => {
        navigate('/recipeDetails', { state: { recipe } });
    }

    const handleRecipeAdd = async (recipeId: number, userId: number, recipeName: string) => {
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
                setApiResponse({ recipeAdded: data.recipeAdded, message: data.message });
                setRecipeName(recipeName);
                setAlertVisibility(true);
            } else {
                console.log(response);
                setApiResponse({ recipeAdded: false, message: 'Failed to add recipe' });
            }
        } catch (error) {
            console.error('Error adding recipe:', error);
            setApiResponse({ recipeAdded: false, message: 'Something went wrong, please try again later' });
        }
    }


    return (
        <>
            <ul>
                {alertVisible && <Alert message={apiResponse.message} onClose={() => setAlertVisibility(false)} recipe={recipeName} type="success" />}
                {recipes.map((recipe) => (
                    <li key={recipe.recipeId} className="link-margin-top">
                        <div>
                            <a href="/recipeDetails" className="recipes" onClick={() => handleClick(recipe)}>
                                {recipe.recipeName}
                            </a>
                            <Button color='success' onClick={() => handleRecipeAdd(recipe.recipeId, userCredentials.id, recipe.recipeName)} type="Add" />
                        </div>
                    </li>
                ))}
            </ul>

        </>
    )
}
