import { useEffect, useState } from "react"
import { UserRecipesComponent } from "./UserRecipesComponent";

export const UserRecipes = () => {
    const [recipes, setRecipe] = useState([]);

    const fetchUserData = async () => {
        try {
            const userId = 15;
            const response = await fetch(`/recipe/recipesFromUser/${userId}`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data = await response.json();
            setRecipe(data);
        } catch (error) {
            console.error("Error fetching recipes: ", error)
        }
    }

    useEffect(() => {
        fetchUserData();
    }, []);

    return (
        <div>
            <h1>Your saved recipes</h1>
            <UserRecipesComponent recipes={recipes}/>
        </div>
    )
}