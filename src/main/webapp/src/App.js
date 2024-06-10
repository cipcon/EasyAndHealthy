import './App.css';
import React, { useState, useEffect } from 'react';
import Ingredients from './components/Ingredients';
import Units from './components/Units'

function App() {
  const [ingredient, setIngredient] = useState([]);
  const [unit, setUnit] = useState([]);

  useEffect(() => {
    fetch('/ingredients/getAllIngredients')
      .then(res => res.json())
      .then((data) => setIngredient(data))
      .catch(console.error);
  }, []);

  useEffect(() => {
    fetch('/ingredients/getAllUnits')
      .then(res => res.json())
      .then((data) => setUnit(data))
      .catch(console.error);
  }, []);

  return (
    <>
      <Ingredients ingredients={ingredient} />
      <Units units={unit} />
    </>
  );

}

export default App;
