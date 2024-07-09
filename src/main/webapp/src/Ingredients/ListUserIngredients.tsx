import React, { useEffect, useState } from 'react'
import { useUserContext } from '../Contexts/Context'

interface Props {
    ingredientName: string;
    ingredientId: number;
    quantity: number;
    unit: string;
}

export const ListUserIngredients: React.FC = () => {
    const { userCredentials } = useUserContext();
    const [apiResponse, setApiResponse] = useState<Props[]>();

    useEffect(() => {
        fetchData()
    }, []);

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

    return (
        <>
            <hr />
            {!apiResponse ?
                <h5>No ingredient added</h5> :
                <div>
                    <h5>Your ingredients</h5>
                    <ul>
                        {apiResponse?.map((ingredient) => (
                            <li key={ingredient.ingredientId}>
                                {ingredient.ingredientName}: {ingredient.quantity} {ingredient.unit}
                            </li>
                        ))}
                    </ul>
                </div>
            }
        </>
    )
}
