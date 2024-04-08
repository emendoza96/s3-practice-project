package com.s3practice.product.service;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class ImageProcessingService {

    public File resizeImage(MultipartFile multipartFile) throws IOException {

        File image = convertMultiPartFileToFile(multipartFile);
        File output = new File("output.jpg");

        Thumbnails.of(image)
            .scale(1)
            .outputQuality(0.5)
            .outputFormat("jpg")
            .toFile(output)
        ;

        return output;
    }

    private File convertMultiPartFileToFile(MultipartFile file) throws IOException {
        // Create a temporal file into the current directory
        File convertedFile = File.createTempFile("temp-file", null);
        file.transferTo(convertedFile);
        return convertedFile;
    }

}
