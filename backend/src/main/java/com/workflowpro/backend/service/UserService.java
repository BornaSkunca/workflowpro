package com.workflowpro.backend.service;

import org.springframework.stereotype.Service;

import com.workflowpro.backend.model.Role;
import com.workflowpro.backend.model.User;
import com.workflowpro.backend.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, RoleService roleService){
        this.userRepository=userRepository;
        this.roleService=roleService;
    }

    public User createUser(String username,String email,String password,String roleName){
        Role role=roleService.createRoleIfNotExists(roleName);

        User user=new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        return userRepository.save(user);
    }

}
