import { useNavigate } from "react-router-dom";
import { Recipe } from "./AllRecipes";
import { useUserContext } from "../Contexts/context";
import { useEffect, useState } from "react";

interface UserRecipesProps {
    recipes: Recipe[];
    onRecipeRemoved: () => void;
}

export const UserRecipesComponent: React.FC<UserRecipesProps> = ({ recipes, onRecipeRemoved }) => {
    const navigate = useNavigate();
    const { userCredentials } = useUserContext();
    const [message, setMessage] = useState('');
    const [localRecipes, setLocalRecipes] = useState<Recipe[]>(recipes);

    useEffect(() => {
        setLocalRecipes(recipes);
    }, [recipes]);


    const handleClick = (recipe: Recipe) => {
        navigate('/recipeDetails', { state: { recipe } });
    }


    const removeRecipe = async (recipeId: number, userId: number) => {
        try {
            const response = await fetch('recipe/deleteFromRecipeUserTable', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ recipeId, userId })
            });
            if (response.ok) {
                setMessage('Successfully deleted');
                setLocalRecipes(prevRecipes => prevRecipes.filter(recipe => recipe.recipeId !== recipeId));
                onRecipeRemoved();
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
        <div>
            <ul>
                {localRecipes.length === 0 ?
                    <p>No saved recipes found.</p>
                    :
                    localRecipes.map((recipe) => (
                        <li key={recipe.recipeId}>
                            <a href="/recipeDetails" className="recipes" onClick={() => handleClick(recipe)}>
                                {recipe.recipeName}
                            </a>
                            <button className="removeButton" type="button" onClick={() => removeRecipe(recipe.recipeId, userCredentials.id)}>Remove</button>
                        </li>
                    ))
                }
                <p style={{ color: "green" }}>{message}</p>
            </ul>
        </div>
    )
}
