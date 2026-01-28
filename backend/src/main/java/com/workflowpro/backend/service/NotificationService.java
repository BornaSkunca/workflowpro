package com.workflowpro.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.workflowpro.backend.model.Notification;
import com.workflowpro.backend.model.User;
import com.workflowpro.backend.repository.NotificationRepository;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository){
        this.notificationRepository=notificationRepository;
    }

    public void createNotification(User user, String message){
        Notification notification= new Notification();
        notification.setRecipient(user);
        notification.setMessage(message);
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }

    public List<Notification> getUserNotifications(User user){
        return notificationRepository.findByRecipientOrderByCreatedAtDesc(user);
    }

    public void markAsRead(Long id){
        Notification n=notificationRepository.findById(id)
        .orElseThrow(()->new RuntimeException("Notification not found!"));
        n.setRead(true);
        notificationRepository.save(n);
    }
}
