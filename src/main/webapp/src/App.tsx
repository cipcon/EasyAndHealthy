import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Home } from './Homepage/Home';
import { Login } from './User/Login';
import { Register } from './User/Register';
import { Profile } from './User/Profile';
import { useCallback, useEffect, useState } from 'react';
import { AppContext } from './Contexts/Context';
import { RecipeDetails } from './Recipes/RecipeDetails';
import { Navbar } from './components/Navbar';
import FavoriteRecipes from './Recipes/FavoriteRecipes';
import { Ingredients } from './Ingredients/Ingredients';
import EditOwnRecipes from './User/Components/EditOwnRecipes';
import { CreateRecipe } from './Recipes/Components/CreateRecipe';
import { EditRecipe } from './Recipes/Components/EditRecipe';
import { SearchRecipe } from './Recipes/SearchRecipe';

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

  useEffect(() => {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      setUserCredentials(JSON.parse(storedUser));
    }
  }, []);

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
              <Route path='/recipes' element={<SearchRecipe />} />
              <Route path='/favoriteRecipes' element={<FavoriteRecipes />} />
              <Route path='*' element={<h1>You are out of the page</h1>} />
              <Route path='/recipeDetails' element={<RecipeDetails />} />
              <Route path='/ingredients' element={<Ingredients />} />
              <Route path='/editOwnRecipes' element={<EditOwnRecipes />} />
              <Route path='/create-recipe' element={<CreateRecipe />} />
              <Route path='/edit-recipe' element={<EditRecipe />} />
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
