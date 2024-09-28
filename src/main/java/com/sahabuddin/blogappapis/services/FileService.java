package com.sahabuddin.blogappapis.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    String uploadImage(String path,MultipartFile image) throws IOException;

    InputStream getImage(String path, String filename) throws FileNotFoundException;
}
