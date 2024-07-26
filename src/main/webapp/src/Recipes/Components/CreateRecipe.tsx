import React, { ChangeEvent, FormEvent, useEffect, useState } from 'react'
import { Ingredient } from '../../Ingredients/Ingredients';
import Button from '../../components/Button';
import { AlertColor } from '../../Ingredients/Components/AddIngredients';
import { Alert } from '../../components/Alert';
import { useUserContext } from '../../Contexts/Context';
import { fetchIngredients } from '../../Ingredients/Components/FetchAllIngredients';
import { IngredientComponent } from '../../Ingredients/Components/IngredientComponent';
import { RecipeNameAndServings } from './RecipeNameAndServings';

interface IngredientsProps {
    ingredientId: number;
    quantity: number;
}

interface RecipeProps {
    recipeName: string;
    servings: number;
    userId: number;
    ingredient: IngredientsProps[];
}

interface RecipeApiResponse {
    added: boolean;
    message: string;
}

export const CreateRecipe: React.FC = () => {
    const { userCredentials } = useUserContext();
    const [allIngredients, setAllIngredients] = useState<Ingredient[]>([]);
    const [ingredientsProps, setIngredientsProps] = useState<IngredientsProps[]>([]);
    const [recipeProps, setRecipeProps] = useState<RecipeProps>(
        { recipeName: '', servings: 0, userId: 0, ingredient: [] });
    const [recipeApiResponse, setRecipeApiResponse] = useState<RecipeApiResponse>(
        { added: false, message: '' });
    const [alertVisible, setAlertVisibility] = useState(false);
    const [alertColor, setAlertColor] = useState<AlertColor>();

    useEffect(() => {
        const fetchAndSetIngredients = async () => {
            const fetchedIngredients = await fetchIngredients();
            setAllIngredients(fetchedIngredients);
        };

        fetchAndSetIngredients();
    }, []);

    const handleRecipeName = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRecipeProps({ ...recipeProps, recipeName: event.target.value });
    }

    const handleServings = (event: React.ChangeEvent<HTMLInputElement>) => {
        const value = parseInt(event.target.value, 10);
        setRecipeProps({ ...recipeProps, servings: value });
    }

    const addNewRecipe = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const newRecipeProps = {
            ...recipeProps,
            userId: userCredentials.id,
            ingredient: ingredientsProps
        };
        console.log(newRecipeProps);

        try {
            const response = await fetch('/recipe/createRecipe', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(newRecipeProps)
            });
            if (!response.ok) {
                setRecipeApiResponse({ added: false, message: 'Something went wrong, please try again later' });
                setAlertColor('danger');
                throw new Error('Failed to add recipe');
            }
            const data: RecipeApiResponse = await response.json();
            setRecipeApiResponse(data);
            console.log(response.status);
            console.log(data);
            setAlertColor(data.added ? 'success' : 'danger');
        } catch (error) {
            console.error('Error while adding the recipe:', error);
            setRecipeApiResponse({ added: false, message: 'Something went wrong, please try again later' });
            setAlertColor('danger');
        }
        setAlertVisibility(true);
    }

    const handleIngredientChange = (index: number, field: 'ingredientId' | 'quantity', value: number) => {
        setIngredientsProps(prevIngredients => {
            const updatedIngredients = [...prevIngredients];
            updatedIngredients[index] = {
                ...updatedIngredients[index],
                [field]: value
            };
            return updatedIngredients;
        });
    };

    const addIngredient = () => {
        setIngredientsProps(prevIngredients => [
            ...prevIngredients,
            { ingredientId: 0, quantity: 0 }
        ]);
    };

    const deleteIngredient = (index: number) => {
        setIngredientsProps(prevIngredients =>
            prevIngredients.filter((_, i) => i !== index)
        );
    }

    return (
        <>
            <form className='row g-3 form-width add-form' onSubmit={addNewRecipe} style={{ margin: 'auto', justifyContent: 'center' }}>
                <div>
                    <h5>Create new Recipe</h5>
                    <div className="col-auto">
                        {alertVisible && <Alert color={alertColor} message={recipeApiResponse.message} onClose={() => setAlertVisibility(false)} />}
                    </div>
                </div>
                <RecipeNameAndServings
                    handleRecipeName={handleRecipeName}
                    handleServings={handleServings} />
                {ingredientsProps.map((ingredient, index) => (
                    <IngredientComponent
                        handleDelete={() => deleteIngredient(index)}
                        key={index}
                        handleIngredientId={(e) => handleIngredientChange(index, 'ingredientId', parseInt(e.target.value, 10))}
                        allIngredients={allIngredients}
                        handleIngredientQuantity={(e) => handleIngredientChange(index, 'quantity', parseInt(e.target.value, 10))}
                        ingredientId={ingredient.ingredientId}
                        quantity={ingredient.quantity}
                    />
                ))}
                <div>
                    <Button color='success' type='button' onClick={addIngredient}>Add Ingredient</Button>
                </div>
                <div>
                    <Button color='success' type='submit'>Add new Recipe</Button>
                </div>

            </form>
        </>
    )
}