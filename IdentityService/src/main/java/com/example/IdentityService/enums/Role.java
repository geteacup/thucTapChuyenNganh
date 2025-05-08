package com.example.IdentityService.enums;

public enum Role {
    ADMIN,
    USER;

    public String toString() {
        return "ROLE_" + this.name();
    }
}
