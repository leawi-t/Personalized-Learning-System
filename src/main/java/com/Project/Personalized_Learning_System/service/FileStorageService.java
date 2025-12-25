package com.Project.Personalized_Learning_System.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String saveFile(MultipartFile file);
    void delete(String filePath);
}
