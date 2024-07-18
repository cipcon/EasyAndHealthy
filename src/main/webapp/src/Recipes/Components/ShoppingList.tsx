import React, { useEffect, useState } from 'react'
import { useLocation } from 'react-router-dom';
import { Ingredient } from '../AllRecipes';

export const ShoppingList: React.FC = () => {
    const location = useLocation();
    const { request } = location.state || {};
    const [message, setMessage] = useState<string>();
    const [missingIngredients, setMissingIngredients] = useState<Ingredient[]>([]);

    useEffect(() => {
        fetchIngredients();
        // eslint-disable-next-line
    }, [])

    const fetchIngredients = async () => {
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
            setMessage('Something went Wrong, please try again later');
        }
        console.log(missingIngredients);
    }

    return (
        <>
            {missingIngredients.length > 0 ?
                <div>
                    <h5>Shopping List</h5>
                    <ul>
                        {missingIngredients.map((ingredient: Ingredient) =>
                            <li key={ingredient.ingredientId}>
                                {ingredient.ingredientName}: {ingredient.quantity} {ingredient.unit}
                            </li>
                        )}
                    </ul>
                </div> :
                <p>{message}</p>}
        </>
    )
}
