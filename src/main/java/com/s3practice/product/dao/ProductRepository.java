package com.s3practice.product.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s3practice.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
