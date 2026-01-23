package com.workflowpro.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.workflowpro.backend.dto.UserResponse;
import com.workflowpro.backend.model.Role;
import com.workflowpro.backend.model.User;
import com.workflowpro.backend.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleService roleService,PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.roleService=roleService;
        this.passwordEncoder=passwordEncoder;
    }

    public UserResponse createUser(String username,String email,String password,String roleName){
        Role role=roleService.createRoleIfNotExists(roleName);

        User user=new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        User saved=userRepository.save(user);

        return mapToResponse(saved);
    }

    public List<UserResponse> getAllUsers(){
        return userRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private UserResponse mapToResponse(User user){
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole().getName());
    }

    public boolean login(String username,String rawPassword){
        Optional<User> userOpt=userRepository.findByUsername(username);
        if (userOpt.isEmpty())return false;

        User user=userOpt.get();
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

}
