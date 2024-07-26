import { FormEvent, ChangeEvent } from 'react';
import { Alert } from '../../components/Alert'
import Button from '../../components/Button'
import { Ingredient } from "../Ingredients";
import { AlertColor } from './AddIngredients';


interface AddIngredientProps {
    ingredients: Ingredient[];
    handleAddIngredient: (event: FormEvent<HTMLFormElement>) => Promise<void>;
    handleIngredientId: React.ChangeEventHandler<HTMLSelectElement>;
    handleQuantity: (event: ChangeEvent<HTMLInputElement>) => void;
    alertVisible: boolean;
    alertColor: AlertColor;
    message: string;
    setAlertVisibility: (isVisible: boolean) => void;
}

const AddIngredientComponent: React.FC<AddIngredientProps> = ({
    ingredients,
    handleAddIngredient,
    handleIngredientId,
    handleQuantity,
    alertVisible,
    alertColor,
    message,
    setAlertVisibility
}) => {
    return <>
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
                    min='1' />
            </div>
            <div className='col-auto'>
                <Button color='success' type='submit' children='Add' />
            </div>
        </form>
        <div className="form-width">
            {alertVisible && <Alert color={alertColor} message={message} onClose={() => setAlertVisibility(false)} />}
        </div>
    </>;
};

export default AddIngredientComponent;
