import { useEffect, useState } from "react";

interface IngredientQuantity {
    ingredient: string;
    quantity: number;
}

interface Recipe {
    recipeName: string;
    ingredientAndQuantity: IngredientQuantity[];
}

interface ApiResponse {
    [recipeName: string]: {
        ingredientAndQuantity: {
            [ingredient: string]: number;
        };
    };
}

export const Allrecipes = () => {
    const [recipe, setRecipe] = useState<Recipe[]>([]);

    const fetchData = async () => {
        try {
            const response = await fetch('/recipe/readAllRecipes');
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data: ApiResponse = await response.json();

            // Transform the data into the expected format
            const transformedData: Recipe[] = Object.entries(data).map(([recipeName, value]) => {
                const ingredientAndQuantity = Object.entries(value.ingredientAndQuantity).map(([ingredient, quantity]) => ({
                    ingredient,
                    quantity
                }));
                return { recipeName, ingredientAndQuantity };
            });

            setRecipe(transformedData);
        } catch (error) {
            console.error("Error fetching recipes:", error);
        }
    };

    useEffect(() => {
        fetchData();
    }, []);

    return (
        <div>
            <ul>
                {recipe.map((list) => (
                    <div key={list.recipeName}>
                        <li><strong>{list.recipeName}</strong></li>
                        <ul>
                            {list.ingredientAndQuantity.map((item) => (
                                <li key={item.ingredient}>{item.ingredient}: {item.quantity}</li>
                            ))}
                        </ul>
                    </div>
                ))}
            </ul>
        </div>
    );
}
