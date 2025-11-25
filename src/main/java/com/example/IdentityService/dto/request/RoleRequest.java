package com.example.IdentityService.dto.request;

import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleRequest {
    private String roleName;
    private String description;
    private Set<String> permissionSet;
}
