package com.workflowpro.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToMany
    @JoinTable (name="project_users", joinColumns = @JoinColumn(name= "project_id"),inverseJoinColumns = @JoinColumn(name="user_id"))
    private Set<User> members;


}
