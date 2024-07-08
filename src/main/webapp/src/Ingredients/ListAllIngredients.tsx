import React, { useEffect, useState } from "react";
import internal from "stream";
import { UserIngredients } from "./UserIngredients";

export interface Ingredient {
    ingredientName: string;
    ingredientId: number;
    unit: string;
}

export const ListAllIngredients: React.FC = () => {
    const [ingredients, setIngredients] = useState<Ingredient[]>([]);

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
            <UserIngredients ingredients={ingredients} />
        </>
    );

}