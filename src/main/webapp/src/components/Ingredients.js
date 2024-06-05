import React from 'react'


const Ingredients = ({ ingredients }) => {
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
    )
}

export default Ingredients