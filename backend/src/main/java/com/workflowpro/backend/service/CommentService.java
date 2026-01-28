package com.workflowpro.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.workflowpro.backend.dto.CommentResponse;
import com.workflowpro.backend.dto.CreateCommentRequest;
import com.workflowpro.backend.model.Comment;
import com.workflowpro.backend.model.Task;
import com.workflowpro.backend.model.User;
import com.workflowpro.backend.repository.CommentRepository;
import com.workflowpro.backend.repository.TaskRepository;
import com.workflowpro.backend.repository.UserRepository;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final NotificationService notificationService;

    public CommentService(CommentRepository commentRepository, 
        UserRepository userRepository, TaskRepository taskRepository,NotificationService notificationService){
        this.commentRepository=commentRepository;
        this.taskRepository=taskRepository;
        this.userRepository=userRepository;
        this.notificationService=notificationService;
    }

    public CommentResponse addComment(Long taskId, CreateCommentRequest request,
        String username){
            Task task=taskRepository.findById(taskId)
            .orElseThrow(()->new RuntimeException("Task not found!"));

            User user=userRepository.findByUsername(username)
            .orElseThrow(()->new RuntimeException("User not found!"));

            if(!task.getProject().getMembers().contains(user)){
                throw new AccessDeniedException("You are not a member of this project!");
            }

            Comment comment=new Comment();
            comment.setContent(request.getContent());
            comment.setAuthor(user);
            comment.setTask(task);
            comment.setCreatedAt(LocalDateTime.now());

            Comment saved=commentRepository.save(comment);

            User assignee=task.getAssignedTo();
            if(assignee!=null&&!assignee.getUsername().equals(user.getUsername())){
                notificationService.createNotification(assignee, 
                    "New comment on your task '"+task.getTitle()+"' by "+user.getUsername());
            }

            return new CommentResponse(saved.getId(), saved.getContent(), 
            saved.getAuthor().getUsername(), saved.getCreatedAt());

    }

    public List<CommentResponse> getCommentsForTask(Long taskId){
        return commentRepository.findByTaskIdOrderByCreatedAtAsc(taskId).stream()
        .map(c->new CommentResponse(c.getId(), c.getContent(), 
        c.getAuthor().getUsername(), c.getCreatedAt())).toList();
    }
}
