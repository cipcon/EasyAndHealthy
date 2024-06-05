import './App.css';
import React, { useState, useEffect } from 'react';
import Ingredients from './components/Ingredients';

function App() {
  const [ingredients, setIngredients] = useState([])

  useEffect(() => {
    fetch('/ingredients/getAllIngredients')
      .then(res => res.json())
      .then((data) => setIngredients(data))
      .catch(console.error);
  }, [])

    return (
      <Ingredients ingredients={ingredients} />
    );
  
}

export default App;
