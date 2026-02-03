package com.learn.demo.controller;

import com.learn.demo.dto.CreateUserRequest;
import com.learn.demo.dto.UpdateUserRequest;
import com.learn.demo.dto.UserResponse;
import com.learn.demo.mapper.UserMapper;
import com.learn.demo.model.UserEntity;
import com.learn.demo.service.UserService;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userservice;
    private final UserMapper mapper;

    public UserController(UserService userservice, UserMapper mapper) {
        this.userservice = userservice;
        this.mapper = mapper;
    }

    @GetMapping("/users")
    public Page<UserResponse> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return userservice.getAllUsers(page, size, sortBy, sortDir)
                .map(mapper::toResponse);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable int id) {
        UserEntity user = userservice.getUserById(id);
        if (user != null) {
            return new ResponseEntity<>(mapper.toResponse(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
        UserEntity userEntity = mapper.toEntity(request);
        UserEntity createdUser = userservice.createUser(userEntity);
        return new ResponseEntity<>(mapper.toResponse(createdUser), HttpStatus.CREATED);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        boolean deleted = userservice.deleteUserById(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable int id, @RequestBody UpdateUserRequest request) {
        UserEntity userEntity = mapper.toEntity(request);
        UserEntity updatedUser = userservice.updateUser(id, userEntity);
        if (updatedUser != null) {
            return new ResponseEntity<>(mapper.toResponse(updatedUser), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("users/search")
    public Page<UserEntity> searchUsersByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return userservice.findByName(name, page, size);
    }

}
