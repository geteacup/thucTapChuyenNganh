package com.example.IdentityService.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.IdentityService.dto.request.RoleRequest;
import com.example.IdentityService.dto.response.RoleResponse;
import com.example.IdentityService.entity.Role;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissionSet", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

    @Mapping(target = "permissionSet", ignore = true)
    void updateRole(RoleRequest roleRequest,@MappingTarget Role role);
}
