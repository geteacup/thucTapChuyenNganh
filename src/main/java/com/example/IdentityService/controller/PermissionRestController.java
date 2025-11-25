package com.example.IdentityService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.IdentityService.dto.request.ApiResponse;
import com.example.IdentityService.dto.request.PermissionRequest;
import com.example.IdentityService.dto.response.PermissionResponse;
import com.example.IdentityService.service.PermissionService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/permissions")
@Slf4j
public class PermissionRestController {

    private final PermissionService permissionService;
    @Autowired
    PermissionRestController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponse<PermissionRequest> create(@RequestBody PermissionRequest permissionRequest) {
        permissionService.create(permissionRequest);
        return ApiResponse.<PermissionRequest>builder()
                .result(permissionRequest)
                .build();
    }
    @PutMapping("/{permissionId}")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponse<PermissionResponse> update(@PathVariable("permissionId") String permissionId, @RequestBody PermissionRequest permissionRequest) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.updatePermission(permissionId, permissionRequest))
                .build();
    }
    @GetMapping()
    ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.findAll())
                .build();
    }
    @GetMapping("/{permissionId}")
    ApiResponse<PermissionResponse> get(@PathVariable("permissionId") String permissionId) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.getById(permissionId))
                .build();
    }
    @DeleteMapping("/{permissionName}")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponse<Void> delete(@PathVariable("permissionName") String permissionName) {
        permissionService.delete(permissionName);
        return ApiResponse.<Void>builder().build();
    }

    @DeleteMapping()
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponse<Void> deleteAll() {
        permissionService.deleteAll();
        return ApiResponse.<Void>builder().build();
    }
}
