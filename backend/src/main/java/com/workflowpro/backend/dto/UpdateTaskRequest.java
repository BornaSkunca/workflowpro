package com.workflowpro.backend.dto;

import java.time.LocalDate;

import com.workflowpro.backend.model.TaskStatus;

import lombok.Data;

@Data
public class UpdateTaskRequest {
    private String title;
    private String description;
    private TaskStatus status;
    private Long assignedUserId;
    private LocalDate dueDate;
}
