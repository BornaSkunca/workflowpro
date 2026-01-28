package com.workflowpro.backend.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workflowpro.backend.dto.CommentResponse;
import com.workflowpro.backend.dto.CreateCommentRequest;
import com.workflowpro.backend.service.CommentService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/tasks/{taskId}/comments")
@CrossOrigin(origins = "*")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService=commentService;
    }

    @PostMapping
    public CommentResponse addComment(@PathVariable Long taskId, @Valid 
        @RequestBody CreateCommentRequest request, Authentication authentication) {
        return commentService.addComment(taskId, request, authentication.getName());
    }

    @GetMapping
    public List<CommentResponse> getComments(@PathVariable Long taskId) {
        return commentService.getCommentsForTask(taskId);
    }
    
    
}
