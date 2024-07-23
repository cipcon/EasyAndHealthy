import React, { ChangeEvent, useEffect, useState } from 'react'
import { useLocation } from 'react-router-dom';
import { Ingredient } from '../AllRecipes';
import Button from '../../components/Button';
import { AlertColor } from '../../Ingredients/Components/AddIngredients';
import { Alert } from '../../components/Alert';

interface RequestProps {
    newRecipeName: string;
    recipeId: number;
    servings: number;
}

interface IngredientNameIdProps {
    ingredientId: number;
    ingredientName: string;
}

interface UpdateIngredientProps {
    ingredientId: number;
    quantity: number;
    recipeId: number;
}

export const EditRecipe: React.FC = () => {
    const location = useLocation();
    const { recipe } = location.state || {};
    const [ingredients, setIngredients] = useState<Ingredient[]>([]);
    const [ingredientNameId, setIngredientNameId] = useState<IngredientNameIdProps>({ ingredientId: 0, ingredientName: '' });
    const [updateIngredientProps, setUpdateIngredientProps] = useState<UpdateIngredientProps>();
    const [requestProps, setRequestProps] = useState<RequestProps>(
        { newRecipeName: '', recipeId: recipe.recipeId, servings: 0 }
    )
    const [message, setMessage] = useState<string>();
    const [alertVisible, setAlertVisibility] = useState(false);
    const [updateVisible, setUpdateVisibility] = useState<boolean>(false);
    const [alertColor, setAlertColor] = useState<AlertColor>();

    const fetchIngredients = async () => {
        try {
            const response = await fetch('/ingredients/recipeIngredients', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(recipe.recipeId)
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

    const handleRecipe = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        console.log(requestProps);
        try {
            const response = await fetch('/recipe/updateGlobalRecipe', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestProps)
            });
            if (!response.ok) {
                setMessage('Something went wrong, please try again later');
                setAlertColor('danger')
                throw new Error('Failed to change recipe name and number of servings');
            }
            const data: boolean = await response.json();
            console.log(data);
            setMessage('Recipe name and/or number of servings succesfully changed');
            setAlertColor('success')
        } catch (error) {
            console.error('Error changing recipe name and number of servings:', error);
            setMessage('Something went wrong, please try again later');
            setAlertColor('danger')
        }
        setAlertVisibility(true);
    };

    const handleRecipeName = (event: React.ChangeEvent<HTMLInputElement>) => {
        event.preventDefault();
        setRequestProps({ ...requestProps, newRecipeName: event.target.value })
        console.log(event.target.value);
    }

    const handleServings = (event: React.ChangeEvent<HTMLInputElement>) => {
        event.preventDefault();
        const value = parseInt(event.target.value, 10);
        setRequestProps({ ...requestProps, servings: value });
        console.log(event.target.value);
    }

    const updateIngredient = async (e: React.FormEvent) => {
        e.preventDefault();
        console.log(updateIngredientProps);
        try {
            const response = await fetch('/recipe/updateIngredientQuantity', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(updateIngredientProps)
            });
            if (!response.ok) {
                setMessage('Failed to update quantity of the ingredient, please try again later');
                setAlertColor('danger')
                throw new Error('Failed to update quantity of the ingredient');
            }
            const data: boolean = await response.json();
            console.log(data);
            setMessage('Quantity of the ingredient successfully updated');
            setAlertColor('success')
        } catch (error) {
            console.error('Failed to update quantity of the ingredient: ', error);
            setMessage('Failed to update quantity of the ingredient, please try again later');
            setAlertColor('danger')
        }
        setAlertVisibility(true);
    }

    useEffect(() => {
        fetchIngredients();
    }, [updateIngredient])

    const handleQuantity = (event: ChangeEvent<HTMLInputElement>) => {
        const value = parseInt(event.target.value, 10);

        const request: UpdateIngredientProps = {
            recipeId: recipe.recipeId,
            ingredientId: ingredientNameId.ingredientId,
            quantity: value
        }
        setUpdateIngredientProps(request);
    }

    const removeIngredient = async (ingredientId: number) => {

    }

    const showUpdateIngredient = async (show: boolean, ingredientName: string, ingredientId: number) => {
        setUpdateVisibility(show);
        setIngredientNameId({ ...ingredientNameId, ingredientName: ingredientName, ingredientId: ingredientId });
    }

    return (
        <>
            <form className='row g-3 form-width' onSubmit={handleRecipe}>
                <h5>Change Recipe name or the number of servings</h5>

                <div className='col-auto'>
                    <label
                        htmlFor="ingredient"
                    >
                        RecipeName
                    </label>
                    <input
                        className='form-control'
                        onChange={handleRecipeName}
                        placeholder={recipe.recipeName}
                        type='text'
                        id='ingredient'
                    />
                </div>
                <div className='col-auto'>
                    <label
                        htmlFor="ingredient"
                    >
                        Servings
                    </label>
                    <input
                        className='form-control'
                        onChange={handleServings}
                        placeholder={recipe.servings}
                        type='text'
                        id='ingredient'
                    />
                </div>
                <div className='col-auto' style={{ alignContent: 'end' }}>
                    <Button color='success' type='submit' children='Change' />
                </div>
                <div className="col-auto">
                    {alertVisible && <Alert color={alertColor} message={message} onClose={() => setAlertVisibility(false)} />}
                </div>
            </form>
            <hr />

            {updateVisible &&
                <form className='li-ingredients' onSubmit={updateIngredient}>
                    <div>
                        <input className="form-control input-width" type="text" placeholder={ingredientNameId.ingredientName} disabled />
                    </div>
                    <div className='col-auto'>
                        <label htmlFor="quantity" className='visually-hidden'>Quantity</label>
                        <input className='form-control' id="quantity" min='1' name="quantity" onChange={handleQuantity} placeholder='quantity' required type="number" />
                    </div>
                    <Button color='warning' type='submit' children='Update' />
                    <Button color='warning' type='submit' children='Back' onClick={() => setUpdateVisibility(false)} />
                </form>
            }            <hr />
            <h5>Change ingredients:</h5>
            <div>
                {ingredients?.map((ingredient) =>
                    <div key={ingredient.ingredientId}>
                        <span className='col-auto'>{ingredient.ingredientName} {ingredient.quantity} {ingredient.unit}</span>
                        {!updateVisible && <Button color='warning' onClick={() => showUpdateIngredient(true, ingredient.ingredientName, ingredient.ingredientId)} type='button' children='Update' />}
                        <Button color='danger' onClick={() => removeIngredient(ingredient.ingredientId)} type='button' children='Delete' />

                    </div>
                )}
            </div>

        </>
    )
}
