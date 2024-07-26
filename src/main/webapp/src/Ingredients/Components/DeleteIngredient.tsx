import React, { FormEvent, useState } from 'react'
import { AlertColor } from './AddIngredients';
import { Ingredient } from '../Ingredients';
import { DeleteIngredientChild } from './DeleteIngredientChild';

interface Props {
    ingredients: Ingredient[];
    ingredientChanged: number;
    setIngredientChanged: React.Dispatch<React.SetStateAction<number>>
}

export const DeleteIngredient: React.FC<Props> = ({ ingredients, ingredientChanged, setIngredientChanged }) => {
    const [message, setMessage] = useState<string>('');
    const [alertVisible, setAlertVisibility] = useState(false);
    const [alertColor, setAlertColor] = useState<AlertColor>();
    const [ingredientId, setIngredientId] = useState<number>();


    const handleIngredientId: React.ChangeEventHandler<HTMLSelectElement> = (event) => {
        const value = parseInt(event.target.value, 10);
        setIngredientId(value);
    }

    const handleDeleteIngredient = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const isConfirmed = window.confirm(`Are you sure you want to delete the ingredient? This action cannot be undone.`)

        if (!isConfirmed) return;
        try {
            const response = await fetch('/ingredients/deleteIngredient', {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(ingredientId)
            });
            const data: boolean = await response.json();
            if (!data) {
                setAlertColor('warning');
                throw new Error('Failed to add ingredient');
            }

            data && setIngredientChanged(prev => prev + 1);
            setMessage('Ingredient deleted');
            setAlertColor('success');
        } catch (error) {
            console.error('Error adding ingredient:', error);
            setMessage('Unable to delete the ingredient. It is part of recipes.');
            setAlertColor('warning');
        }
        setAlertVisibility(true);
    };

    return (
        <DeleteIngredientChild
            key={ingredientChanged}
            handleDeleteIngredient={handleDeleteIngredient}
            handleIngredientId={handleIngredientId}
            ingredients={ingredients}
            alertVisible={alertVisible}
            alertColor={alertColor}
            message={message}
            setAlertVisibility={setAlertVisibility}
        />
    )
}
