import '../Recipes.css'
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { Recipe } from "../AllRecipes";
import { useUserContext } from "../../Contexts/Context";
import { Alert } from "../../components/Alert";
import Button from "../../components/Button";
import { AlertColor } from '../../Ingredients/AddIngredients';


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
    const [alertColor, setAlertColor] = useState<AlertColor>();

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
                setAlertColor('success');
            } else {
                console.error("Error deleting recipe");
                setMessage('Failed to delete recipe');
                setAlertColor('warning')
            }
        } catch (error) {
            console.error("Error deleting recipe:", error);
            setMessage('Failed to delete recipe');
            setAlertColor('warning')
        }
    }

    return (
        <>
            <h2 className='center-h1'>Favorite recipes</h2>
            {alertVisible && <Alert color={alertColor} message={message} onClose={() => setAlertVisibility(false)} children={recipe} type='button'></Alert>}
            <ul className="recipes" style={{ gap: 50 }}>
                {localRecipes.length === 0 ?
                    <p>No saved recipes found.</p>
                    :
                    localRecipes.map((recipe) => (
                        <li key={recipe.recipeId} className='row g-3'>
                            <a href="/recipeDetails" className="recipe-name col-auto" onClick={() => handleClick(recipe)}>
                                {recipe.recipeName}
                            </a>
                            <Button color='danger' children='Remove' onClick={() => removeRecipe(recipe.recipeId, userCredentials.id, recipe.recipeName)} type='button' ></Button>
                        </li>
                    ))
                }
            </ul>
        </>
    )
}
