package com.example.IdentityService.dto.response;

import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleResponse {
    private String roleName;
    private String description;
    private Set<PermissionResponse> permissionSet;
}
