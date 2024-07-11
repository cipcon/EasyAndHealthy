import React, { ChangeEvent, useEffect, useState } from 'react'
import { useUserContext } from '../../Contexts/Context'
import Button from '../../components/Button';
import { Alert } from '../../components/Alert';

export interface ResponseProps {
    ingredientName: string;
    ingredientId: number;
    quantity: number;
    unit: string;
}

export interface RemovedProps {
    removed: boolean;
    message: string;
}

interface UpdateIngredientProps {
    ingredientId: number;
    userId: number;
    quantity: number;
}

interface IngredientNameIdProps {
    ingredientId: number;
    ingredientName: string;
}

interface UserIdProps {
    userId: number;
}

export const ListUserIngredients: React.FC<UserIdProps> = ({ userId }) => {
    const [apiResponse, setApiResponse] = useState<ResponseProps[]>();
    const [removed, setRemoved] = useState<RemovedProps>();
    const [alertVisible, setAlertVisibility] = useState<boolean>(false);
    const [updateVisible, setUpdateVisibility] = useState<boolean>(false);
    const [updateIngredientProps, setUpdateIngredientProps] = useState<UpdateIngredientProps>();
    const [ingredientNameId, setIngredientNameId] = useState<IngredientNameIdProps>({ ingredientId: 0, ingredientName: '' });

    useEffect(() => {
        fetchData();
    });

    const fetchData = async () => {
        try {
            const response = await fetch('/manageStock/listUserIngredients', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(userId)
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data: ResponseProps[] = await response.json();
            setApiResponse(data);
        } catch (error) {
            console.error("Error fetching recipes:", error)
        }
    }

    const removeIngredient = async (ingredientId: number) => {
        try {
            const request = {
                ingredientId: ingredientId,
                userId: userId
            }
            const response = await fetch('/manageStock/removeIngredientFromUserList', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(request)
            });

            if (!response.ok) {
                setRemoved({ removed: false, message: 'Sth. went wrong, please try again later 1' });
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data: boolean = await response.json();
            if (data) {
                setRemoved({ removed: data, message: 'Ingredient successfully removed' });
            }
        } catch (error) {
            console.error("Error fetching recipes:", error)
            setRemoved({ removed: false, message: 'Sth. went wrong, please try again later 2' });
        }
        setAlertVisibility(true);
    }

    const showUpdateIngredient = async (show: boolean, ingredientName: string, ingredientId: number) => {
        setUpdateVisibility(show);
        setIngredientNameId({ ...ingredientNameId, ingredientName: ingredientName, ingredientId: ingredientId });
    }

    const updateIngredient = async () => {
        try {
            const response = await fetch('/manageStock/updateUserStock', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(updateIngredientProps)
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const data: boolean = await response.json();
            setUpdateVisibility(false);
        } catch (error) {
            console.error('Error by updating ingredient:', error);
        }

    }

    const handleQuantity = (event: ChangeEvent<HTMLInputElement>) => {
        const value = parseInt(event.target.value, 10);

        const request: UpdateIngredientProps = {
            ingredientId: ingredientNameId.ingredientId,
            userId: userId,
            quantity: value
        }
        setUpdateIngredientProps(request);
    }

    return (
        <>
            <hr />
            {!apiResponse ?
                <h5>No ingredient added</h5> :

                <div>
                    <h5 className='margin-bottom'>Your ingredients</h5>

                    {/* Update Ingredient */}
                    {updateVisible &&
                        <div className='li-ingredients ul-padding'>
                            <div>
                                <input className="form-control" type="text" placeholder={ingredientNameId.ingredientName} disabled />
                            </div>
                            <div className='col-auto'>
                                <label htmlFor="quantity" className='visually-hidden'>Quantity</label>
                                <input className='form-control' id="quantity" min='1' name="quantity" onChange={handleQuantity} placeholder='quantity' required type="number" />
                            </div>
                            <Button color='warning' type='submit' children='Update' onClick={updateIngredient} />
                        </div>
                    }
                    {alertVisible && <Alert message={removed?.message} onClose={() => setAlertVisibility(false)} type='button' color='success' />}

                    {alertVisible && <Alert message={removed?.message} onClose={() => setAlertVisibility(false)} type='button' color='success' />}

                    {/* List User Ingredients + Delete button */}
                    <ul style={{ padding: 0 }}>
                        {apiResponse?.map((ingredient) => (
                            <li key={ingredient.ingredientId} className='li-ingredients ul-padding'>
                                <span className='col-auto'>{ingredient.ingredientName}: {ingredient.quantity} {ingredient.unit}</span>
                                {!updateVisible && <Button color='warning' onClick={() => showUpdateIngredient(true, ingredient.ingredientName, ingredient.ingredientId)} type='button' children='Update' />}
                                <Button color='danger' onClick={() => removeIngredient(ingredient.ingredientId)} type='button' children='Delete' />
                            </li>
                        ))}
                    </ul>
                </div>
            }
        </>
    )
}
