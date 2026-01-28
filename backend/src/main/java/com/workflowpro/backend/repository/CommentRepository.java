package com.workflowpro.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workflowpro.backend.model.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long>{
    List<Comment> findByTaskIdOrderByCreatedAtAsc(Long taskId);
}
