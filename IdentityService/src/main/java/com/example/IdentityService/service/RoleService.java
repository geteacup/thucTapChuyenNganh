package com.example.IdentityService.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.IdentityService.dto.request.RoleRequest;
import com.example.IdentityService.dto.response.RoleResponse;
import com.example.IdentityService.entity.Role;
import com.example.IdentityService.mapper.PermissionMapper;
import com.example.IdentityService.mapper.RoleMapper;
import com.example.IdentityService.repository.PermissionRepository;
import com.example.IdentityService.repository.RoleRepository;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Data
@Service
@Slf4j
public class RoleService {
    private final PermissionMapper permissionMapper;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;

    public RoleService(PermissionMapper permissionMapper, RoleRepository roleRepository,
                       PermissionRepository permissionRepository, RoleMapper roleMapper) {
        this.permissionMapper = permissionMapper;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.roleMapper = roleMapper;
    }

    public RoleResponse createRole(RoleRequest roleRequest) {
        Role role = roleMapper.toRole(roleRequest);

        var permissionSet = permissionRepository.findAllById(roleRequest.getPermissionSet());
        role.setPermissionSet(new HashSet<>(permissionSet));
        role = roleRepository.save(role);
        RoleResponse roleResponse = roleMapper.toRoleResponse(role);
        roleResponse.setPermissionSet(new HashSet<>(permissionSet.stream()
                .map(permissionMapper::toPermissionResponse)
                .toList()));
        return roleMapper.toRoleResponse(role);
    }

    public RoleResponse getRoleById(String roleId){
        return roleMapper.toRoleResponse(roleRepository.findByRoleName(roleId));
    }
    public RoleResponse updateRole(String roleId, RoleRequest roleRequest) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(()-> new RuntimeException("something went wrong"));
        roleMapper.updateRole(roleRequest,role);
        var permissionSet = permissionRepository.findAllById(roleRequest.getPermissionSet());
        role.setPermissionSet(new HashSet<>(permissionSet));
        role = roleRepository.save(role);
        RoleResponse roleResponse = roleMapper.toRoleResponse(role);
        roleResponse.setPermissionSet(new HashSet<>(permissionSet.stream()
                .map(permissionMapper::toPermissionResponse)
                .toList()));
        return roleMapper.toRoleResponse(role);
    }
    public void deleteRole(String id) {
        roleRepository.deleteById(id);
    }

    public void deleteAllRoles() {
        roleRepository.deleteAll();
    }

    public List<RoleResponse> findAllRoles() {
        var roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::toRoleResponse).toList();
    }
}
