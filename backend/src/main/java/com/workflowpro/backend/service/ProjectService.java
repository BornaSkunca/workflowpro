package com.workflowpro.backend.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.workflowpro.backend.dto.CreateProjectRequest;
import com.workflowpro.backend.dto.ProjectResponse;
import com.workflowpro.backend.model.Project;
import com.workflowpro.backend.model.User;
import com.workflowpro.backend.repository.ProjectRepository;
import com.workflowpro.backend.repository.UserRepository;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository,UserRepository userRepository){
        this.userRepository=userRepository;
        this.projectRepository=projectRepository;
    }

    public ProjectResponse createProject(CreateProjectRequest request){
        Project project=new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());

        if(request.getMemberIds()!=null&&!request.getMemberIds().isEmpty()){
            Set<User> members=request.getMemberIds().stream()
            .map(id->userRepository
            .findById(id).orElseThrow(()->new RuntimeException("User not found "+id)))
            .collect(Collectors.toSet());
            
            project.setMembers(members);
        }

        Project saved=projectRepository.save(project);
        return mapToResponse(saved);
    }

    public List<ProjectResponse> getAllProjects(){
        return projectRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public List<ProjectResponse> getProjectsForUser(String username){
        User user = userRepository.findByUsername(username)
        .orElseThrow(()->new RuntimeException("User not found!"));

        return projectRepository.findByMembersContaining(user)
        .stream().map(this::mapToResponse).toList();
    }

    private ProjectResponse mapToResponse(Project project){
        Set<String> members = project.getMembers().stream().map(User::getUsername)
        .collect(Collectors.toSet());

        return new ProjectResponse(project.getId(), project.getName(), project.getDescription(), members);
    }

}
