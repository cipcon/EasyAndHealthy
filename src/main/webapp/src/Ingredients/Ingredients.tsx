import { useState, useEffect } from "react";
import { AddIngredient } from "./Components/AddIngredients";
import { ListUserIngredients } from "./Components/ListUserIngredients";
import { useUserContext } from "../Contexts/Context";
import AddNewIngredient from "./Components/AddNewIngredient";


export interface Ingredient {
    ingredientName: string;
    ingredientId: number;
    unit: string;
}


export const Ingredients: React.FC = () => {
    const [ingredients, setIngredients] = useState<Ingredient[]>([]);
    const { userCredentials } = useUserContext();
    const [refreshTrigger, setRefreshTrigger] = useState<boolean>(false);

    useEffect(() => {
        fetchData();
    }, [refreshTrigger]);

    const fetchData = async () => {
        try {
            const response = await fetch('/ingredients/getAllIngredients');
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const data: Ingredient[] = await response.json();
            const sortedData = data.sort((a, b) => a.ingredientName.localeCompare(b.ingredientName));
            setIngredients(sortedData);
        } catch (error) {
            console.error("Error fetching recipes: ", error);
        }
    }

    const handleIngredientAdded = () => {
        setRefreshTrigger(prev => !prev);
    }

    return (
        <>
            <AddIngredient ingredients={ingredients} userId={userCredentials.id} />
            <AddNewIngredient onAdded={handleIngredientAdded} />
            <ListUserIngredients userId={userCredentials.id} />
        </>
    );

}