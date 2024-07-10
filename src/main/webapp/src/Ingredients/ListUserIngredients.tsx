import React, { useEffect, useState } from 'react'
import { useUserContext } from '../Contexts/Context'
import Button from '../components/Button';
import { Alert } from '../components/Alert';

interface Props {
    ingredientName: string;
    ingredientId: number;
    quantity: number;
    unit: string;
}

interface RemovedProps {
    removed: boolean;
    message: string;
}

export const ListUserIngredients: React.FC = () => {
    const { userCredentials } = useUserContext();
    const [apiResponse, setApiResponse] = useState<Props[]>();
    const [removed, setRemoved] = useState<RemovedProps>();
    const [alertVisible, setAlertVisibility] = useState<boolean>(false);
    const [showUpdate, setShowUpdate] = useState<boolean>(false);

    useEffect(() => {
        fetchData();
    }, [userCredentials.id, removed]);

    const fetchData = async () => {
        try {
            const response = await fetch('/manageStock/listUserIngredients', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(userCredentials.id)
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data: Props[] = await response.json();
            setApiResponse(data);
        } catch (error) {
            console.error("Error fetching recipes:", error)
        }
    }

    const removeIngredient = async (ingredientId: number) => {
        const userId: number = userCredentials.id;
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

    const updateIngredient = async () => {

    }

    return (
        <>
            <hr />
            {!apiResponse ?
                <h5>No ingredient added</h5> :
                <div>
                    <h5>Your ingredients</h5>
                    {alertVisible && <Alert message={removed?.message} onClose={() => setAlertVisibility(false)} type='button' color='success' />}
                    <ul style={{ padding: 0 }}>
                        {apiResponse?.map((ingredient) => (
                            <li key={ingredient.ingredientId} className='row g-1' style={{ gap: 10, margin: 5, listStyleType: 'none' }}>
                                <span className='col-auto' style={{ alignContent: 'center' }}>{ingredient.ingredientName}: {ingredient.quantity} {ingredient.unit}</span>
                                <Button color='warning' onClick={() => updateIngredient} type='button' children='Update' />
                                <Button color='danger' onClick={() => removeIngredient(ingredient.ingredientId)} type='button' children='Delete' />
                                <div>

                                </div>
                            </li>
                        ))}
                    </ul>
                </div>
            }
        </>
    )
}
