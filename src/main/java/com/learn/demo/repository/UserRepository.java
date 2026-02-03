package com.learn.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.learn.demo.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    boolean existsByEmail(String email);

    @Query("Select u from UserEntity u where lower(u.name) Like lower(concat('%', :name, '%'))")
    Page<UserEntity> findByName(String name, Pageable pageable);
}
