import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';
import Button from '../../components/Button';
import { Recipe } from '../../Homepage/NoIdeaMode';
import { Alert } from '../../components/Alert';
import { useUserContext } from '../../Contexts/Context';
import { AlertColor } from '../../Ingredients/Components/AddIngredients';
import { ApiResponse } from './AddRecipeComponent';

interface Props {
    setSearch: React.Dispatch<React.SetStateAction<boolean>>
}

export const SearchRecipe: React.FC<Props> = ({ setSearch }) => {
    const { userCredentials } = useUserContext();
    const [message, setMessage] = useState<string>('');
    const [alertColor, setAlertColor] = useState<AlertColor>();
    const [alertVisibility, setAlertVisibility] = useState<boolean>(false);
    const [alertVisible, setAlertVisible] = useState(false);
    const [recipeName, setRecipeName] = useState('');
    const [recipes, setRecipes] = useState<Recipe[]>([]);
    const [name, setName] = useState<string>('');
    const navigate = useNavigate();


    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        event.preventDefault();
        setName(event.target.value);
        fetchData();
    }

    const handleClick = (recipeWithoutIngredients: Recipe) => {
        navigate('/recipeDetails', { state: { recipeWithoutIngredients } });
    }

    const fetchData = async () => {
        console.log(name);
        try {
            const response = await fetch('/searchRecipe/recipeSearch', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(name)
            })
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data: Recipe[] = await response.json();
            setRecipes(data);
        } catch (error) {
            console.error("Error fetching recipes:", error)
        }
        console.log(recipes);
        setAlertVisibility(true);
        setSearch(true);
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
        setAlertVisible(true);
    }

    return (
        <div>
            <form className='search-recipe' onSubmit={fetchData}>
                <input
                    className="form-control"
                    id='recipe'
                    placeholder='Recipe or ingredient name'
                    type="text"
                    onChange={handleChange}
                />
                <div className='center-button'>
                    <Button color='success' children='All Recipes' type='submit' />
                </div>

            </form>
            {alertVisibility ? (<div style={{ marginTop: 10 }}>
                <h2 className='center-h1'>Recipes</h2>
                {alertVisible && <Alert color={alertColor} message={message} onClose={() => setAlertVisibility(false)} children={recipeName} type='button' />}
                {recipes.length > 0 ? <ul className="recipes">
                    {recipes.map((recipe) => (
                        <li key={recipe.recipeId}>
                            <a href="/recipeDetails" className="recipe-name" onClick={() => handleClick(recipe)}>
                                {recipe.recipeName}
                            </a>
                            <Button color='success' children='Add' heart='&#x1F49A;' onClick={() => handleRecipeAdd(recipe.recipeId, userCredentials.id, recipe.recipeName)} type='button' />
                        </li>
                    ))}
                </ul> :
                    <p className='center-h1'>No recipes found</p>}
            </div>
            ) : (
                '')}
        </div>
    )
}
