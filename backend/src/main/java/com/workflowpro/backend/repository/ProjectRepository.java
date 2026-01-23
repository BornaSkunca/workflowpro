package com.workflowpro.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workflowpro.backend.model.Project;

public interface ProjectRepository extends JpaRepository<Project,Long> {

}
