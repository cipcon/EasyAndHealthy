import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';
import Button from '../../components/Button';
import { Recipe } from '../../Homepage/NoIdeaMode';


export const SearchRecipe: React.FC = () => {
    const [recipes, setRecipes] = useState<Recipe[]>([]);
    const [name, setName] = useState<string>('');
    const [search, setSearch] = useState<boolean>(false);
    const navigate = useNavigate();

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        event.preventDefault();
        setName(event.target.value);
    }

    const handleClick = (recipeWithoutIngredients: Recipe) => {
        navigate('/recipeDetails', { state: { recipeWithoutIngredients } });
    }

    const fetchData = async () => {
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
        setSearch(true);
        console.log(recipes);
    }

    return (
        <div>
            <input
                className="form-control"
                id='recipe'
                placeholder='Recipe or ingredient name'
                type="text"
                onChange={handleChange}
            />
            <Button color='success' children={'Search'} type='button' onClick={fetchData} />
            {search &&
                <div>
                    {recipes?.map((recipe) =>
                        <div>
                            <a href="/recipeDetails" onClick={() => handleClick(recipe)}>
                                {recipe.recipeName}
                            </a>
                        </div>
                    )}
                </div>}
        </div>
    )
}
