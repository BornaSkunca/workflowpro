package com.workflowpro.backend.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateProjectRequest {

    @NotBlank
    private String name;

    private String description;

    private Set<Long> memberIds;
}
