package com.example.IdentityService.mapper;

import org.mapstruct.Mapper;

import com.example.IdentityService.dto.request.PermissionRequest;
import com.example.IdentityService.dto.response.PermissionResponse;
import com.example.IdentityService.entity.Permission;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest permissionRequest);

    PermissionResponse toPermissionResponse(Permission permission);
    void updatePermission(@MappingTarget Permission permission, PermissionRequest permissionRequest);
}
