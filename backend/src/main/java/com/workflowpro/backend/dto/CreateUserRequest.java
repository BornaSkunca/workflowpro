package com.workflowpro.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequest {

    @NotBlank (message="Username is required!")
    @Size (min=3,max=20)
    private String username;

    @NotBlank (message = "Emial is required!")
    @Email (message = "Invalid email format!")
    private String email;

    @NotBlank (message = "Password is required!")
    @Size (min=6,message = "Password must have at least 6 characters!")
    private String password;

    @NotBlank (message = "Role is required!")
    private String role;
}
