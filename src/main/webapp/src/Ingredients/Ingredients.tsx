import { useState, useEffect } from "react";
import { AddIngredient } from "./Components/AddIngredients";
import { ListUserIngredients } from "./Components/ListUserIngredients";
import { useUserContext } from "../Contexts/Context";
import AddNewIngredient from "./Components/AddNewIngredient";
import { fetchIngredients } from "./Components/FetchAllIngredients";
import { DeleteIngredient } from "./Components/DeleteIngredient";


export interface Ingredient {
    ingredientName: string;
    ingredientId: number;
    unit: string;
}

export interface IngredientChanged {
    ingredientChanged: number;
    setIngredientChanged: React.Dispatch<React.SetStateAction<number>>
}

export const Ingredients: React.FC = () => {
    const [ingredients, setIngredients] = useState<Ingredient[]>([]);
    const { userCredentials } = useUserContext();
    const [ingredientChanged, setIngredientChanged] = useState<number>(0);

    const fetchAndSetIngredients = async () => {
        const fetchedIngredients = await fetchIngredients();
        setIngredients(fetchedIngredients);
    };


    useEffect(() => {
        fetchAndSetIngredients();
        console.log(userCredentials);
    }, [ingredientChanged, userCredentials.id]);

    return (
        <>
            <AddIngredient ingredientChanged={ingredientChanged} setIngredientChanged={setIngredientChanged} ingredients={ingredients} userId={userCredentials.id} />
            <AddNewIngredient ingredientChanged={ingredientChanged} setIngredientChanged={setIngredientChanged} />
            <DeleteIngredient ingredients={ingredients} ingredientChanged={ingredientChanged} setIngredientChanged={setIngredientChanged} />
            <ListUserIngredients userId={userCredentials.id} ingredientChanged={ingredientChanged} />
        </>
    );

}