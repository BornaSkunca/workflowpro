import api from "axios";

export const fetchProjects= async ()=>{
    const res= await api.get("/Projects");
    return res.data;
};

export const createProject= async (project)=>{
    const res= await api.post("/Projects",project);
    return res.data;
}