package com.example.IdentityService.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.IdentityService.dto.request.AuthenticationRequest;
import com.example.IdentityService.dto.request.IntrospectRequest;
import com.example.IdentityService.dto.request.LogoutRequest;
import com.example.IdentityService.dto.request.RefreshRequest;
import com.example.IdentityService.dto.response.AuthenticationResponse;
import com.example.IdentityService.dto.response.IntrospectResponse;
import com.example.IdentityService.entity.InvalidatedToken;
import com.example.IdentityService.entity.User;
import com.example.IdentityService.exception.AppException;
import com.example.IdentityService.exception.ErrorCode;
import com.example.IdentityService.repository.InvalidatedTokenRepository;
import com.example.IdentityService.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Data
@Service
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final InvalidatedTokenRepository invalidatedTokenRepository;



    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @Value("${jwt.valid-duration}")
    protected int VALID_DURATION;

    @Value("${jwt.refreshable-duration}")
    protected int REFRESHABLE_DURATION;

    @Autowired
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserService userService
    , InvalidatedTokenRepository invalidatedTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.invalidatedTokenRepository = invalidatedTokenRepository;
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws AppException, JOSEException, ParseException {
        var token = request.getToken();

        try {
            verifyToken(token, false);

            return IntrospectResponse.builder().valid(true).build();
        } catch (AppException e) {
            return IntrospectResponse.builder().valid(false).build();
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        var user = userRepository
                .findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_DOES_NOT_EXIST));
        System.out.println(authenticationRequest.getPassword());
        System.out.println(user.getPassword());
        boolean authenticated = userService.passwordMatches(authenticationRequest.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.AUTHENTICATION_FAILED);

        } else {
            try {
                return AuthenticationResponse.builder()
                        .token(tokenGenerate(user))
                        .authenticated(authenticated)
                        .build();
            } catch (JOSEException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException {

        try {
            var signToken = verifyToken(request.getToken(), true);
            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken =
                    InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();

            invalidatedTokenRepository.save(invalidatedToken);
        } catch (AppException e) {
            log.info("Token already expired");
        }
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws ParseException, JOSEException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY);
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expireTime = (isRefresh)
                ? new Date(signedJWT
                        .getJWTClaimsSet()
                        .getIssueTime()
                        .toInstant()
                        .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                        .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean verified = signedJWT.verify(verifier);
        String id = signedJWT.getJWTClaimsSet().getJWTID();
        if (!(verified && expireTime.after(new Date()))) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        if (invalidatedTokenRepository.existsById(id)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        return signedJWT;
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signJWT = verifyToken(request.getToken(), true);
        var jit = signJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signJWT.getJWTClaimsSet().getExpirationTime();
        InvalidatedToken invalidatedToken =
                InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();

        invalidatedTokenRepository.save(invalidatedToken);
        var username = signJWT.getJWTClaimsSet().getSubject();

        var user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_DOES_NOT_EXIST));

        return AuthenticationResponse.builder()
                .token(tokenGenerate(user))
                .authenticated(true)
                .build();
    }

    public String tokenGenerate(User user) throws JOSEException {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        String username = user.getUsername();

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("somedieyoung")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);
        jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
        return jwsObject.serialize();
    }

    private List<String> buildScope(User user) {
        List<String> scopes = new ArrayList<>();
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                scopes.add("ROLE_" + role.getRoleName());
                if (!CollectionUtils.isEmpty(role.getPermissionSet())) {
                    role.getPermissionSet().forEach(permission -> {
                        scopes.add(permission.getPermissionName());
                    });
                }
            });
        }
        return scopes;
    }
    ;
}
