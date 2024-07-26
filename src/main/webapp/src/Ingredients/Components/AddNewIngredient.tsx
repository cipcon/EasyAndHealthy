import React, { useEffect, useState } from 'react';
import { AlertColor } from './AddIngredients';
import { AddNewIngredientChild } from './AddNewIngredientChild';
import { IngredientChanged } from '../Ingredients';

export interface ApiResponseProps {
    message: string;
    added: boolean;
}

const AddNewIngredient: React.FC<IngredientChanged> = ({ ingredientChanged, setIngredientChanged }) => {
    const [unitList, setUnitList] = useState<string[]>([]);
    const [unit, setUnit] = useState<string>('');
    const [alertColor, setAlertColor] = useState<AlertColor>();
    const [alertVisible, setAlertVisibility] = useState<boolean>(false);
    const [ingredientName, setIngredientName] = useState<string>('');
    const [apiResponse, setApiResponse] = useState<ApiResponseProps>();

    useEffect(() => {
        getUnits();
    }, []); // Re-fetch units if ingredient is added

    // save locally the unit the user saved
    const handleUnit = (event: React.ChangeEvent<HTMLSelectElement>) => {
        setUnit(event.target.value);
    };

    // save locally the name of the ingredient the user typed in
    const handleIngredient = (event: React.ChangeEvent<HTMLInputElement>) => {
        setIngredientName(event.target.value);
    };

    // take all the inputs and send the request to API
    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const request = {
            ingredientName: ingredientName,
            unit: unit
        };
        try {
            const response = await fetch('/ingredients/createIngredient', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(request)
            });

            if (!response.ok) {
                throw new Error('Failed to add ingredient');
            }
            const data: ApiResponseProps = await response.json();
            setApiResponse(data);
            setAlertVisibility(true);
            setAlertColor(data.added ? 'success' : 'danger');
            if (data.added) {
                setIngredientChanged(prev => prev + 1); // Update ingredientAdded to trigger re-render
            }
        } catch (error) {
            console.error('Error adding ingredient: ', error);
            setApiResponse({ message: 'Error adding ingredient', added: false });
            setAlertColor('danger');
        }
    };

    // get all units from the API
    const getUnits = async () => {
        try {
            const response = await fetch('/ingredients/getUnits');
            if (!response.ok) {
                throw new Error('HTTP error! status: ' + response.status);
            }

            const data: string[] = await response.json();
            setUnitList(data);
        } catch (error) {
            console.error('Error fetching units: ', error);
        }
    };

    return (
        <AddNewIngredientChild
            key={ingredientChanged}
            handleSubmit={handleSubmit}
            handleIngredient={handleIngredient}
            handleUnit={handleUnit}
            unitList={unitList}
            alertVisible={alertVisible}
            alertColor={alertColor}
            apiResponse={apiResponse}
            setAlertVisibility={setAlertVisibility}
        />
    );
};

export default AddNewIngredient;
