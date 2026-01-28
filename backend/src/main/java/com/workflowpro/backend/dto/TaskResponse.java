package com.workflowpro.backend.dto;

import java.time.LocalDate;

import com.workflowpro.backend.model.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private String projectName;
    private String assignedTo;
    private LocalDate dueDate;
}
