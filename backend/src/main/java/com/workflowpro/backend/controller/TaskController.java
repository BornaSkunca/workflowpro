package com.workflowpro.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.workflowpro.backend.dto.CreateTaskRequest;
import com.workflowpro.backend.dto.TaskResponse;
import com.workflowpro.backend.dto.UpdateTaskRequest;
import com.workflowpro.backend.service.TaskService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService=taskService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public TaskResponse createTask(@Valid @RequestBody CreateTaskRequest request) {
        
        return taskService.createTask(request);
    }

    @GetMapping("/project/{projectId}")
    public List<TaskResponse> getTasksByProject(@PathVariable Long projectId){
        return taskService.getTasksByProject(projectId);
    }

    @PutMapping("/{taskId}")
    public TaskResponse updateTask(@PathVariable Long taskId, 
        @RequestBody UpdateTaskRequest request, Authentication authentication) {
        String username= authentication.getName();

        boolean isAdminOrManager= authentication.getAuthorities().stream()
        .anyMatch(a-> a.getAuthority().equals("ROLE_ADMIN")||
        a.getAuthority().equals("ROLE_MANAGER"));

        return taskService.updateTask(taskId, request, username, isAdminOrManager);
    }
    
}
