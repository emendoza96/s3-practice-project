package com.s3practice.product.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.s3practice.product.client.AwsClient;
import com.s3practice.product.dao.ProductRepository;
import com.s3practice.product.model.Product;

@Service
public class ProductService {

    @Autowired
    private AwsClient awsS3Client;

    @Autowired
    private ProductRepository productRepository;

    @Transactional // We need to do a rollback if we can not save the image in s3
    public Product saveProductWithImage(Product product, MultipartFile imageFile) {

        if (imageFile != null) {
            String fileName = String.format("product-%s-%s.jpg", product.getCode(), LocalDate.now().toString());

            try {
                awsS3Client.uploadFile(imageFile, fileName);
            } catch (IOException e) {
                throw new RuntimeException("Error to save the image in S3", e);
            }

            product.setImage(fileName);

        }

        return productRepository.save(product);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }


}
