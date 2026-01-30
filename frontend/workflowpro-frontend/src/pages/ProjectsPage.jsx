import { useState,useEffect } from "react";
import {fetchProjects, createProject} from "../api/projects";

export default function ProjectsPage(){
    const [projects,setProjects]=useState([]);
    const [name,setName]=useState("");
    const [description , setDescription]=useState("");

    const loadProjects=async ()=>{
        const data=await fetchProjects();
        setProjects(data);
    };

    useEffect(()=>{
        loadProjects();
    },[]);

    const handleCreate = async (e)=>{
        e.preventDefault();

        await createProject({name,description});
        setName("");
        setDescription("");
        loadProjects();
    };

    return(
        <div>
            <h2>Projects</h2>
            <form onSubmit={handleCreate}>
                <input placeholder="Project Name" value={name} 
                onChange={(e)=>setName(e.target.value)}/>
                <input placeholder="Description" value={description}
                onChange={(e)=>setDescription(e.target.value)}/>
                <button>Create project</button>
            </form>

            <hr/>

            <ul>
                {projects.map((p)=>{
                    <li key={p.id}>
                        <strong>{p.name}</strong> - {p.description}
                    </li>
                })}
            </ul>

        </div>
    );
};