import React from 'react'

interface Props {
    handleRecipeName: (event: React.ChangeEvent<HTMLInputElement>) => void;
    handleServings: (event: React.ChangeEvent<HTMLInputElement>) => void;
}

export const RecipeNameAndServings: React.FC<Props> = ({
    handleRecipeName,
    handleServings
}) => {
    return (
        <>
            <div className='col-auto'>
                <label htmlFor="recipeName" className='visually-hidden'>Recipe Name</label>
                <input
                    className='form-control'
                    id='recipeName'
                    onChange={handleRecipeName}
                    placeholder='Recipe Name'
                    required
                    type="text"
                />
            </div>

            <div className='col-auto'>
                <label htmlFor="servings" className='visually-hidden'>Recipe Name</label>
                <input
                    className='form-control'
                    id='servings'
                    onChange={handleServings}
                    placeholder='Servings'
                    required
                    type="number"
                    min='1'
                />
            </div>
        </>
    )
}
