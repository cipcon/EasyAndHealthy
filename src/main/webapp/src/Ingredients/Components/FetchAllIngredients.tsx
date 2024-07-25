import { Ingredient } from '../Ingredients';

export const fetchIngredients = async (): Promise<Ingredient[]> => {
    try {
        const response = await fetch('/ingredients/getAllIngredients');
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data: Ingredient[] = await response.json();
        const sortedData = data.sort((a, b) => a.ingredientName.localeCompare(b.ingredientName));

        return sortedData;
    } catch (error) {
        console.error("Error fetching recipes: ", error);
        return []; // Return an empty array or handle the error as needed
    }
}


