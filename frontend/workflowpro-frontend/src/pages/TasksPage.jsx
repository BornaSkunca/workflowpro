import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import {fetchTasksByProject, createTask, updateTask} from "../api/tasks";

export default function TasksPage(){
    const {projectId}=useParams();
    const [tasks,setTasks]=useState([]);
    const [title,setTitle]=useState("");

    const loadTasks= async ()=>{
        const data=await fetchTasksByProject(projectId);
        setTasks(data);
    }

    useEffect(()=>{
        loadTasks();
    },[]);

    const handleCreate=async (e)=>{
        e.preventDefault();

        await createTask({title,projectId});
        setTitle("");
        loadTasks();
    };

    const handleStatusChange=async (taskId,status)=>{
        await updateTask(taskId,{status});
        loadTasks();
    };

    return(
        <div>
            <h2>Tasks</h2>

            <form onSubmit={handleCreate}>
                <input placeholder="New task title" value={title}
                onChange={(e)=>setTitle(e.target.value)}/>
                <button>Create</button>
            </form>

            <ul>
                {tasks.map((t)=>{
                    <li key={t.id}>
                        <strong>{t.title}</strong> - {t.status}
                        <br/>
                        <button onClick={()=>handleStatusChange(t.id,"TODO")}>TODO</button>
                        <button onClick={()=>handleStatusChange(t.id,"IN_PROGRESS")}>IN PROGRESS</button>
                        <button onClick={()=>handleStatusChange(t.id,"DONE")}>DONE</button>
                    </li>
                })}
            </ul>
        </div>
    );
}