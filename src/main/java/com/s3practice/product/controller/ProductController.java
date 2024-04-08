package com.s3practice.product.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.s3practice.product.model.Product;
import com.s3practice.product.service.ProductService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;





@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<String> saveProduct(
        @RequestParam(required = false) String code,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String description,
        @RequestParam(required = false) Float price,
        @RequestParam(required = false) Integer stockMin,
        @RequestParam(required = false) Integer currentStock,
        @RequestParam(required = false) MultipartFile imageFile
    ) {

        Product product = Product.builder()
            .code(code)
            .name(name)
            .description(description)
            .price(price)
            .stockMin(stockMin)
            .currentStock(currentStock)
            .build()
        ;
        System.out.println(imageFile.getOriginalFilename());
        try {
            productService.saveProductWithImage(product, imageFile);
            return ResponseEntity.ok("Product saved successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }

    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {

        try {
            List<Product> products = productService.getAll();
            return ResponseEntity.ok().body(products);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

}
