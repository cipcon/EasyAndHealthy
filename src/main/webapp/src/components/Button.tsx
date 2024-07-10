import React from 'react';

interface Props {
    color?: 'success' | 'warning' | 'danger';
    onClick?: () => void;
    type?: 'button' | 'submit';
    children: React.ReactNode;
}

const Button: React.FC<Props> = ({ color, onClick, type, children }) => {
    return (
        <button
            className={`col-auto btn btn-${color}`}
            type={type}
            onClick={onClick}
        >
            {children}
        </button>
    )
}

export default Button;