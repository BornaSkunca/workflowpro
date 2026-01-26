package com.workflowpro.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workflowpro.backend.dto.LoginRequest;
import com.workflowpro.backend.dto.LoginResponse;
import com.workflowpro.backend.model.User;
import com.workflowpro.backend.security.JwtUtil;
import com.workflowpro.backend.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService,JwtUtil jwtUtil){
        this.userService=userService;
        this.jwtUtil=jwtUtil;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        boolean success= userService.login(request.getUsername(),request.getPassword());

        if (!success) {
            throw new RuntimeException("Invalid username or password!");
        }

        User user=userService.findByUsername(request.getUsername());
        String token= jwtUtil.generateToken(user.getUsername(), user.getRole().getName());

        return new LoginResponse(token);
    }
    
}
