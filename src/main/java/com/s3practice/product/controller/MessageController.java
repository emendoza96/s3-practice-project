package com.s3practice.product.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.s3practice.product.model.ContactMessage;
import com.s3practice.product.service.ContactMessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private ContactMessageService contactMessageService;

    @PostMapping()
    public ResponseEntity<?> saveContactMessage(
        @RequestParam String name,
        @RequestParam String lastname,
        @RequestParam String email,
        @RequestParam String message,
        @RequestParam(required = false) MultipartFile image
    ) {

        ContactMessage contactMessage = ContactMessage.builder()
            .name(name)
            .lastName(lastname)
            .email(email)
            .message(message)
            .build()
        ;

        try {
            ContactMessage newContactMessage = contactMessageService.saveContactMessage(contactMessage);
            return ResponseEntity.ok().body(newContactMessage);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}
