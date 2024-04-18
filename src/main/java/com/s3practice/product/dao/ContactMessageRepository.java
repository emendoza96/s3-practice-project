package com.s3practice.product.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s3practice.product.model.ContactMessage;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Integer> {

}
