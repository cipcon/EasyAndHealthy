import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Navbar } from './MainComponents/Navbar';
import { Home } from './Homepage/Home';
import { Login } from './User/Login';
import { Register } from './User/Register';
import { Profile } from './User/Profile';
import { Allrecipes } from './Recipes/AllRecipes';
import { useCallback, useEffect, useState } from 'react';
import { AppContext } from './Contexts/context';

export interface User {
  id: number;
  name: string;
  token: string | null;
}

export interface AppContextType {
  userCredentials: User;
  setUserCredentials: React.Dispatch<React.SetStateAction<User>>;
  logout: () => void;
}

const App: React.FC = () => {
  const [userCredentials, setUserCredentials] = useState<User>({ id: 0, name: '', token: null });

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

  const logout = useCallback(() => {
    setUserCredentials({ id: 0, name: '', token: null })
  }, []);

  const contextValue: AppContextType = {
    userCredentials,
    setUserCredentials,
    logout
  };

  return (
    <div>
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
              <Route path='/allRecipes' element={<Allrecipes />} />
              <Route path='*' element={<h1>You are out of the page</h1>} />
            </Routes>
          </main>
          <footer>
          </footer>
        </Router>
      </AppContext.Provider>
    </div>
  );
}

export default App;
