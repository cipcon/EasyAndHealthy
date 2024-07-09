import '../Recipes.css'
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Recipe } from '../AllRecipes';
import { useUserContext } from '../../Contexts/Context';
import { Alert } from '../../components/Alert';
import Button from '../../components/Button';
import { AlertColor } from '../../Ingredients/AddIngredients';

interface Props {
    recipes: Recipe[];
}

export interface ApiResponse {
    added: boolean;
    message: string;
}


export const AddRecipeComponent: React.FC<Props> = ({ recipes }) => {
    const navigate = useNavigate();
    const [alertVisible, setAlertVisibility] = useState(false);
    const { userCredentials } = useUserContext();
    const [recipeName, setRecipeName] = useState('');
    const [apiResponse, setApiResponse] = useState<ApiResponse>({ added: false, message: '' });
    const [alertColor, setAlertColor] = useState<AlertColor>();


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
                setApiResponse({ added: data.added, message: data.message });
                setRecipeName(recipeName);
                setAlertVisibility(true);
                setAlertColor('success')
            } else {
                setApiResponse({ added: false, message: 'Failed to add recipe' });
                setAlertColor('warning')
            }
        } catch (error) {
            console.error('Error adding recipe:', error);
            setApiResponse({ added: false, message: 'Something went wrong, please try again later' });
            setAlertColor('warning')
        }
    }


    return (
        <div>
            <h2 className='center-h1'>Recipes</h2>
            {alertVisible && <Alert color={alertColor} message={apiResponse.message} onClose={() => setAlertVisibility(false)} recipe={recipeName} type='button' />}
            <ul className="recipes" style={{ gap: 50 }}>
                {recipes.map((recipe) => (
                    <li key={recipe.recipeId} className='row g-3'>
                        <a href="/recipeDetails" className="recipe-name col-auto" onClick={() => handleClick(recipe)}>
                            {recipe.recipeName}
                        </a>
                        <Button color='success' children='Add' onClick={() => handleRecipeAdd(recipe.recipeId, userCredentials.id, recipe.recipeName)} type='button' />
                    </li>
                ))}
            </ul>

        </div>
    )
}
