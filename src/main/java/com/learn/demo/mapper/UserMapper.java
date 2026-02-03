package com.learn.demo.mapper;

import com.learn.demo.dto.CreateUserRequest;
import com.learn.demo.dto.UpdateUserRequest;
import com.learn.demo.dto.UserResponse;
import com.learn.demo.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(CreateUserRequest request) {
        UserEntity user = new UserEntity();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        return user;
    }

    public UserEntity toEntity(UpdateUserRequest request) {
        UserEntity user = new UserEntity();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        return user;
    }

    public UserResponse toResponse(UserEntity user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}
