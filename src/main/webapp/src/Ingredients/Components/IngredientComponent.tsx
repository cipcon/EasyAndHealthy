import React, { ChangeEvent } from "react"
import { Ingredient } from "../Ingredients";

interface Props {
    handleIngredientId: React.ChangeEventHandler<HTMLSelectElement>;
    allIngredients: Ingredient[];
    handleIngredientQuantity: (event: ChangeEvent<HTMLInputElement>) => void;
}

export const IngredientComponent: React.FC<Props> = ({
    handleIngredientId,
    allIngredients,
    handleIngredientQuantity,

}) => {
    return (
        <div className='row g-3 form-width' style={{ margin: 'auto', justifyContent: 'center' }}>
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
                    onChange={handleIngredientQuantity}
                    placeholder='quantity'
                    required
                    type='number'
                    id='quantity'
                    min='1' />
            </div>
        </div>
    )
}
