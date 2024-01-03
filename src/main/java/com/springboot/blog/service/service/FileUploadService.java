package com.springboot.blog.service.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    String uploadFile(MultipartFile multipartFile) throws Exception;
}
