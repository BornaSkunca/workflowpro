package com.workflowpro.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workflowpro.backend.model.Project;
import java.util.List;
import com.workflowpro.backend.model.User;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    List<Project> findByMembersContaining(User user);
}
