import { Link } from "react-router-dom";
import {logout} from "../auth/AuthService";

export default function Navbar(){
    return(
        <nav>
            <Link to="/">Dashboard</Link>|{" "}
            <Link to="/projects">Projects</Link>|{" "}
            <button onClick={logout}>Logout</button>
        </nav>
    );
}