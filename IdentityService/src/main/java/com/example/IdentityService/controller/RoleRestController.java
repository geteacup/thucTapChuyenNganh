package com.example.IdentityService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.IdentityService.dto.request.ApiResponse;
import com.example.IdentityService.dto.request.RoleRequest;
import com.example.IdentityService.dto.response.RoleResponse;
import com.example.IdentityService.service.RoleService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/roles")
@Slf4j
public class RoleRestController {
    private final RoleService roleService;

    @Autowired
    public RoleRestController(RoleService roleService) {
        this.roleService = roleService;
    }
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<RoleResponse> addRole(@RequestBody RoleRequest request) {
        RoleResponse result = roleService.createRole(request);
        return ApiResponse.<RoleResponse>builder().result(result).build();
    }
    @PutMapping("/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<RoleResponse> updateRole(@PathVariable String roleId, @RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder().result(roleService.updateRole(roleId, request)).build();
    }
    @GetMapping("/{roleId}")
    public ApiResponse<RoleResponse> getRole(@PathVariable String roleId) {
        return ApiResponse.<RoleResponse>builder().result(roleService.getRoleById(roleId))  .build();
    }
    @GetMapping()
    public ApiResponse<List<RoleResponse>> findAll() {
        List<RoleResponse> roles = roleService.findAllRoles();
        return ApiResponse.<List<RoleResponse>>builder().result(roles).build();
    }

    @DeleteMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteRoles() {
        roleService.deleteAllRoles();
        return ApiResponse.<Void>builder().build();
    }

    @DeleteMapping("/{roleName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteRole(@PathVariable String roleName) {
        roleService.deleteRole(roleName);
        return ApiResponse.<Void>builder().build();
    }
}
