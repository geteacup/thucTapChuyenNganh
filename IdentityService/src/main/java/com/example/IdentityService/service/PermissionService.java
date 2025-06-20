package com.example.IdentityService.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.IdentityService.dto.request.PermissionRequest;
import com.example.IdentityService.dto.response.PermissionResponse;
import com.example.IdentityService.entity.Permission;
import com.example.IdentityService.mapper.PermissionMapper;
import com.example.IdentityService.repository.PermissionRepository;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Data
@Service
@Slf4j
public class PermissionService {
    private final PermissionMapper permissionMapper;
    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionMapper permissionMapper, PermissionRepository permissionRepository) {
        this.permissionMapper = permissionMapper;
        this.permissionRepository = permissionRepository;
    }

    public PermissionResponse updatePermission(String permissionID, PermissionRequest permissionRequest) {
        Permission permission = permissionRepository.findById(permissionID)
                .orElseThrow(()-> new RuntimeException("sometime"));
        permissionMapper.updatePermission(permission, permissionRequest);
        permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }
    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public PermissionResponse getById(String permissionID) {
        return permissionMapper.toPermissionResponse(permissionRepository.findByPermissionName(permissionID));
    }
    public void delete(String id) {
        permissionRepository.deleteById(id);
    }

    public void deleteAll() {
        permissionRepository.deleteAll();
    }

    public List<PermissionResponse> findAll() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }
}
