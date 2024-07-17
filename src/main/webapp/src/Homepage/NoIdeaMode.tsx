import React, { useEffect, useState } from 'react'
import '../Recipes/Recipes.css'
import { useNavigate } from 'react-router-dom';
import { UserProps } from './Home';


interface Recipe {
    recipeName: string;
    recipeId: number;
    servings: number;
}

export const NoIdeaMode: React.FC<UserProps> = ({ userId, userName }) => {
    const navigate = useNavigate();
    const [recipes, setRecipes] = useState<Recipe[]>();

    useEffect(() => {
        fetchData();
    }, [userId]);

    const handleClick = (recipe: Recipe) => {
        navigate('/recipeDetails', { state: { recipeDetails: recipe } });
    }

    const fetchData = async () => {
        try {
            const response = await fetch('/searchRecipe/noIdeaMode', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(userId)
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data: Recipe[] = await response.json();
            setRecipes(data);
        } catch (error) {
            console.error("Error fetching recipes:", error)
        }
    }



    return (
        <>
            <h4>Hello {userName} how are you today?</h4>
            <hr />
            <h5>Based on your ingredients:</h5>
            <ul className="recipes">
                {recipes?.map((recipe) => (
                    <li key={recipe.recipeId} className='li-ingredients'>
                        <a href="/recipeDetails" className='recipe-name col-auto' onClick={() => handleClick(recipe)}>
                            {recipe.recipeName}
                        </a>
                    </li>
                ))}

            </ul>
        </>
    )
}
