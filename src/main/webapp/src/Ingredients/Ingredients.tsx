import { useState, useEffect } from "react";
import { AddIngredient } from "./Components/AddIngredients";
import { ListUserIngredients } from "./Components/ListUserIngredients";
import { useUserContext } from "../Contexts/Context";
import AddNewIngredient from "./Components/AddNewIngredient";
import { fetchIngredients } from "./Components/FetchAllIngredients";


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
        const fetchAndSetIngredients = async () => {
            const fetchedIngredients = await fetchIngredients();
            setIngredients(fetchedIngredients);
        };

        fetchAndSetIngredients();
    }, [refreshTrigger]);


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