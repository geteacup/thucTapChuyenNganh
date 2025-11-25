package com.example.IdentityService.dto.request;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.example.IdentityService.validator.BirthDateConstraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    @Size(min = 5, max = 20, message = "USERNAME_INVALID")
    private String password;

    @NotNull(message = "fuck")
    @Size(min = 8, max = 20, message = "PASSWORD_INVALID")
    private String firstName;

    private String lastName;

    @BirthDateConstraint(min = 18, message = "INVALID_BIRTH_DATE")
    private LocalDate birthDate;
    private String sdt;
    private Set<String> roles;
}
