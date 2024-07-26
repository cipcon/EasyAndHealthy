import React, { FormEvent } from 'react'
import { Alert } from '../../components/Alert'
import Button from '../../components/Button'
import { Ingredient } from '../Ingredients';
import { AlertColor } from './AddIngredients';

interface Props {
    handleDeleteIngredient: (event: FormEvent<HTMLFormElement>) => Promise<void>;
    handleIngredientId: React.ChangeEventHandler<HTMLSelectElement>;
    ingredients: Ingredient[];
    alertVisible: boolean;
    alertColor: AlertColor;
    message: string;
    setAlertVisibility: React.Dispatch<React.SetStateAction<boolean>>;
}

export const DeleteIngredientChild: React.FC<Props> = ({
    handleDeleteIngredient,
    handleIngredientId,
    ingredients,
    alertVisible,
    alertColor,
    message,
    setAlertVisibility
}) => {
    return (
        <>
            <hr />
            <h5>Delete Globally Ingredient</h5>
            <form className='row g-3 form-width' onSubmit={handleDeleteIngredient}>
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
                        <option value="" hidden>Select an ingredient</option>
                        {ingredients.map((ingredient) => <option
                            key={ingredient.ingredientId}
                            value={ingredient.ingredientId}
                        >
                            {ingredient.ingredientName} ({ingredient.unit})
                        </option>
                        )}
                    </select>
                </div>
                <div className='col-auto'>
                    <Button color='danger' type='submit' children='Delete' />
                </div>
            </form>
            <div className="form-width">
                {alertVisible && <Alert color={alertColor} message={message} onClose={() => setAlertVisibility(false)} />}
            </div>
        </>
    )
}
