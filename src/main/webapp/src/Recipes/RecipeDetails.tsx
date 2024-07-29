import { useLocation } from "react-router-dom"
import React, { useEffect, useState } from "react";
import Button from "../components/Button";
import { useUserContext } from "../Contexts/Context";
import { ShoppingList } from "./Components/ShoppingList";

export interface Ingredient {
    ingredientName: string;
    ingredientId: number;
    quantity: number;
    unit: string;
}

export interface Recipe {
    recipeName: string;
    recipeId: number;
    servings: number;
    ingredients: Ingredient[];
}

interface RequestLoadProps {
    portions: number;
    recipeId?: number;
}

export const RecipeDetails: React.FC = () => {
    const location = useLocation();
    const { recipeWithIngredients } = location.state || {};
    const { recipeWithoutIngredients } = location.state || {};
    const { userCredentials } = useUserContext();
    const [ingredients, setIngredients] = useState<Ingredient[]>([]);
    const [recipe, setRecipe] = useState<Recipe>({ recipeName: '', recipeId: 0, servings: 0, ingredients });
    const [requestLoad, setRequestLoad] = useState<RequestLoadProps>({ portions: 0, recipeId: 0 });
    const [newQuantities, setNewQuantities] = useState<Ingredient[]>();

    useEffect(() => {
        const initializeForm = async () => {
            if (recipeWithoutIngredients) {
                if (newQuantities) {
                    const form: Recipe = {
                        recipeName: recipeWithoutIngredients.recipeName,
                        recipeId: recipeWithoutIngredients.recipeId,
                        servings: requestLoad?.portions,
                        ingredients: newQuantities
                    }
                    setRecipe(form);
                } else {
                    fetchIngredients(recipeWithoutIngredients.recipeId);
                    const form: Recipe = {
                        recipeName: recipeWithoutIngredients.recipeName,
                        recipeId: recipeWithoutIngredients.recipeId,
                        servings: recipeWithoutIngredients.servings,
                        ingredients: ingredients
                    }
                    setRecipe(form);
                }

            }
            if (recipeWithIngredients) {
                if (newQuantities) {
                    const form: Recipe = {
                        recipeName: recipe.recipeName,
                        recipeId: recipe.recipeId,
                        servings: requestLoad?.portions,
                        ingredients: newQuantities
                    }
                    setRecipe(form);
                } else {
                    setRecipe(recipeWithIngredients);
                }
            }

        }

        initializeForm();
        // eslint-disable-next-line
    }, [ingredients, recipeWithIngredients, recipeWithoutIngredients, newQuantities]);

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


    if (!recipeWithIngredients && !recipeWithoutIngredients) {
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

    return (
        <div className="recipe-details">
            <h4>{recipe?.recipeName}</h4>
            <div className="row change-servings">
                <input
                    className="form-control"
                    id="servings"
                    min={1}
                    placeholder="Servings"
                    required
                    type="number"
                    onChange={handleServings}
                />
                <Button color='success' type='button' children='Change' onClick={changeQuantities} />
            </div>
            <h5>{recipe?.servings} Servings</h5>
            <ul className="list-ingredients">
                {recipe?.ingredients.map((ingredient: Ingredient) =>
                    <li key={ingredient.ingredientId}>
                        {ingredient.ingredientName}: {ingredient.quantity} {ingredient.unit}
                    </li>
                )}
            </ul>
            <ShoppingList
                recipe={recipe}
                userId={userCredentials.id}
            />
        </div>
    )
}