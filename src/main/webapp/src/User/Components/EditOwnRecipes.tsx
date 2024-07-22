import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';
import { useUserContext } from '../../Contexts/Context';
import './User.css'
import { Recipe } from '../../Homepage/NoIdeaMode';


const EditOwnRecipes: React.FC = () => {
    const navigate = useNavigate();
    const { userCredentials } = useUserContext();
    const [recipes, setRecipe] = useState<Recipe[]>([]);

    useEffect(() => {
        fetchOwnRecipes();
        // eslint-disable-next-line
    }, [userCredentials]);

    const handleClick = (recipeWithoutIngredients: Recipe) => {
        navigate('/recipeDetails', { state: { recipeWithoutIngredients } })
    }

    const removeRecipe = async (recipeId: number, userId: number) => {
        const request = {
            recipeId: recipeId,
            userId: userId
        }
        try {
            const response = await fetch('/createRecipe/deleteRecipeGlobally', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(request)
            })
        } catch (error) {

        }
    }

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

    return (
        <>
            <h5 className='center-h1'>Add, edit or delete your own recipes</h5>
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
                                <button className='edit'>
                                    Edit
                                </button>
                                <button className='delete'>
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