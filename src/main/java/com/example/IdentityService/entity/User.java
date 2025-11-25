package com.example.IdentityService.entity;

import java.time.LocalDate;
import java.util.Set;

import com.example.IdentityService.validator.BirthDateConstraint;
import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String sdt;
    @ManyToMany
    private Set<Role> roles;
}
