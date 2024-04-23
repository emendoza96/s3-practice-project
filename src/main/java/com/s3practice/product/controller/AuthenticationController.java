package com.s3practice.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.s3practice.product.service.TokenBlacklistService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
public class AuthenticationController {

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @PostMapping("/app-logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {

        String authToken = request.getHeader("Authorization");

        if (authToken != null && authToken.startsWith("Bearer ")) {
            String token = authToken.substring(7);

            // Add token to black list
            tokenBlacklistService.addToBlacklist(token);

            SecurityContextHolder.clearContext();

            return ResponseEntity.ok().body("Logged out successfully");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


}
