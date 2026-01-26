package com.workflowpro.backend.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workflowpro.backend.dto.CreateProjectRequest;
import com.workflowpro.backend.dto.ProjectResponse;
import com.workflowpro.backend.service.ProjectService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/projects")
@CrossOrigin ("*")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService){
        this.projectService=projectService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ProjectResponse createProject(@Valid @RequestBody CreateProjectRequest request){
        return projectService.createProject(request);
    }

    @GetMapping
    public List<ProjectResponse> getProjects(Authentication authentication) {
        String username =authentication.getName();

        if(authentication.getAuthorities().stream()
            .anyMatch(a->a.getAuthority()
        .equals("ROLE_ADMIN")||a.getAuthority()
        .equals("ROLE_MANAGER"))){
            return projectService.getAllProjects();
        }else{
            return projectService.getProjectsForUser(username);
        }
    }
    
    
}
