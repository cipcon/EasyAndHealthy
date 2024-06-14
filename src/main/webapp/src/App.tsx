import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import { Navbar } from './MainComponents/Navbar';
import { Home } from './Homepage/Home';
import { Login } from './User/Login';
import { Register } from './User/Register';
import { Profile } from './User/Profile';
import { Allrecipes } from './Recipes/AllRecipes';



function App() {

  return (
    <div className="App">
      <Router>
        <Navbar />
        <Routes>
          <Route path='/' element={<Home />}/>
          <Route path='/login' element={<Login />}/>
          <Route path='/register' element={<Register />}/>
          <Route path='/profile' element={<Profile />}/>
          <Route path='/allRecipes' element={<Allrecipes />} />
          <Route path='*' element={<h1>You are out of the page</h1>}/>
        </Routes>
      </Router>
    </div>
  );
}



export default App;
