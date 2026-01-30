import { logout } from "../auth/AuthService";
import Navbar from "../components/Navbar";

export default function Dashboard(){
    return(
        <div>
            <Navbar/>
            <h1>Dashboard</h1>
            <p>Welcome to WorkFlowPro</p>
        </div>
    );
}