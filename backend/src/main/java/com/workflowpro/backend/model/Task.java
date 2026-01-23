package com.workflowpro.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated (EnumType.STRING)
    private TaskStatus status;

    private LocalDate dueDate;

    @ManyToOne
    private Project project;

    @ManyToOne
    private User assignedTo;

}
