import React, { useState } from 'react'
import { Ingredient, Recipe } from '../RecipeDetails';
import Button from '../../components/Button';
import { Alert } from '../../components/Alert';

interface ShoppingListProps {
    portions: number;
    recipeId: number;
    userId: number;
}

interface CookedResponseProps {
    prepared: boolean;
    message: string;
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
    const [cookedReponse, setCookedResponse] = useState<CookedResponseProps>({ prepared: false, message: '' });
    const [missingIngredients, setMissingIngredients] = useState<Ingredient[]>([]);
    const [showList, setShowList] = useState<boolean>(false);
    const [alertVisible, setAlertVisibility] = useState<boolean>(false);


    const handleClick = () => {
        const request = {
            portions: recipe.servings,
            recipeId: recipe.recipeId,
            userId: userId
        }
        shoppingList(request);
        setShowList(true);
    }

    const handleCookButton = async () => {
        const request = {
            recipeId: recipe.recipeId,
            userId: userId,
            portions: recipe.servings
        }
        console.log(request)
        try {
            const response = await fetch('/searchRecipe/prepareRecipe', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(request)
            })
            if (!response.ok) {
                const resp = {
                    prepared: false,
                    message: 'Something went wrong, please try again later'
                }
                setCookedResponse(resp);
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data: CookedResponseProps = await response.json();
            setCookedResponse(data);
            console.log(data);
        } catch (error) {
            console.error("Error fetching ingredients:", error);
            setMessage('Something went wrong, please try again later');
        }
        setAlertVisibility(true);
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
                setMessage('You have all the necessary ingredients');
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
            {showList &&
                <div>
                    {

                        missingIngredients.length > 0 ?
                            <div >

                                <hr />
                                <Button color='warning' type='button' children='Close the shopping list' onClick={() => setShowList(false)} />
                                <h5 style={{ marginTop: 10 }}>Shopping List:</h5>
                                <ul className='ul-padding'>
                                    {missingIngredients.map((ingredient: Ingredient) =>
                                        <li className='shopping-list' key={ingredient.ingredientId}>
                                            {ingredient.ingredientName}: {ingredient.quantity} {ingredient.unit}
                                        </li>
                                    )}
                                </ul>

                            </div> :
                            <h5 style={{ marginTop: 10 }}>{message}</h5>
                    }
                    <Button color='success' type='button' children='Cook recipe' onClick={handleCookButton} />
                    {alertVisible && <Alert color='warning' children={cookedReponse.message} type='button' onClose={() => setAlertVisibility(false)} />}
                </div>
            }
        </>
    )
}
