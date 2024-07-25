import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';
import Button from '../../components/Button';
import { Recipe } from '../../Homepage/NoIdeaMode';


export const SearchRecipe: React.FC = () => {
    const [recipes, setRecipes] = useState<Recipe[]>([]);
    const [name, setName] = useState<string>('');
    const navigate = useNavigate();


    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        event.preventDefault();
        setName(event.target.value);
    }

    const handleClick = (recipeWithoutIngredients: Recipe) => {
        navigate('/recipeDetails', { state: { recipeWithoutIngredients } });
    }

    const fetchData = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        console.log(name);
        try {
            const response = await fetch('/searchRecipe/recipeSearch', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(name)
            })
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data: Recipe[] = await response.json();
            setRecipes(data);
        } catch (error) {
            console.error("Error fetching recipes:", error)
        }
        console.log(recipes);
    }

    return (
        <div>
            <form onSubmit={fetchData}>
                <input
                    className="form-control"
                    id='recipe'
                    placeholder='Recipe or ingredient name'
                    type="text"
                    onChange={handleChange}
                />
                <Button color='success' children={'Search'} type='submit' />
            </form>
            <div>
                {recipes?.map((recipe) =>
                    <div key={recipe.recipeId}>
                        <a href="/recipeDetails" onClick={() => handleClick(recipe)}>
                            {recipe.recipeName}
                        </a>
                    </div>
                )}
            </div>
        </div>
    )
}
