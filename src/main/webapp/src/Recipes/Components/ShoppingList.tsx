import React, { useState } from 'react'
import { Ingredient, Recipe } from '../RecipeDetails';
import Button from '../../components/Button';

interface ShoppingListProps {
    portions: number;
    recipeId: number;
    userId: number;
}

interface Props {
    recipe: Recipe;
    userId: number;
}

export const ShoppingList: React.FC<Props> = ({
    recipe,
    userId,
}) => {
    const [message, setMessage] = useState<string>();
    const [missingIngredients, setMissingIngredients] = useState<Ingredient[]>([]);

    const handleClick = () => {
        const request = {
            portions: recipe.servings,
            recipeId: recipe.recipeId,
            userId: userId
        }
        shoppingList(request);
    }

    const shoppingList = async (request: ShoppingListProps) => {
        console.log(request);
        try {
            const response = await fetch('/searchRecipe/shoppingList', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(request)
            })
            if (!response.ok) {
                setMessage('Something went Wrong, please try again later');
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            console.log(response.status);
            const data: Ingredient[] = await response.json();
            if (data.length == 0) {
                setMessage('You have all necessary ingredients');
            }
            setMissingIngredients(data);
        } catch (error) {
            console.error("Error fetching ingredients:", error);
            setMessage('Something went wrong, please try again later');
        }
        console.log(missingIngredients);
    }

    return (
        <>
            <Button color='success' type='button' children='Show me the shopping list' onClick={handleClick} />
            {missingIngredients.length > 0 ?

                <div >
                    <hr />
                    <h5>Shopping List:</h5>
                    <ul className='ul-padding'>
                        {missingIngredients.map((ingredient: Ingredient) =>
                            <li className='shopping-list' key={ingredient.ingredientId}>
                                {ingredient.ingredientName}: {ingredient.quantity} {ingredient.unit}
                            </li>
                        )}
                    </ul>
                </div> :
                <p>{message}</p>}
        </>
    )
}
