import { useNavigate } from "react-router-dom";
import { Recipe } from "./AllRecipes";
import { useUserContext } from "../Contexts/context";
import { useEffect, useState } from "react";

interface UserRecipesProps {
    recipes: Recipe[];
}

interface DeleteUserProps {
    userId: number;
    recipeId: number;
}

export const UserRecipesComponent: React.FC<UserRecipesProps> = ({ recipes }) => {
    const navigate = useNavigate();
    const { userCredentials } = useUserContext();
    const [remove, setRemove] = useState<DeleteUserProps | null>(null);
    const [successDelete, setSuccessDelete] = useState('');



    const handleClick = (recipe: Recipe) => {
        navigate('/recipeDetails', { state: { recipe } });
    }


    const removeRecipe = async (recipeId: number, userId: number) => {
        setRemove({ recipeId, userId });
        try {
            const response = await fetch('recipe/deleteFromRecipeUserTable', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(remove)
            });
            if (response.ok) {
                setSuccessDelete('Successfully deleted');
            } else {
                console.error("Error deleting recipe");
            }
        } catch (error) {
            console.error("Error deleting recipe:", error);
        }
    }

    return (
        <div>
            <ul>
                {recipes.length === 0 ?
                    <p>No saved recipes found.</p>
                    :
                    recipes.map((recipe) => (
                        <li key={recipe.recipeId}>
                            <a href="/recipeDetails" className="recipes" onClick={() => handleClick(recipe)}>
                                {recipe.recipeName}
                            </a>
                            <button className="removeButton" type="button" onClick={() => removeRecipe(recipe.recipeId, userCredentials.id)}>Remove</button>
                        </li>
                    ))
                }
                <p>{successDelete}</p>
            </ul>
        </div>
    )
}
