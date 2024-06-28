import { RecipeRequest } from "./UserRecipes";

interface UserRecipesProps {
    recipes: RecipeRequest[];
}

export const UserRecipesComponent: React.FC<UserRecipesProps> = ({ recipes }) => {

    return (
        <div>
            <ul>
                {recipes.length === 0 ?
                    <p>No saved recipes found.</p>
                    :
                    recipes.map((recipe) => (
                        <li key={recipe.recipeId}>
                            <p>{recipe.recipeName}</p>
                        </li>


                    ))
                }

            </ul>
        </div>
    )
}