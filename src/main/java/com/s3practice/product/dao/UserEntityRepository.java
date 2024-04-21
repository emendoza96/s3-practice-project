package com.s3practice.product.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s3practice.product.model.UserEntity;


public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {

    public Optional<UserEntity> findByUsername(String username);
    public Optional<UserEntity> findByEmail(String email);

}
