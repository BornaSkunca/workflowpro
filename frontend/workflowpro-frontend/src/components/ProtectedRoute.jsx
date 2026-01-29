import { Navigate, Route } from "react-router-dom";
import {isAuthenticated} from "../auth/AuthService";

export default function ProtectedRoute({children}){
    if(!isAuthenticated()){
        return <Navigate to="/login"/>
    }
    return children;
}