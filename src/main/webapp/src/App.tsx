import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Navbar } from './MainComponents/Navbar';
import { Home } from './Homepage/Home';
import { Login } from './User/Login';
import { Register } from './User/Register';
import { Profile } from './User/Profile';
import { Allrecipes } from './Recipes/AllRecipes';
import { useState } from 'react';
import { AppContext } from './context';

export interface User {
  id: number;
  name: string;
}

interface AppContextType {
  userCredentials: User;
  setUserCredentials: React.Dispatch<React.SetStateAction<User>>;
}

function App() {
  const [userCredentials, setUserCredentials] = useState<User>({ id: 0, name: '' });

  return (
    <div>
      <AppContext.Provider value={{ userCredentials, setUserCredentials }}>
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
