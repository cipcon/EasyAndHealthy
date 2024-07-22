import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Home } from './Homepage/Home';
import { Login } from './User/Login';
import { Register } from './User/Register';
import { Profile } from './User/Profile';
import { AllRecipes } from './Recipes/AllRecipes';
import { useCallback, useEffect, useState } from 'react';
import { AppContext } from './Contexts/Context';
import { RecipeDetails } from './Recipes/RecipeDetails';
import { Navbar } from './components/Navbar';
import FavoriteRecipes from './Recipes/FavoriteRecipes';
import { Ingredients } from './Ingredients/Ingredients';
import { ShoppingList } from './Recipes/Components/ShoppingList';
import EditOwnRecipes from './User/Components/EditOwnRecipes';

export interface UserProps {
  id: number;
  name: string;
  token: string | null;
}

export interface AppContextType {
  userCredentials: UserProps;
  setUserCredentials: React.Dispatch<React.SetStateAction<UserProps>>;
  onLogout: () => void;
}

const App: React.FC = () => {
  const [userCredentials, setUserCredentials] = useState<UserProps>({ id: 0, name: '', token: null });

  // the code inside this function changes only when sth is passed in the second parameter. The second parameter is an array that can take more inputs

  useEffect(() => {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      setUserCredentials(JSON.parse(storedUser));
    }
  }, []); // this is the second parameter

  // Example: when i pass a function in this parameter (sth should change when i click a button for eg.), the function inside useEffect() is triggered when the button is clicked
  // else it isn't
  // If i don't pass anything inside the second parameter, the function inside useEffect() only runs once on mount
  // In my case, the useEffect() hook is triggered one time and takes the values. The values are not going to be changed during the app is used
  // have also the possibility to return sth

  useEffect(() => {
    if (userCredentials.token) {
      localStorage.setItem('user', JSON.stringify(userCredentials));
    } else {
      localStorage.removeItem('user');
    }
  }, [userCredentials]);

  const onLogout = useCallback(() => {
    setUserCredentials({ id: 0, name: '', token: null })
  }, []);

  const contextValue: AppContextType = {
    userCredentials,
    setUserCredentials,
    onLogout
  };

  return (
    <>
      <AppContext.Provider value={contextValue}>
        <Router>
          <header>
            <Navbar />
          </header>
          <main>
            <Routes>
              <Route path='/' element={<Home />} />
              <Route path='/login' element={<Login />} />
              <Route path='/register' element={<Register />} />
              <Route path='/profile' element={<Profile />} />
              <Route path='/allRecipes' element={<AllRecipes />} />
              <Route path='/favoriteRecipes' element={<FavoriteRecipes />} />
              <Route path='*' element={<h1>You are out of the page</h1>} />
              <Route path='/recipeDetails' element={<RecipeDetails />} />
              <Route path='/ingredients' element={<Ingredients />} />
              <Route path='/shoppingList' element={<ShoppingList />} />
              <Route path='/editOwnRecipes' element={<EditOwnRecipes />} />
            </Routes>
          </main>
          <footer>
          </footer>
        </Router>
      </AppContext.Provider>
    </>
  );
}

export default App;
