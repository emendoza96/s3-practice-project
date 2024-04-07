package com.s3practice.product.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.s3practice.product.client.AwsClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private AwsClient awsS3Client;

    @PostMapping
    public String postMethodName(@RequestBody MultipartFile multipartFile) {

        try {
            PutObjectResult result = awsS3Client.uploadFile(multipartFile);
            return result.getETag();

        } catch (IOException e) {
            return "error";
        }

    }

}
