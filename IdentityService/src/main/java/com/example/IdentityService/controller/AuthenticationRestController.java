package com.example.IdentityService.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.IdentityService.dto.request.*;
import com.example.IdentityService.dto.response.AuthenticationResponse;
import com.example.IdentityService.dto.response.IntrospectResponse;
import com.example.IdentityService.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;

import lombok.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationRestController {

    private final AuthenticationService authenticationService;
    @Autowired
    public AuthenticationRestController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        var result = authenticationService.authenticate(authenticationRequest);
        System.out.println(result);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest introspectRequest)
            throws ParseException, JOSEException {

        return ApiResponse.<IntrospectResponse>builder()
                .code(200)
                .result(authenticationService.introspect(introspectRequest))
                .build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> introspect(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {

        return ApiResponse.<AuthenticationResponse>builder()
                .code(200)
                .result(authenticationService.refreshToken(request))
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().build();
    }
}
