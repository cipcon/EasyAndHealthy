import React, { useEffect, useState } from 'react'
import '../Recipes/Recipes.css'
import { useNavigate } from 'react-router-dom';
import { UserProps } from './Home';
import Button from '../components/Button';
import { useUserContext } from '../Contexts/Context';
import { AlertColor } from '../Ingredients/Components/AddIngredients';
import { Alert } from '../components/Alert';

export interface Recipe {
    recipeName: string;
    recipeId: number;
    servings: number;
}

export interface ApiResponse {
    added: boolean;
    message: string;
}


export const NoIdeaMode: React.FC<UserProps> = ({ userId, userName }) => {
    const navigate = useNavigate();
    const [recipes, setRecipes] = useState<Recipe[]>([]);
    const [alertVisible, setAlertVisibility] = useState(false);
    const { userCredentials } = useUserContext();
    const [recipeName, setRecipeName] = useState('');
    const [apiResponse, setApiResponse] = useState<ApiResponse>({ added: false, message: '' });
    const [alertColor, setAlertColor] = useState<AlertColor>();

    useEffect(() => {
        fetchData();
        // eslint-disable-next-line
    }, [userCredentials.id]);

    const handleClick = (recipeWithoutIngredients: Recipe) => {
        navigate('/recipeDetails', { state: { recipeWithoutIngredients } });
    }

    const fetchData = async () => {
        try {
            const response = await fetch('/searchRecipe/noIdeaMode', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(userId)
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data: Recipe[] = await response.json();
            setRecipes(data);
        } catch (error) {
            console.error("Error fetching recipes:", error)
        }
        console.log(recipes);
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
        <>
            <h4>Hello {userName}, how are you today?</h4>
            <hr />

            {alertVisible && <Alert color={alertColor} message={apiResponse.message} onClose={() => setAlertVisibility(false)} children={recipeName} type='button' />}
            {recipes?.length > 0 ?
                <div>
                    <h5>Based on your ingredients:</h5>
                    <ul className="recipes">
                        {recipes?.map((recipe) => (
                            <li key={recipe.recipeId} className='li-ingredients'>
                                <a href="/recipeDetails" className='recipe-name' onClick={() => handleClick(recipe)}>
                                    {recipe.recipeName}
                                </a>
                                <Button color='success' children='Add' heart='&#x1F49A;' onClick={() => handleRecipeAdd(recipe.recipeId, userCredentials.id, recipe.recipeName)} type='button' />
                            </li>
                        ))}
                    </ul>
                </div> :
                <div>
                    {userCredentials.id === 0 ?
                        <h5>You can start by creating and account or login, search for recipes and so on</h5> :
                        <h5>You can start by adding Ingredients or recipes in your list, create new recipes and so on</h5>
                    }

                </div>}

        </>
    )
}
