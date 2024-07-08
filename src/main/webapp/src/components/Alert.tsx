
interface Props {
    message: string;
    onClose?: () => void;
    recipe?: string;
    type?: string;
}

export const Alert = ({ message, onClose, recipe, type }: Props) => {
    return (
        <div className={"alert alert-dismissible align-header alert-" + type} role="alert" style={{ maxWidth: 400 }}>
            {recipe} {message}
            <button type="button" className="btn-close" data-bs-dismiss="alert" aria-label="Close" onClick={onClose}></button>
        </div>
    )
}
