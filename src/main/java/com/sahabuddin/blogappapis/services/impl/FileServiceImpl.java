package com.sahabuddin.blogappapis.services.impl;

import com.sahabuddin.blogappapis.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile image) throws IOException {

        String fileName = image.getOriginalFilename();

        String randomID = UUID.randomUUID().toString();
        String finalFileName = randomID.concat(fileName.substring(fileName.lastIndexOf(".")));
        String filePath = path + File.separator + finalFileName;
        log.info("Uploading image to {}", filePath);

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        Files.copy(image.getInputStream(), Paths.get(filePath));
        return finalFileName;
    }

    @Override
    public InputStream getImage(String path, String filename) throws FileNotFoundException {
        String filePath = path + File.separator + filename;
        log.info("Retrieving image from {}", filePath);
        return new FileInputStream(filePath);
    }
}
