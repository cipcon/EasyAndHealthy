import { useNavigate } from "react-router-dom";
import { RecipeRequest } from "./UserRecipes";

interface UserRecipesProps {
    recipes: RecipeRequest[];
}

export const UserRecipesComponent: React.FC<UserRecipesProps> = ({ recipes }) => {
    const navigate = useNavigate();

    const handleClick = async (event: any) => {
        navigate('/recipe');
    }
    return (
        <div>
            <ul>
                {recipes.length === 0 ?
                    <p>No saved recipes found.</p>
                    :
                    recipes.map((recipe) => (
                        <li key={recipe.recipeId}>
                            <a href="" className="recipes" onClick={handleClick}>{recipe.recipeName}</a>
                        </li>
                    ))
                }
            </ul>
        </div>
    )
}