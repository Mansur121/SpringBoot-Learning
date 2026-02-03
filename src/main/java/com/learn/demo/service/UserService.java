package com.learn.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.learn.demo.exception.UserAlreadyExistsException;
import com.learn.demo.exception.UserNotFoundException;
import com.learn.demo.model.UserEntity;
import com.learn.demo.repository.UserRepository;
import org.springframework.data.domain.*;
import jakarta.transaction.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(UserEntity user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("User already exists with email: " + user.getEmail());
        }
        return userRepository.save(user);
    }

    public UserEntity getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    public Page<UserEntity> getAllUsers(int page, int size, String sortBy,
            String sortDirection) {
        Sort sort = sortDirection
                .equalsIgnoreCase(Sort.Direction.ASC.name())
                        ? Sort.by(sortBy).ascending()
                        : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size,
                sort);
        return userRepository.findAll(pageable);
    }

    @Transactional
    public UserEntity updateUser(int id, UserEntity user) {
        Optional<UserEntity> existingOptional = userRepository.findById(id);
        if (existingOptional.isPresent()) {
            UserEntity existing = existingOptional.get();
            existing.setName(user.getName());
            existing.setEmail(user.getEmail());
            return userRepository.save(existing);
        }
        return null;
    }

    public boolean deleteUserById(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Page<UserEntity> findByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findByName(name, pageable);
    }

}
