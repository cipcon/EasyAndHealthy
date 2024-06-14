
interface UserRecipesProps  {
    recipes: string[];
}

export const UserRecipesComponent: React.FC<UserRecipesProps> = ({ recipes }) => {

    return (
        <div>
            <ul>
                {recipes.length > 0 ? (
                    recipes.map((recipe, index) => (
                        <li key={index}>{recipe}</li>
                    ))
                ) : (
                    <p>No saved recipes found.</p>
                )}

            </ul>
        </div>
    )
}