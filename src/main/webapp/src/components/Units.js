import React, { useState, useEffect } from 'react';

interface Unit {
    id: number;
    unit: string;
}

export default function getUnits() {
    const [unit, setUnit] = useState<Unit[]>([]);

    useEffect(() => {
        const fetchUnits = async () => {
            const response = await fetch('/ingredients/getAllIngredient');
            const unit = await response.json();
            setUnit(unit);
        }
    })

}



/* const Units = ({ units }) => {
    return (
        <>
            <h1>List of Ingredients</h1>
            <div className="dropdown">
                <button className="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                    Select Unit
                </button>
                <ul className="dropdown-menu">
                    {units.map((unit) =>
                        <li>{unit}</li>
                    )}
                </ul>
            </div>
        </>
    )
}

export default Units; */