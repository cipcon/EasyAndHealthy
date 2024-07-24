import React, { ChangeEvent, FormEvent, useEffect, useState } from 'react'
import { useLocation } from 'react-router-dom';
import Button from '../../components/Button';
import { AlertColor } from '../../Ingredients/Components/AddIngredients';
import { Alert } from '../../components/Alert';
import { ApiResponse } from './AddRecipeComponent';
import { fetchIngredients } from '../../Ingredients/Ingredients';
import { Recipe } from '../../Homepage/NoIdeaMode';

interface RecipeNameServingsProps {
    newRecipeName: string;
    recipeId: number;
    servings: number;
}

interface IngredientQuantityRecipeProps {
    ingredientId: number;
    quantity: number;
    recipeId: number;
}

interface IngredientProps {
    ingredientName: string;
    ingredientId: number;
    quantity: string;
    unit: string;
}

interface Ingredient {
    ingredientName: string;
    ingredientId: number;
    unit: string;
}

export const EditRecipe: React.FC = () => {
    const location = useLocation();
    const { recipe } = location.state || {};
    const [updatedRecipe, setUpdatedRecipe] = useState<Recipe>({ recipeName: recipe.recipeName, recipeId: recipe.recipeId, servings: recipe.servings });
    const [allIngredients, setAllIngredients] = useState<Ingredient[]>([]);

    const [ingredients, setIngredients] = useState<IngredientProps[]>([]);
    const [updateProps, setUpdateProps] = useState<IngredientQuantityRecipeProps>(
        { ingredientId: 0, quantity: 0, recipeId: recipe.recipeId }
    );
    const [addProps, setAddProps] = useState<IngredientQuantityRecipeProps>(
        { ingredientId: 0, quantity: 0, recipeId: recipe.recipeId }
    );
    const [recipeNameServings, setRecipeNameServings] = useState<RecipeNameServingsProps>(
        { newRecipeName: '', recipeId: recipe.recipeId, servings: 0 }
    )
    const [message, setMessage] = useState<string>();
    const [alertVisible, setAlertVisibility] = useState(false);
    const [alertColor, setAlertColor] = useState<AlertColor>();

    useEffect(() => {
        const fetchAndSetIngredients = async () => {
            const fetchedIngredients = await fetchIngredients();
            setAllIngredients(fetchedIngredients);
        };

        fetchAndSetIngredients();
    }, []);

    const handleAddIngredient = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        console.log('Payload', JSON.stringify(addProps));

        try {
            const response = await fetch('/recipe/addIngredientToRecipe', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(addProps)
            });

            if (!response.ok) {
                throw new Error('Failed to add ingredient');
            }

            const data: ApiResponse = await response.json();
            if (data) {
                setMessage('Ingredient added successfully');
                setAlertColor('success')
            } else {
                setMessage('Ingredient already exist, please edit it or insert another one');
                setAlertColor('warning');
            }
        } catch (error) {
            console.error('Error adding ingredient:', error);
            setMessage('Something went wrong, please try again later or contact support');
            setAlertColor('danger');
        }
        setAlertVisibility(true);
    };

    const handleIngredientId: React.ChangeEventHandler<HTMLSelectElement> = (event) => {
        const value = parseInt(event.target.value, 10);
        setAddProps({ ...addProps, ingredientId: value });
    };

    const handleQuantitytoAdd = (event: ChangeEvent<HTMLInputElement>) => {
        const value = parseInt(event.target.value, 10);
        setAddProps({ ...addProps, quantity: value });
    };

    // Fetch the ingredients of the recipe
    const fetchRecipeIngredients = async () => {
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
            const data: IngredientProps[] = await response.json();
            setIngredients(data);
        } catch (error) {
            console.error("Error fetching ingredients:", error);
        }
    }

    // Edit the name or portions of the recipe
    const handleRecipe = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        console.log(recipeNameServings);
        try {
            const response = await fetch('/recipe/updateGlobalRecipe', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(recipeNameServings)
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

    // Locally save the new recipe name
    const handleRecipeName = (event: React.ChangeEvent<HTMLInputElement>) => {
        event.preventDefault();
        setRecipeNameServings({ ...recipeNameServings, newRecipeName: event.target.value })
        console.log(event.target.value);
    }

    // Locally save the number of servings
    const handleServings = (event: React.ChangeEvent<HTMLInputElement>) => {
        event.preventDefault();
        const value = parseInt(event.target.value, 10);
        setRecipeNameServings({ ...recipeNameServings, servings: value });
        console.log(event.target.value);
    }

    // Locally save the new quantity of the ingredient
    const handleQuantity = (event: ChangeEvent<HTMLInputElement>, ingredientId: number) => {
        const value = parseInt(event.target.value, 10);

        const request: IngredientQuantityRecipeProps = {
            recipeId: recipe.recipeId,
            ingredientId: ingredientId,
            quantity: value
        }
        setUpdateProps(request);
    }

    // Update quantity of ingredients
    const updateIngredient = async () => {
        console.log(updateProps);
        try {
            const response = await fetch('/recipe/updateIngredientQuantity', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(updateProps)
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
        fetchRecipeIngredients();
        const { recipe: updatedRecipe } = location.state || {};
        setUpdateProps(updatedRecipe);
    }, [updateIngredient, updatedRecipe])


    // remove an ingredient
    const removeIngredient = async (ingredientId: number) => {
        const removeLoad = {
            recipeId: recipe.recipeId,
            ingredientId: ingredientId
        }
        try {
            const response = await fetch('/recipe/deleteIngredientFromRecipe', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(removeLoad)
            });
            if (!response.ok) {
                setMessage('Failed to delete the ingredient, please try again later');
                setAlertColor('danger')
                throw new Error('Failed to delete the ingredient');
            }
            const data: boolean = await response.json();
            console.log(data);
            setMessage('Ingredient successfully deleted');
            setAlertColor('success')
        } catch (error) {
            console.error('Failed to delete the ingredient: ', error);
            setMessage('Failed to delete the ingredient, please try again later');
            setAlertColor('danger')
        }
        setAlertVisibility(true);
    }



    return (
        <>
            <div style={{ maxWidth: 500 }}>
                <h5>Change Recipe name or the number of servings</h5>
                <div className="col-auto">
                    {alertVisible && <Alert color={alertColor} message={message} onClose={() => setAlertVisibility(false)} />}
                </div>
            </div>
            <form className='row g-3 form-width' onSubmit={handleRecipe}>

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
            </form>

            <hr />
            <h5>Add Ingredient</h5>
            <form className='row g-3 form-width' onSubmit={handleAddIngredient}>
                <div className='col-auto'>
                    <label htmlFor="ingredient" className='visually-hidden'>Ingredient</label>
                    {/*  the handleIngredientId function determines the ingredientId based on the value
        attribute of the <option> elements within the <select> tag.*/}
                    <select
                        className='form-select'
                        id="ingredient"
                        onChange={handleIngredientId}
                        required
                    >
                        <option value="">Select an ingredient</option>
                        {allIngredients.map((ingredient) => <option
                            key={ingredient.ingredientId}
                            value={ingredient.ingredientId}
                        >
                            {ingredient.ingredientName} ({ingredient.unit})
                        </option>
                        )}
                    </select>
                </div>
                <div className='col-auto'>
                    <label
                        htmlFor="quantity"
                        className='visually-hidden'
                    >
                        Quantity
                    </label>
                    <input
                        className='form-control'
                        onChange={handleQuantitytoAdd}
                        placeholder='quantity'
                        required
                        type='number'
                        id='quantity'
                        min='1' />
                </div>
                <div className='col-auto'>
                    <Button color='success' type='submit' children='Add' />
                </div>

            </form>

            <hr />
            <h5>Change ingredients:</h5>
            <div>
                {ingredients?.map((ingredient) =>
                    <div className='li-ingredients' key={ingredient.ingredientId}>
                        <div className='col-auto'>
                            <label htmlFor="ingredient" className='visually-hidden'>Ingredient</label>
                            <input className="form-control input-width" id='ingredient' type="text" placeholder={ingredient.ingredientName + " " + "(" + ingredient.unit + ")"} disabled />
                        </div>
                        <div className='col-auto'>
                            <label htmlFor="quantity" className='visually-hidden'>Quantity</label>
                            <input className='form-control' id="quantity" min='1' name="quantity" onChange={(event) => handleQuantity(event, ingredient.ingredientId)} placeholder={ingredient.quantity} required type="number" />
                        </div>
                        <Button color='warning' type='submit' children='Update' onClick={updateIngredient} />
                        <Button color='danger' type='submit' children='Delete' onClick={() => removeIngredient(ingredient.ingredientId)} />
                    </div>
                )}
            </div>
            <hr />
        </>
    )
}
