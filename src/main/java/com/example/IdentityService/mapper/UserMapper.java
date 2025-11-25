package com.example.IdentityService.mapper;

import org.mapstruct.*;

import com.example.IdentityService.dto.request.UserCreationRequest;
import com.example.IdentityService.dto.request.UserUpdateRequest;
import com.example.IdentityService.dto.response.UserResponse;
import com.example.IdentityService.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", ignore = true)
    User toUser(UserCreationRequest userCreationRequest);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);
}
