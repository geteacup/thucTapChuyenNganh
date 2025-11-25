package com.example.IdentityService.dto.request;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.example.IdentityService.validator.BirthDateConstraint;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreationRequest {
    @Size(min = 5, max = 20, message = "USERNAME_INVALID")
    private String username;

    @NotNull(message = "fuck")
    @Size(min = 8, max = 20, message = "PASSWORD_INVALID")
    private String password;

    private String firstName;
    private String lastName;

    @BirthDateConstraint(min = 16, message = "INVALID_BIRTH_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String sdt;
    private Set<String> roles;
}
