package com.workflowpro.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workflowpro.backend.dto.LoginRequest;
import com.workflowpro.backend.dto.LoginResponse;
import com.workflowpro.backend.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        boolean success= userService.login(request.getUsername(),request.getPassword());

        if (!success) {
            throw new RuntimeException("Invalid username or password!");
        }

        return new LoginResponse("Login successful!");
    }
    
}
