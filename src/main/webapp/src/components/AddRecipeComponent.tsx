import React from "react";
import { Recipe } from "../Recipes/AllRecipes"
import { useNavigate } from "react-router-dom";
import Button from "./Button";

interface Props {
    recipes: Recipe[];
}


export const AddRecipeComponent: React.FC<Props> = ({ recipes }) => {
    const navigate = useNavigate();

    const handleClick = (recipe: Recipe) => {
        navigate('/recipeDetails', { state: { recipe } });
    }

    const handleRecipeAdd = () => {

    }


    return (
        <>
            <ul>
                {recipes.map((recipe) => (
                    <li key={recipe.recipeId} className="link-margin-top">
                        <div>
                            <a href="/recipeDetails" className="recipes" onClick={() => handleClick(recipe)}>
                                {recipe.recipeName}
                            </a>
                            <Button color="success" onClick={() => handleRecipeAdd} type="Add" />

                        </div>
                    </li>
                ))}
            </ul>

        </>
    )
}
