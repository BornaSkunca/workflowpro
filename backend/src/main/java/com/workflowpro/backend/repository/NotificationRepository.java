package com.workflowpro.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workflowpro.backend.model.Notification;
import com.workflowpro.backend.model.User;

public interface NotificationRepository extends JpaRepository<Notification,Long>{
    List<Notification> findByRecipientOrderByCreatedAtDesc(User user);

    long countByRecipientAndReadFalse(User user);
        
    
}
