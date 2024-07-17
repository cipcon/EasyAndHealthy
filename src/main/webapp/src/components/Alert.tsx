import { AlertColor } from "../Ingredients/Components/AddIngredients";

interface Props {
    message?: string;
    onClose?: () => void;
    children?: string;
    type?: 'button' | 'submit';
    color: AlertColor;
}

export const Alert = ({ message, onClose, children, type, color }: Props) => {
    return (
        <div className={"alert alert-dismissible align-header alert-" + color} role="alert">
            {children} {message}
            <button type={type} className="btn-close" data-bs-dismiss="alert" aria-label="Close" onClick={onClose}></button>
        </div>
    )
}
