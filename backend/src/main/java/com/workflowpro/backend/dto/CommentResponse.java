package com.workflowpro.backend.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private String content;
    private String author;
    private LocalDateTime createdAt;
}
