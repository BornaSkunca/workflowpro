package com.workflowpro.backend.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workflowpro.backend.model.Notification;
import com.workflowpro.backend.model.User;
import com.workflowpro.backend.repository.UserRepository;
import com.workflowpro.backend.service.NotificationService;

import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    public NotificationController(NotificationService notificationService, UserRepository userRepository){
        this.notificationService=notificationService;
        this.userRepository=userRepository;
    }

    @GetMapping
    public List<Notification> getMyNotifications(Authentication authentication){
        User user=userRepository.findByUsername(authentication.getName())
        .orElseThrow(()->new RuntimeException("User not found!"));
        return notificationService.getUserNotifications(user);
    }

    @PutMapping("/{id}/read")
    public void markAsRead(@PathVariable Long id){
        notificationService.markAsRead(id);
    }

}
