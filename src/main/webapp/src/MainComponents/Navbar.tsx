import { Link } from "react-router-dom";
import { useUserContext } from "../Contexts/context";
import { LogoutButton } from "../User/LogoutButton";



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
                        {
                            userCredentials.token === null ?
                                <>
                                    <Link to={'/register'} className=' nav-link '>Register</Link>
                                    <Link to={'/login'} className=' nav-link '>Login</Link>
                                </>
                                : ''
                        }

                        <Link to={'/allRecipes'} className=' nav-link '>Recipes</Link>

                        {
                            userCredentials.token === null ?
                                '' :
                                <>
                                    <Link to={'/profile'} className=' nav-link '>Profile</Link>
                                    <LogoutButton />
                                </>

                        }

                    </div>
                </div>
            </nav>
        </div>
    )

}