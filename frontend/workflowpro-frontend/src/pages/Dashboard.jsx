import { logout } from "../auth/AuthService";

export default function Dashboard(){
    return(
        <div>
            <h1>Workflowpro dashboard</h1>
            <button onClick={logout}>Logout</button>
        </div>
    );
}