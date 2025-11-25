package com.example.IdentityService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    GLOBAL_EXCEPTION(666, "MR PICKLES", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1001, "User already exists", HttpStatus.BAD_REQUEST),
    INVALID_KEY(1002, "Invalid message key", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_DOES_NOT_EXIST(1005, "user does not exist", HttpStatus.NOT_FOUND),
    AUTHENTICATION_FAILED(1006, "authentication failed", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "AccessDenied", HttpStatus.FORBIDDEN),
    INVALID_BIRTH_DATE(1008, "Your age is at least {min}", HttpStatus.BAD_REQUEST),
    ;

    private int code;
    private String message;
    private HttpStatusCode httpStatus;

    ErrorCode(int code, String message, HttpStatusCode httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
