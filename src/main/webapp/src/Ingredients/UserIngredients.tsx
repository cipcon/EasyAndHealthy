import React, { ChangeEvent, useEffect, useState } from 'react'
import { Ingredient } from './ListAllIngredients'
import Button from '../components/Button';
import { useUserContext } from '../Contexts/Context';

interface Props {
    ingredients: Ingredient[];
}

interface AddProps {
    ingredientId: number;
    quantity: number;
    userId: number;
}

export const UserIngredients: React.FC<Props> = ({ ingredients }) => {
    const [addIngredientProps, setAddIngredientProps] = useState<AddProps>({ ingredientId: 0, quantity: 0, userId: 0 });
    const { userCredentials } = useUserContext();
    const [insertError, setInsertError] = useState<string>();

    useEffect(() => {
        if (userCredentials) {
            setAddIngredientProps((prevProps) => ({ ...prevProps, userId: userCredentials.id }));
        }
    }, [userCredentials]);

    const addIngredient = async ({ ingredientId, quantity, userId }: AddProps) => {

    }

    const handleQuantity = (event: ChangeEvent<HTMLInputElement>) => {
        setAddIngredientProps({ ...addIngredientProps, quantity: parseInt(event.target.value, 10) });
    }

    const handleIngredientId: React.ChangeEventHandler<HTMLSelectElement> = (event) => {
        setAddIngredientProps({ ...addIngredientProps, ingredientId: parseInt(event.target.value, 10) })
    }

    return (
        <>
            <h1 className='header-center-align'>Add Ingredient</h1>
            <form className='row g-3' style={{ maxWidth: 600 }}>
                <div className='col-auto'>
                    <label htmlFor="ingredient" className='visually-hidden'>Ingredient</label>
                    <select
                        id="ingredient"
                        onChange={handleIngredientId}
                        className='form-select'
                    >
                        {ingredients.map((ingredient) =>
                            <option
                                value={ingredient.ingredientId}
                                key={ingredient.ingredientId}
                            >
                                {ingredient.ingredientName} ({ingredient.unit})
                            </option>
                        )}
                    </select>
                </div>
                <div className='col-auto'>
                    <input
                        className='form-control'
                        onChange={handleQuantity}
                        placeholder='quantity'
                        required
                        type="number"
                        value={addIngredientProps.quantity}
                    />
                </div>
                <div className='col-auto'>
                    <Button color='success' onClick={() => addIngredient(addIngredientProps)} type='Add' />
                </div>

            </form>
        </>
    )
}
