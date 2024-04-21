package com.s3practice.product.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s3practice.product.model.UserEntity;


public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {

    public UserEntity findByUsername(String username);
    public UserEntity findByEmail(String email);

}
