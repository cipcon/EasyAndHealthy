import { Link } from "react-router-dom";


export const Navbar = () => {
    return (
        <div >
            <nav className="navbar sticky-top navbar-expand-lg " > 
                <div className="container-fluid"  >
                    <div className="collapse navbar-collapse" id="navbarNav" >
                        <a className="navbar-brand" href="/">
                            <img src="1.png" alt="Easy and healthy" width="40" height="40" />
                        </a>
                        <a className="navbar-brand" href="/">
                            <img src="2.png" alt="Easy and healthy" width="60" height="40" style={{ marginLeft: -16}} />
                        </a>
                        <Link to={'/'} className='margin-left nav-link '>Home</Link>
                        <Link to={'/register'} className='margin-left nav-link '>Register</Link>
                        <Link to={'/login'} className='margin-left nav-link '>Login</Link>
                        <Link to={'/profile'} className='margin-left nav-link '>Profile</Link>
                        <Link to={'/allRecipes'} className='margin-left nav-link '>Recipes</Link>
                    </div>
                </div>
            </nav>
        </div>
    )

}