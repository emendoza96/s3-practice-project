package com.s3practice.product.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class ImageProcessingService {

    public byte[] resizeImage(MultipartFile multipartFile) throws IOException {

        File image = convertMultiPartFileToFile(multipartFile);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Thumbnails.of(image)
            .scale(1)
            .outputQuality(0.5)
            .outputFormat("jpg")
            .toOutputStream(outputStream);
        ;

        return outputStream.toByteArray();
    }

    private File convertMultiPartFileToFile(MultipartFile file) throws IOException {
        // Create a temporal file into the current directory
        File convertedFile = File.createTempFile("temp-file", null);
        file.transferTo(convertedFile);
        return convertedFile;
    }

}
