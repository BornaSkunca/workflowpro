package com.workflowpro.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workflowpro.backend.model.Task;

public interface TaskRepository extends JpaRepository<Task,Long>{
    List<Task> findByProjectId(Long projectId);
    List<Task> findByAssignedToId(Long userId);
}
