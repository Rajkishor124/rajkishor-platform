package com.rajkishor.platform.config;

import com.rajkishor.platform.user.entity.Role;
import com.rajkishor.platform.user.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepo) {
        return args -> {
            if (roleRepo.findByName("USER").isEmpty()) {
                roleRepo.save(new Role("USER"));
            }
            if (roleRepo.findByName("ADMIN").isEmpty()) {
                roleRepo.save(new Role("ADMIN"));
            }
        };
    }
}
