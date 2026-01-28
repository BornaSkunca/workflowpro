package com.workflowpro.backend.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTaskRequest {
    @NotBlank
    private String title;

    private String description;

    @NotNull
    private Long projectId;

    private Long assignedUserId;

    private LocalDate dueDate;
}
