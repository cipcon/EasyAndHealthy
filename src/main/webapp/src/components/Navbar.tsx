import { Link } from "react-router-dom";
import { useUserContext } from "../Contexts/Context";
import { LogoutButton } from "../User/Components/LogoutButton";



export const Navbar = () => {
    const { userCredentials } = useUserContext();

    return (
        <>
            <nav className="navbar sticky-top navbar-expand-lg " >
                <div className="container-fluid"  >
                    <div className="collapse navbar-collapse gap-3" >
                        <a href="/" className="logo">
                            <img src="logoCompleted.png" alt="Easy and healthy Logo" width={100} height={45} style={{ borderRadius: 10 }} />
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

                        {/* No Profile Link, if no user */}
                        {
                            userCredentials.token === null ?
                                '' :
                                <>
                                    <Link to={'/profile'} className="nav-link">Profile</Link>
                                    <Link to={'/favoriteRecipes'} className="nav-link">Favorite</Link>
                                    <Link to={'/ingredients'} className="nav-link">Ingredients</Link>
                                    <Link to={"editOwnRecipes"} className="nav-link">Own Recipes</Link>
                                    <LogoutButton />
                                </>

                        }

                    </div>
                </div>
            </nav>
        </>
    )

}