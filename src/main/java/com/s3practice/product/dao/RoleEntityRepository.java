package com.s3practice.product.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s3practice.product.model.RoleEntity;

public interface RoleEntityRepository extends JpaRepository<RoleEntity, Integer> {

}
