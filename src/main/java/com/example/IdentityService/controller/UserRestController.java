package com.example.IdentityService.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import com.example.IdentityService.dto.request.ApiResponse;
import com.example.IdentityService.dto.request.UserCreationRequest;
import com.example.IdentityService.dto.request.UserUpdateRequest;
import com.example.IdentityService.dto.response.UserResponse;
import com.example.IdentityService.enums.Role;
import com.example.IdentityService.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserRestController {

    private final UserService userService;
    private final RestClient.Builder builder;

    @Autowired
    public UserRestController(UserService userService, RestClient.Builder builder) {
        this.userService = userService;
        this.builder = builder;
    }
    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<UserResponse>> getUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("User: " + authentication.getName());
        System.out.println("Authorities: " + authentication.getAuthorities());
        System.out.println("Authenticated: " + authentication.isAuthenticated());

        authentication.getAuthorities().forEach(role -> log.info(role.getAuthority()));

        log.info("Username : {}", Role.ADMIN.name());
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<UserResponse> getUser(@PathVariable String userId) {

        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .build();
    }

    @GetMapping("/myInfo")
    public ApiResponse<UserResponse> getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("User: " + authentication.getName());
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo(authentication.getName()))
                .build();
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUsers() {
        userService.deleteUsers();
    }
}
