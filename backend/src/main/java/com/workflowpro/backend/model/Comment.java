package com.workflowpro.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    private LocalDateTime createdAt;

    @ManyToOne
    private User author;

    @ManyToOne
    private Task task;
}
