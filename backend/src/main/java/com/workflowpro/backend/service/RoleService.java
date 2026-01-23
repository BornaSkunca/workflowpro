package com.workflowpro.backend.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.workflowpro.backend.model.Role;
import com.workflowpro.backend.repository.RoleRepository;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository){
        this.roleRepository=roleRepository;
    }

    public Role createRoleIfNotExists(String roleName){
        Optional<Role> existing=roleRepository.findByName(roleName);
        if(existing.isPresent()){
            return existing.get();
        }

        Role role=new Role();
        role.setName(roleName);
        return roleRepository.save(role);
    }
}
