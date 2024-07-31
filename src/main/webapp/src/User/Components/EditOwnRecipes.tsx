import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';
import { useUserContext } from '../../Contexts/Context';
import './User.css'
import { Recipe } from '../../Homepage/NoIdeaMode';
import { Alert } from '../../components/Alert';
import { AlertColor } from '../../Ingredients/Components/AddIngredients';
import Button from '../../components/Button';


const EditOwnRecipes: React.FC = () => {
    const navigate = useNavigate();
    const { userCredentials } = useUserContext();
    const [recipes, setRecipe] = useState<Recipe[]>([]);
    const [alertVisible, setAlertVisibility] = useState<boolean>(false);
    const [alertColor, setAlertColor] = useState<AlertColor>();
    const [message, setMessage] = useState<string>();

    const handleClick = (recipeWithoutIngredients: Recipe) => {
        navigate('/recipeDetails', { state: { recipeWithoutIngredients } })
    }

    const removeRecipe = async (recipeId: number, recipeName: string) => {
        const isConfirmed = window.confirm(`Are you sure you want to delete "${recipeName}"? This action cannot be undone.`)

        if (!isConfirmed) return;

        const request = {
            recipeId: recipeId,
            userId: userCredentials.id
        }
        try {
            const response = await fetch('/recipe/deleteRecipeGlobally', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(request)
            })
            if (!response.ok) {
                throw new Error(`Failed to delete recipe; status: ${response.status}`);
            }
            setMessage(recipeName + ' successfully deleted');
            setAlertColor('success');
        } catch (error) {
            setMessage('Something went wrong, please try again later or contact support');
            setAlertColor('warning');
            console.error("Error fetching recipes: ", error)
        }
        setAlertVisibility(true);
    }

    useEffect(() => {
        fetchOwnRecipes();
        // eslint-disable-next-line
    }, [userCredentials]);

    const fetchOwnRecipes = async () => {
        try {
            const response = await fetch('/recipe/recipesCreatedByUser', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(userCredentials.id)
            });
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data: Recipe[] = await response.json();
            setRecipe(data);
        } catch (error) {
            console.error("Error fetching recipes: ", error)
        }
    }


    const handleCreate = () => {
        navigate('/create-recipe');
    }

    const handleEdit = (recipe: Recipe) => {
        navigate('/edit-recipe', { state: { recipe } });
    }

    return (
        <>
            <h5 className='center-h1'>Add, edit or delete your own recipes</h5>
            <div className='add-button'>
                <Button children='Create new recipe' color='success' onClick={() => handleCreate()} type='button' />
            </div>
            {alertVisible && <Alert color={alertColor} message={message} onClose={() => setAlertVisibility(false)} />}

            <ul className="recipes">
                {recipes.length === 0 ?
                    <p>No own recipes found.</p>
                    :
                    recipes.map((recipe) => (
                        <li key={recipe.recipeId} className='own-recipes'>
                            <a href="/recipeDetails" className="recipe-name" onClick={() => handleClick(recipe)}>
                                {recipe.recipeName}
                            </a>
                            <div className='edit-delete'>
                                <button className='edit' onClick={() => handleEdit(recipe)}>
                                    Edit
                                </button>
                                <button className='delete' onClick={() => removeRecipe(recipe.recipeId, recipe.recipeName)}>
                                    Delete
                                </button>
                            </div>
                        </li>
                    ))
                }
            </ul>
        </>
    )
}

export default EditOwnRecipes