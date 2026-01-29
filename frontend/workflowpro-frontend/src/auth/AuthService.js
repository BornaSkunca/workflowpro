import api from "../api/axios";

export const login = async (username, password)=>{
    const res=await api.post("/auth/login",{username,password});
    localStorage.setItem("token",res.data.token);
    return res.data;
};

export const logout= ()=>{
    localStorage.removeItem("token");
};

export const isAuthenticated=()=>{
    return !!localStorage.getItem("token");
};