import { AlertColor } from "../Ingredients/AddIngredients";

interface Props {
    message: string;
    onClose?: () => void;
    recipe?: string;
    type?: 'button' | 'submit';
    color: AlertColor;
}

export const Alert = ({ message, onClose, recipe, type, color }: Props) => {
    return (
        <div className={"alert alert-dismissible align-header alert-" + color} role="alert" style={{ maxWidth: 400 }}>
            {recipe} {message}
            <button type={type} className="btn-close" data-bs-dismiss="alert" aria-label="Close" onClick={onClose}></button>
        </div>
    )
}
