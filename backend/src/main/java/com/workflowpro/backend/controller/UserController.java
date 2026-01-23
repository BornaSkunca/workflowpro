package com.workflowpro.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workflowpro.backend.dto.CreateUserRequest;
import com.workflowpro.backend.model.User;
import com.workflowpro.backend.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping ("api/users")
@CrossOrigin (origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping
    public User createUser(@RequestBody CreateUserRequest request){
        return userService.createUser(request.getUsername(), request.getEmail(), request.getPassword(), request.getRole());
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    
    
}
