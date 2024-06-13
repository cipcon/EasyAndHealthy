import Axios from "axios";
import { useEffect, useState } from "react"

export const UserRecipes = () => {
    const [recipe, setRecipe] = useState([]);


    useEffect(() => {
        Axios.get(`/recipesFromUser/${15}`).then((res) => {
            setRecipe(res.data);
        })
    })

    return (
        <div>
            <h1>Your saved recipes</h1>
            <div>
                <ol>
                    {recipe.map((recipe) =>
                    <li>{recipe}</li>
                    )}
                </ol>
            </div>
        </div>
    )
}