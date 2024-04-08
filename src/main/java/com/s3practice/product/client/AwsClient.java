package com.s3practice.product.client;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;

import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

@Component
public class AwsClient {

    private static final Logger logger = LoggerFactory.getLogger(AwsClient.class);

    @Autowired
    private AmazonS3 awsS3Client;

    @Value("${aws.bucket-name}")
    private String bucketName;

    public void createBucket(String newBucketName) {

        if(awsS3Client.doesBucketExistV2(newBucketName)) {
            logger.info("Bucket name is not available. Try again with a different Bucket name.");
            return;
        }

        try {
            CreateBucketRequest bucketRequest = new CreateBucketRequest(newBucketName);

            awsS3Client.createBucket(bucketRequest);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

    public PutObjectResult uploadFile(MultipartFile multipartFile, String fileName) throws IOException {

        File file = convertMultiPartFileToFile(multipartFile);

        PutObjectRequest request = new PutObjectRequest(bucketName, fileName, file);

        return awsS3Client.putObject(request);
    }


    private File convertMultiPartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        file.transferTo(convertedFile);
        return convertedFile;
    }
}
