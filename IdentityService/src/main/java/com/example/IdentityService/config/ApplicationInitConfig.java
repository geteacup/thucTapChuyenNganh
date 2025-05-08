package com.example.IdentityService.config;

import java.util.*;

import com.example.IdentityService.dto.request.UserCreationRequest;
import com.example.IdentityService.entity.Role;
import com.example.IdentityService.entity.User;
import com.example.IdentityService.repository.RoleRepository;
import com.example.IdentityService.service.UserService;
import jakarta.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.IdentityService.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
public class ApplicationInitConfig {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Autowired
    public ApplicationInitConfig(UserRepository userRepository, UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> createAdmin();
    }

    @Transactional
    public void createAdmin() {
        if (userRepository.findByUsername("admin").isEmpty()) {

//            List<String> listRoles = new ArrayList<>();
//            listRoles.add("ADMIN");
//            UserCreationRequest userCreationRequest = UserCreationRequest.builder()
//                    .username("admin")
//                    .password(passwordEncoder.encode("admin666"))
//                    .roles(new HashSet<String>(listRoles))
//                    .build();
//            userService.createUser(userCreationRequest);
//
//            listRoles = new ArrayList<>();
//            listRoles.add("ADMIN");
//
//            var roles = roleRepository.findAllById(listRoles);
//            for (Role role : roles) {
//                Hibernate.initialize(role.getPermissionSet());
//            }
//            User user = User.builder()
//                    .username("admin")
//                    .password(passwordEncoder.encode("admin666"))
//                    .roles(new HashSet<>(roles))
//                    .build();
//            userRepository.save(user);
//            log.info("ADMIN created");
//
          }
    }
}
