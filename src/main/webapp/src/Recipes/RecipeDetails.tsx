import { useLocation, useNavigate } from "react-router-dom"
import { Ingredient, Recipe } from "./AllRecipes";
import React, { useEffect, useState } from "react";
import Button from "../components/Button";
import { useUserContext } from "../Contexts/Context";

interface RequestLoadProps {
    portions: number;
    recipeId?: number;
}

export const RecipeDetails: React.FC = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const { recipeData } = location.state || {};
    const { recipeDetails } = location.state || {};
    const { userCredentials } = useUserContext();
    const [ingredients, setIngredients] = useState<Ingredient[]>([]);
    const [recipe, setRecipe] = useState<Recipe>({ recipeName: '', recipeId: 0, servings: 0, ingredients });
    const [requestLoad, setRequestLoad] = useState<RequestLoadProps>({ portions: 0, recipeId: 0 });
    const [newQuantities, setNewQuantities] = useState<Ingredient[]>();

    useEffect(() => {
        const initializeForm = async () => {
            if (recipeDetails) {
                if (newQuantities) {
                    const form: Recipe = {
                        recipeName: recipeDetails.recipeName,
                        recipeId: recipeDetails.recipeId,
                        servings: requestLoad?.portions,
                        ingredients: newQuantities
                    }
                    setRecipe(form);
                } else {
                    fetchIngredients(recipeDetails.recipeId);
                    const form: Recipe = {
                        recipeName: recipeDetails.recipeName,
                        recipeId: recipeDetails.recipeId,
                        servings: recipeDetails.servings,
                        ingredients: ingredients
                    }
                    setRecipe(form);
                }

            }
            if (recipeData) {
                if (newQuantities) {
                    const form: Recipe = {
                        recipeName: recipe.recipeName,
                        recipeId: recipe.recipeId,
                        servings: requestLoad?.portions,
                        ingredients: newQuantities
                    }
                    setRecipe(form);
                } else {
                    setRecipe(recipeData);
                }
            }

        }

        initializeForm();
        // eslint-disable-next-line
    }, [ingredients, recipeData, recipeDetails, newQuantities]);

    const fetchIngredients = async (recipeId: number) => {
        try {
            const response = await fetch('/ingredients/recipeIngredients', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(recipeId)
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data: Ingredient[] = await response.json();
            setIngredients(data);
        } catch (error) {
            console.error("Error fetching ingredients:", error);
        }
    }


    if (!recipeData && !recipeDetails) {
        return <h1>No recipe data available</h1>;
    }

    const handleServings = (event: React.ChangeEvent<HTMLInputElement>) => {
        event.preventDefault();
        const value = parseInt(event.target.value, 10)
        setRequestLoad({ portions: value, recipeId: recipe?.recipeId });
    }

    const changeQuantities = async () => {
        console.log(requestLoad);
        try {
            const response = await fetch('/searchRecipe/cookingPlan', {
                method: 'POST',
                headers: {
                    'Content-type': 'application/json'
                },
                body: JSON.stringify(requestLoad)
            });
            console.log(response.status);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data: Ingredient[] = await response.json();
            setNewQuantities(data);
        } catch (error) {
            console.error("Error fetching ingredients:", error);
        }
    }

    const handleClick = () => {
        const request = {
            portions: recipe.servings,
            recipeId: recipe.recipeId,
            userId: userCredentials.id
        }
        navigate('/shoppingList', { state: { request } })
    }


    return (
        <div style={{ textAlign: "center" }}>
            <div>
                <h1>{recipe?.recipeName}</h1>
                <div className="row g-3" style={{ justifyContent: "center" }}>
                    <label
                        htmlFor="servings"
                        className="visually-hidden">
                        Change Number of servings
                    </label>
                    <input
                        className="form-control"
                        id="servings"
                        min={1}
                        placeholder="Servings"
                        required
                        style={{ maxWidth: 100 }}
                        type="number"
                        onChange={handleServings}
                    />
                    <Button color='success' type='button' children='Change' onClick={changeQuantities} />
                </div>
                <h5>{recipe?.servings} Servings</h5>
                <ul style={{ listStyleType: "none", padding: 0 }}>
                    {recipe?.ingredients.map((ingredient: Ingredient) =>
                        <li key={ingredient.ingredientId}>
                            {ingredient.ingredientName}: {ingredient.quantity} {ingredient.unit}
                        </li>
                    )}
                </ul>
                <a href="/shoppingList" onClick={handleClick}>
                    Shopping list for this recipe
                </a>
            </div>
        </div>
    )
}