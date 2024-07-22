import React, { useState, ChangeEvent, FormEvent } from "react";
import { Alert } from "../../components/Alert";
import Button from "../../components/Button";
import { ApiResponse } from "../../Recipes/Components/AddRecipeComponent";
import { Ingredient } from "../Ingredients";

interface Props {
    ingredients: Ingredient[];
    userId: number;
}

interface AddProps {
    ingredientId: number;
    quantity: number;
}

export type AlertColor = 'success' | 'warning' | 'danger' | undefined;

export const AddIngredient: React.FC<Props> = ({ ingredients, userId }) => {
    const [dataSend, setDataSend] = useState<AddProps>({ ingredientId: 0, quantity: 0 });
    const [apiResponse, setApiResponse] = useState<ApiResponse>({ added: false, message: '' });
    const [alertVisible, setAlertVisibility] = useState(false);
    const [alertColor, setAlertColor] = useState<AlertColor>();

    const handleAddIngredient = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        const request = {
            ingredientId: dataSend.ingredientId,
            quantity: dataSend.quantity,
            userId: userId
        }
        console.log('Payload', JSON.stringify(request));

        try {
            const response = await fetch('/manageStock/addIngredientToUser', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(request)
            });

            if (!response.ok) {
                throw new Error('Failed to add ingredient');
            }

            const data: ApiResponse = await response.json();

            setApiResponse({ added: data.added, message: data.message });
            setAlertColor('success');
        } catch (error) {
            console.error('Error adding ingredient:', error);
            setApiResponse({ added: false, message: 'Something went wrong, please try again later' });
            setAlertColor('danger');
        }

        setAlertVisibility(true);
    };

    const handleQuantity = (event: ChangeEvent<HTMLInputElement>) => {
        const value = parseInt(event.target.value, 10);
        setDataSend({ ...dataSend, quantity: value });
    };

    const handleIngredientId: React.ChangeEventHandler<HTMLSelectElement> = (event) => {
        const value = parseInt(event.target.value, 10);
        setDataSend({ ...dataSend, ingredientId: value });
    };

    return (
        <>
            <h1>Add Ingredient</h1>
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
                        {ingredients.map((ingredient) =>
                            <option
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
                        onChange={handleQuantity}
                        placeholder='quantity'
                        required
                        type='number'
                        id='quantity'
                        min='1'
                    />
                </div>
                <div className='col-auto'>
                    <Button color='success' type='submit' children='Add' />
                </div>
                <div className="col-auto">
                    {alertVisible && <Alert color={alertColor} message={apiResponse.message} onClose={() => setAlertVisibility(false)} />}
                </div>


            </form>
        </>
    )
}
