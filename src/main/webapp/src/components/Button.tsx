import React from 'react';
import '../Recipes/Recipes.css'


interface Props {
    color?: 'success' | 'warning' | 'danger';
    onClick?: () => void;
    type?: 'button' | 'submit';
    children: React.ReactNode;
    heart?: '&#x1F9E1;' | '&#x1F49A;';
}

const Button: React.FC<Props> = ({ color, onClick, type, children, heart }) => {

    const heartButton =
        <div className='rightSide'>
            <button
                className='heartButton'
                type={type}
                onClick={onClick}>
                {heart}
            </button>
        </div>

    return (
        <>
            {
                heart ?
                    heartButton
                    :
                    <button
                        className={`col-auto btn btn-${color}`}
                        type={type}
                        onClick={onClick}
                    >
                        {children}
                    </button>
            }
        </>
    )
}

export default Button;