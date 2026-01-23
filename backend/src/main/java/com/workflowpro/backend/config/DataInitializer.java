package com.workflowpro.backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.workflowpro.backend.service.RoleService;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initRoles(RoleService roleService){
        return args->{
            roleService.createRoleIfNotExists("ADMIN");
            roleService.createRoleIfNotExists("MANAGER");
            roleService.createRoleIfNotExists("USER");
        };
    }
}
