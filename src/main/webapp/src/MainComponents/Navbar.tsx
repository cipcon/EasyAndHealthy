import { Link } from "react-router-dom";
import { useUserContext } from "../Contexts/context";
import { Logout } from "../User/Logout";



export const Navbar = () => {
    const { userCredentials } = useUserContext();

    return (
        <div className="">
            <nav className="navbar sticky-top navbar-expand-lg " >
                <div className="container-fluid"  >
                    <div className="collapse navbar-collapse" >
                        <a className="navbar-brand" href="/">
                            <img src="favicon.png" alt="Easy and healthy" width="40" height="40" />
                        </a>
                        <a className="navbar-brand" href="/">
                            <img src="logo.png" alt="Easy and healthy" width="60" height="40" style={{ marginLeft: -16 }} />
                        </a>
                        <Link to={'/'} className=' nav-link '>Home</Link>
                        {/* If the user is logged in, then no register and login links */}
                        {userCredentials.name === '' ?
                            <>
                                <Link to={'/register'} className=' nav-link '>Register</Link>
                                <Link to={'/login'} className=' nav-link '>Login</Link>
                            </>
                            : ''
                        }
                        <Link to={'/profile'} className=' nav-link '>Profile</Link>
                        <Link to={'/allRecipes'} className=' nav-link '>Recipes</Link>
                        <Logout />
                    </div>
                </div>
            </nav>
        </div>
    )

}