package com.workflowpro.backend.service;

import org.springframework.security.access.AccessDeniedException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.workflowpro.backend.dto.CreateTaskRequest;
import com.workflowpro.backend.dto.TaskResponse;
import com.workflowpro.backend.dto.UpdateTaskRequest;
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
    private final NotificationService notificationService;

    public TaskService(TaskRepository taskRepository,ProjectRepository projectRepository, 
        UserRepository userRepository, NotificationService notificationService){
        this.taskRepository=taskRepository;
        this.projectRepository=projectRepository;
        this.userRepository=userRepository;
        this.notificationService=notificationService;
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

        if(assignedUser!=null){
            notificationService.createNotification(assignedUser, 
                "You have been assigned a new task: "+saved.getTitle());
        }

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

    public TaskResponse updateTask(Long taskId, UpdateTaskRequest request, 
        String username, boolean isAdminOrManager ){
            Task task= taskRepository.findById(taskId)
            .orElseThrow(()->new RuntimeException("Task not found!"));

            if(!isAdminOrManager){
                if(task.getAssignedTo()==null||
                !task.getAssignedTo().getUsername().equals(username)){
                    throw new AccessDeniedException("You are not allowed to update this task!");
                }
            }

            if(request.getTitle()!=null){
                task.setTitle(request.getTitle());
            }

            if(request.getDescription()!=null){
                task.setDescription(request.getDescription());
            }

            if(request.getStatus()!=null){
                task.setStatus(request.getStatus());
            }

            if(request.getDueDate()!=null){
                task.setDueDate(request.getDueDate());
            }

            if(isAdminOrManager&&request.getAssignedUserId()!=null){
                User newAssignee=userRepository.findById(request.getAssignedUserId())
                .orElseThrow(()->new RuntimeException("User not found!"));
                task.setAssignedTo(newAssignee);

                notificationService.createNotification(newAssignee,
                     "You have been assigned to task: "+task.getTitle());
            }

            Task updated=taskRepository.save(task);
            return mapToResponse(updated);
        }
 

}
