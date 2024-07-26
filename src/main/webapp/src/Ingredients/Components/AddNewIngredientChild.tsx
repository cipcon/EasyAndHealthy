import React from 'react'
import { Alert } from '../../components/Alert';
import Button from '../../components/Button';
import { AlertColor } from './AddIngredients';
import { ApiResponseProps } from './AddNewIngredient';

interface Props {
    handleSubmit: (event: React.FormEvent<HTMLFormElement>) => Promise<void>;
    handleIngredient: (event: React.ChangeEvent<HTMLInputElement>) => void;
    handleUnit: (event: React.ChangeEvent<HTMLSelectElement>) => void;
    unitList: string[];
    alertVisible: boolean;
    alertColor: AlertColor;
    apiResponse: ApiResponseProps | undefined;
    setAlertVisibility: React.Dispatch<React.SetStateAction<boolean>>;
}


export const AddNewIngredientChild: React.FC<Props> = ({
    handleSubmit,
    handleIngredient,
    handleUnit,
    unitList,
    alertVisible,
    alertColor,
    apiResponse,
    setAlertVisibility
}) => {
    return (
        <>
            <hr />
            <h5>Cannot find ingredient you are looking for? Then add it:</h5>
            <form className='row g-3 form-width' onSubmit={handleSubmit}>

                <div className='col-auto'>
                    <label htmlFor="ingredient" className='visually-hidden'>
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
                        {unitList.map((u) => (
                            <option key={u} value={u}>
                                {u}
                            </option>
                        ))}
                    </select>
                </div>

                <div className='col-auto'>
                    <Button color='success' type='submit' children='Add' />
                </div>
            </form>
            <div className="form-width">
                {alertVisible && (
                    <Alert
                        color={alertColor}
                        message={apiResponse?.message}
                        onClose={() => setAlertVisibility(false)}
                    />
                )}
            </div>
        </>
    )
}
