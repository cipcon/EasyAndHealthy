import { useNavigate } from "react-router-dom";
import { Recipe } from "../Recipes/AllRecipes";
import { useUserContext } from "../Contexts/Context";
import { useEffect, useState } from "react";
import Button from "./Button";
import { Alert } from "./Alert";

export interface UserRecipesProps {
    recipes: Recipe[];
}

export const UserRecipesComponent: React.FC<UserRecipesProps> = ({ recipes }) => {
    const navigate = useNavigate();
    const { userCredentials } = useUserContext();
    const [message, setMessage] = useState('');
    const [localRecipes, setLocalRecipes] = useState<Recipe[]>(recipes);
    const [recipe, setRecipe] = useState('');
    const [alertVisible, setAlertVisibility] = useState(false);

    useEffect(() => {
        setLocalRecipes(recipes);
    }, [recipes]);

    const handleClick = (recipe: Recipe) => {
        navigate('/recipeDetails', { state: { recipe } });
    }


    const removeRecipe = async (recipeId: number, userId: number, recipeName: string) => {
        try {
            const response = await fetch('/recipe/deleteFromRecipeUserTable', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ recipeId, userId })
            });
            if (response.ok) {
                setRecipe(recipeName);
                setMessage('Successfully deleted');
                setLocalRecipes(prevRecipes => prevRecipes.filter(recipe => recipe.recipeId !== recipeId));
                setAlertVisibility(true);
            } else {
                console.error("Error deleting recipe");
                setMessage('Failed to delete recipe');
            }
        } catch (error) {
            console.error("Error deleting recipe:", error);
            setMessage('Failed to delete recipe');
        }
    }

    return (
        <>
            <h2 className='center-h1'>Saved recipes</h2>
            {alertVisible && <Alert message={message} onClose={() => setAlertVisibility(false)} recipe={recipe} type="success"></Alert>}
            <ul className="recipes">
                {localRecipes.length === 0 ?
                    <p>No saved recipes found.</p>
                    :
                    localRecipes.map((recipe) => (
                        <li key={recipe.recipeId}>
                            <a href="/recipeDetails" className="recipe-name" onClick={() => handleClick(recipe)}>
                                {recipe.recipeName}
                            </a>
                            <Button color='danger' onClick={() => removeRecipe(recipe.recipeId, userCredentials.id, recipe.recipeName)} type='Remove' ></Button>
                        </li>
                    ))
                }
            </ul>
        </>
    )
}
