import '../Recipes.css'
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Recipe } from '../AllRecipes';
import { Alert } from '../../components/Alert';
import Button from '../../components/Button';
import { AlertColor } from '../../Ingredients/Components/AddIngredients';

interface Props {
    recipes: Recipe[];
    alertVisible: boolean;
    alertColor: AlertColor;
    message: string;
    setAlertVisibility: React.Dispatch<React.SetStateAction<boolean>>
    recipeName: string;
    handleRecipeAdd: (recipeId: number, recipeName: string) => Promise<void>;
}

export interface ApiResponse {
    added: boolean;
    message: string;
}


export const AddRecipeComponent: React.FC<Props> = ({
    recipes,
    alertVisible,
    alertColor,
    message,
    setAlertVisibility,
    recipeName,
    handleRecipeAdd }) => {
    const navigate = useNavigate();

    const handleClick = (recipeWithIngredients: Recipe) => {
        navigate('/recipeDetails', { state: { recipeWithIngredients } });
    }


    return (
        <div style={{ marginTop: 20 }}>
            <h2 className='center-h1'>Recipes</h2>
            {alertVisible && <Alert color={alertColor} message={message} onClose={() => setAlertVisibility(false)} children={recipeName} type='button' />}
            <ul className="recipes">
                {recipes.map((recipe) => (
                    <li key={recipe.recipeId}>
                        <a href="/recipeDetails" className="recipe-name" onClick={() => handleClick(recipe)}>
                            {recipe.recipeName}
                        </a>
                        <Button color='success' children='Add' heart='&#x1F49A;' onClick={() => handleRecipeAdd(recipe.recipeId, recipe.recipeName)} type='button' />
                    </li>
                ))}
            </ul>

        </div>
    )
}
