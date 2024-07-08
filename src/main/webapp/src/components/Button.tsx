
interface Props {
    color?: 'success' | 'warning' | 'danger';
    onClick: () => void;
    type: string;
}

const Button = ({ color, onClick, type }: Props) => {
    return (
        <button className={"btn btn-" + color} type="button" onClick={onClick} >{type}</button>
    )
}

export default Button