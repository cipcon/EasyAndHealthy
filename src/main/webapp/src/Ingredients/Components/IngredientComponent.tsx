import React, { ChangeEvent } from "react"
import { Ingredient } from "../Ingredients";
import Button from "../../components/Button";

interface Props {
    handleIngredientId: (event: ChangeEvent<HTMLSelectElement>) => void;
    handleIngredientQuantity: (event: ChangeEvent<HTMLInputElement>) => void;
    handleDelete: () => void;
    allIngredients: Ingredient[];
    ingredientId: number;
    quantity: number;
}

export const IngredientComponent: React.FC<Props> = ({
    handleIngredientId,
    handleIngredientQuantity,
    handleDelete,
    allIngredients,
    ingredientId,
    quantity
}) => {
    return (
        <div className='row g-3 form-width' style={{ margin: 'auto', justifyContent: 'center' }}>
            <div className='col-auto'>
                <label htmlFor="ingredient" className='visually-hidden'>Ingredient</label>
                <select
                    className='form-select'
                    id="ingredient"
                    onChange={handleIngredientId}
                    value={ingredientId}
                    required
                >
                    <option value="" hidden>Select an ingredient</option>
                    {allIngredients.map((ingredient) => (
                        <option
                            key={ingredient.ingredientId}
                            value={ingredient.ingredientId}
                        >
                            {ingredient.ingredientName} ({ingredient.unit})
                        </option>
                    ))}
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
                    min='1'
                    value={quantity}
                />
            </div>
            <div className='col-auto'>
                <Button color='danger' type='button' onClick={handleDelete}>
                    Delete
                </Button>
            </div>
        </div>
    )
}