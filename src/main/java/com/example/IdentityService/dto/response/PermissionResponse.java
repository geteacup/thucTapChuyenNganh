package com.example.IdentityService.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionResponse {
    private String permissionName;
    private String description;
}
