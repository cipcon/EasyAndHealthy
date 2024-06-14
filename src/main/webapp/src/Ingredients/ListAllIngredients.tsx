import { useEffect, useState } from "react";

export const ListAllIngredients = () => {
    const [ingredients, setIngredients] = useState([])

    const fetchData = () => {
        fetch('/ingredients/getAllIngredients')
            .then(res => res.json())
            .then((data) => setIngredients(data))
            .catch(console.error);
    }

    useEffect(() => {
        fetchData();
    }, []);

    return (
        <div>
            <h1 className='header-center-align'>Existing ingredients</h1>
            <div>
                <ol role='row'>
                    {ingredients.map((ingredient) =>
                        <li data-label="Ingredient name">{ingredient}</li>
                    )}
                </ol>
            </div>
        </div>
    );

}