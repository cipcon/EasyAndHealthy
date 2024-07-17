import React, { useEffect, useState } from 'react'
import Button from '../../components/Button';
import { Alert } from '../../components/Alert';
import { AlertColor } from './AddIngredients';

interface ApiResponseProps {
    message: string;
    added: boolean;
}

interface AddedProps {
    onAdded: () => void;
}

const AddNewIngredient: React.FC<AddedProps> = ({ onAdded }) => {
    const [unitList, setUnitList] = useState<string[]>([]);
    const [unit, setUnit] = useState<string>('');
    const [alertColor, setAlertColor] = useState<AlertColor>();
    const [alertVisible, setAlertVisibility] = useState<boolean>(false);
    const [ingredientName, setIngredientName] = useState<string>('');
    const [apiResponse, setApiResponse] = useState<ApiResponseProps>();

    useEffect(() => {
        getUnits();
    }, [])

    // save locally the unit the user saved
    const handleUnit = (event: React.ChangeEvent<HTMLSelectElement>) => {
        setUnit(event.target.value);
        console.log(unit);
    }

    // save locally the name of the ingredient the user typed in
    const handleIngredient = (event: React.ChangeEvent<HTMLInputElement>) => {
        setIngredientName(event.target.value);
        console.log(ingredientName);
    }

    // take all the inputs and send the request to API
    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const request = {
            ingredientName: ingredientName,
            unit: unit
        }
        console.log(request);
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
            setAlertColor(data.added ? 'success' : 'danger')
            if (data.added) {
                onAdded();
            }
        } catch (error) {
            console.error('Error adding ingredient: ', error);
            let e = error;
            setApiResponse({ message: 'Error adding ingredient', added: false })
            setAlertColor('danger');
        }
    }

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
            console.error('Error fetching units: ', error)
        }
    }

    return (
        <div>
            <hr />
            <h5>Cannot find ingredient you are looking for? Then add it:</h5>
            <form className='row g-3 form-width' onSubmit={handleSubmit}>

                <div className='col-auto'>
                    <label
                        htmlFor="ingredient"
                        className='visually-hidden'
                    >
                        Ingredient
                    </label>
                    <input
                        className='form-control'
                        onChange={handleIngredient}
                        placeholder='ingredient'
                        required
                        type='text'
                        id='ingredient'
                    />
                </div>

                <div className='col-auto'>
                    <label htmlFor="unit" className='visually-hidden'>Unit</label>
                    <select
                        className='form-select'
                        id="unit"
                        onChange={handleUnit}
                        required
                    >
                        <option value="">Select an unit</option>
                        {unitList.map((u) =>
                            <option
                                key={u}
                                value={u}
                            >
                                {u}
                            </option>
                        )}
                    </select>
                </div>

                <div className='col-auto'>
                    <Button color='success' type='submit' children='Add' />
                </div>

                <div className="col-auto">
                    {alertVisible && <Alert color={alertColor} message={apiResponse?.message} onClose={() => setAlertVisibility(false)} />}
                </div>


            </form>
        </div>
    )
}

export default AddNewIngredient