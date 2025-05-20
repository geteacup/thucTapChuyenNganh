package com.example.IdentityService.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.IdentityService.dto.request.UserCreationRequest;
import com.example.IdentityService.dto.request.UserUpdateRequest;
import com.example.IdentityService.dto.response.UserResponse;
import com.example.IdentityService.entity.User;
import com.example.IdentityService.exception.AppException;
import com.example.IdentityService.exception.ErrorCode;
import com.example.IdentityService.mapper.RoleMapper;
import com.example.IdentityService.mapper.UserMapper;
import com.example.IdentityService.repository.RoleRepository;
import com.example.IdentityService.repository.UserRepository;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Data
@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;



    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository, RoleMapper roleMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;

    }

    public UserResponse createUser(UserCreationRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        if(request.getRoles() != null) {

            var roles = roleRepository.findAllById(request.getRoles());
            user.setRoles(new HashSet<>(roles));

        }
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public boolean passwordMatches(String password, String hashedPassword) {
        System.out.println(passwordEncoder.matches(password, hashedPassword));
        return passwordEncoder.matches(password, hashedPassword);
    }

    public UserResponse getMyInfo(String username) {
        Optional<User> user = userRepository.findByUsername(
                username);
        return userMapper.toUserResponse(user.get());
    }

    public UserResponse updateUser(String id, UserUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUser(user, request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        userRepository.save(user);
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found!!")));
    }

    //    @PreAuthorize("hasRole('ADMIN')")
//    @PreAuthorize("hasAuthority('READ_DATA')")
    public List<UserResponse> getUsers() {
        log.info("getUsers()");
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();

        userResponses =
                users.stream().map(user -> userMapper.toUserResponse(user)).collect(Collectors.toList());

        return userResponses;
    }

    public void deleteUsers() {
        userRepository.deleteAll();
    }

//    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUser(String id) {
        log.info("getUser({})", id);
        UserResponse userResponse = new UserResponse();
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        userResponse = userMapper.toUserResponse(user);
        return userResponse;
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
