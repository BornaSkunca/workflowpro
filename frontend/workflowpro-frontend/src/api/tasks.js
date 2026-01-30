import api from "./axios";

export const fetchTasksByProject= async (projectId)=>{
    const res= await api.get(`/tasks/project/${projectId}`);
    return res.data;
};

export const createTask= async (task)=>{
    const res = await api.post("/tasks",task);
    return res.data;
};

export const updateTask= async (taskId,data)=>{
    const res= await api.put(`/tasks/${taskId}`,data);
    return res.data;
}