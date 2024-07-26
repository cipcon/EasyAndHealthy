import React, { useState, ChangeEvent, FormEvent } from "react";
import { ApiResponse } from "../../Recipes/Components/AddRecipeComponent";
import { Ingredient } from "../Ingredients";
import AddIngredientComponent from "./AddIngredientReturn";

interface Props {
    ingredients: Ingredient[];
    userId: number;
    ingredientChanged: number;
    setIngredientChanged: React.Dispatch<React.SetStateAction<number>>
}

interface AddProps {
    ingredientId: number;
    quantity: number;
}

export type AlertColor = 'success' | 'warning' | 'danger' | undefined;

export const AddIngredient: React.FC<Props> = ({ ingredients, userId, ingredientChanged, setIngredientChanged }) => {
    const [dataSend, setDataSend] = useState<AddProps>({ ingredientId: 0, quantity: 0 });
    const [message, setMessage] = useState<string>('');
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
                setMessage('Something went wrong, please try again later');
                setAlertColor('warning');
                throw new Error('Failed to add ingredient');
            } else {
                const data: ApiResponse = await response.json();
                setMessage(data.message);
                !data.added ? setAlertColor('warning') : setAlertColor('success');
                data.added && setIngredientChanged(prev => prev + 1);  // Update state to trigger re-render
            }

        } catch (error) {
            console.error('Error adding ingredient:', error);
            setMessage('Something went wrong, please try again later');
            setAlertColor('warning');
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
        <AddIngredientComponent
            key={ingredientChanged}
            ingredients={ingredients}
            handleAddIngredient={handleAddIngredient}
            handleIngredientId={handleIngredientId}
            handleQuantity={handleQuantity}
            alertVisible={alertVisible}
            alertColor={alertColor}
            message={message}
            setAlertVisibility={setAlertVisibility}
        />
    );
}
