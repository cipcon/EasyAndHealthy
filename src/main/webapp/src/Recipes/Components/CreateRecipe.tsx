import React, { ChangeEvent, useEffect, useState } from 'react'
import { fetchIngredients, Ingredient } from '../../Ingredients/Ingredients';
import { useUserContext } from '../../Contexts/Context';
import Button from '../../components/Button';

interface IngredientToSubmit {
    ingredientId: number;
    ingredientName: string;
    quantity: number;
    unit: string;
}

interface SingleIngredient {
    ingredientId: number;
    quantity: number;
}

interface IngredientsList {
    ingredient: SingleIngredient;
}

interface RecipeProps {
    recipeName: string;
    servings: number;
}

export const CreateRecipe: React.FC = () => {
    const [allIngredients, setAllIngredients] = useState<Ingredient[]>([]);
    const [ingredientDetails, setIngredientDetails] = useState<Ingredient[]>([]);
    const [ingredientsToSubmit, setIngredientsToSubmit] = useState<IngredientToSubmit[]>([]);
    const { userCredentials } = useUserContext();
    const [updateVisible, setUpdateVisibility] = useState<boolean>(false);
    const [recipeNameServings, setRecipeNameServings] = useState<RecipeProps>({ recipeName: '', servings: 0 });
    const [ingredientsList, setIngredientsList] = useState<IngredientsList[]>([]);
    const [singleIngredient, setSingleIngredient] = useState<SingleIngredient>({ ingredientId: 0, quantity: 0 });



    useEffect(() => {
        const fetchAndSetIngredients = async () => {
            const fetchedIngredients = await fetchIngredients();
            setAllIngredients(fetchedIngredients);
        };

        fetchAndSetIngredients();
    }, []);

    const addNewRecipe = () => {
        const request = {
        }
    }

    const handleQuantity = (event: ChangeEvent<HTMLInputElement>) => {
        event.preventDefault();
        const value = parseInt(event.target.value, 10);
        setSingleIngredient({ ...singleIngredient, quantity: value });
        console.log(event);
    }

    const handleIngredient = (event: Ingredient) => {
        setIngredientDetails([...ingredientDetails, event]);
        const ingredientId = event.ingredientId;
        setSingleIngredient({ ...singleIngredient, ingredientId: ingredientId });
        console.log(event);
    };

    const addToIngredientsList = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        setIngredientsList([...ingredientsList, { ingredient: singleIngredient }]);
        console.log(event);
    }

    const handleRecipeName = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRecipeNameServings({ ...recipeNameServings, recipeName: event.target.value });
    }

    const handleServings = (event: React.ChangeEvent<HTMLInputElement>) => {
        const value = parseInt(event.target.value, 10);
        setRecipeNameServings({ ...recipeNameServings, servings: value });
    }

    return (
        <>
            <form className='row g-3 form-width'>
                <h5>Create new Recipe</h5>
                <div className='col-auto'>
                    <label htmlFor="recipeName" className='visually-hidden'>Recipe Name</label>
                    <input
                        className='form-control'
                        id='recipeName'
                        onChange={handleRecipeName}
                        placeholder='Recipe Name'
                        required
                        type="text"
                    />
                </div>

                <div className='col-auto'>
                    <label htmlFor="servings" className='visually-hidden'>Recipe Name</label>
                    <input
                        className='form-control'
                        id='servings'
                        onChange={handleServings}
                        placeholder='Servings'
                        required
                        type="number"
                        min='1'
                    />
                </div>


                <div className='col-auto'>
                    <label htmlFor="ingredient" className='visually-hidden'>Ingredient</label>
                    <select
                        className='form-select'
                        id="ingredient"
                        required
                    >
                        <option value="">Select an ingredient</option>
                        {allIngredients.map((ingredient) =>
                            <option
                                key={ingredient.ingredientId}
                                value={ingredient.ingredientId}
                                onChange={() => handleIngredient(ingredient)}
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
                        onChange={handleQuantity}
                        placeholder='quantity'
                        required
                        type='number'
                        id='quantity'
                        min='1'
                    />
                </div>
                <div className='col-auto'>
                    <Button color='success' type='submit' children='Add Recipe' onClick={() => addToIngredientsList} />
                </div>
            </form>
        </>

    )
}
