package com.workflowpro.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.workflowpro.backend.dto.CreateTaskRequest;
import com.workflowpro.backend.dto.TaskResponse;
import com.workflowpro.backend.model.Project;
import com.workflowpro.backend.model.Task;
import com.workflowpro.backend.model.TaskStatus;
import com.workflowpro.backend.model.User;
import com.workflowpro.backend.repository.ProjectRepository;
import com.workflowpro.backend.repository.TaskRepository;
import com.workflowpro.backend.repository.UserRepository;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository,ProjectRepository projectRepository, UserRepository userRepository){
        this.taskRepository=taskRepository;
        this.projectRepository=projectRepository;
        this.userRepository=userRepository;
    }

    public TaskResponse createTask(CreateTaskRequest request){
        Project project= projectRepository.findById(request.getProjectId())
        .orElseThrow(()->new RuntimeException("Project not found!"));

        User assignedUser=null;

        if(request.getAssignedUserId()!=null){
            assignedUser=userRepository.findById(request.getAssignedUserId())
            .orElseThrow(()->new RuntimeException("User not found!"));
        }

        Task task= new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setProject(project);
        task.setAssignedTo(assignedUser);
        task.setDueDate(request.getDueDate());
        task.setStatus(TaskStatus.TODO);

        Task saved=taskRepository.save(task);
        return mapToResponse(saved);
    }

    public List<TaskResponse> getTasksByProject(Long projectId){
        return taskRepository.findByProjectId(projectId)
        .stream().map(this::mapToResponse).toList();
    }

    public List<TaskResponse> getTasksForUser(Long userId){
        return taskRepository.findByAssignedToId(userId)
        .stream().map(this::mapToResponse).toList();
    }

    private TaskResponse mapToResponse(Task task){
        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription()
        , task.getStatus(), task.getProject().getName(),
         task.getAssignedTo() != null ? task.getAssignedTo().getUsername():null,
          task.getDueDate());
    }

}
