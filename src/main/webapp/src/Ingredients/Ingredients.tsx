import { useState, useEffect } from "react";
import { AddIngredient } from "./Components/AddIngredients";
import { ListUserIngredients } from "./Components/ListUserIngredients";
import './Components/Ingredients.css'
import { useUserContext } from "../Contexts/Context";


export interface Ingredient {
    ingredientName: string;
    ingredientId: number;
    unit: string;
}


export const Ingredients: React.FC = () => {
    const [ingredients, setIngredients] = useState<Ingredient[]>([]);
    const { userCredentials } = useUserContext();

    useEffect(() => {
        fetchData();
    }, []);

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

    return (
        <>
            <AddIngredient ingredients={ingredients} userId={userCredentials.id} />
            <ListUserIngredients userId={userCredentials.id} />
        </>
    );

}