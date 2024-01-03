package com.springboot.blog.service.service.impl;

import com.cloudinary.Cloudinary;
import com.springboot.blog.service.service.FileUploadService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;
@Service
@AllArgsConstructor
public class FileUploadServiceImp implements FileUploadService {
    private Cloudinary cloudinary;
    @Override
    public String uploadFile(MultipartFile multipartFile) throws Exception {
        return cloudinary.uploader()
                .upload(
                    multipartFile.getBytes(),
                    Map.of("public_id", UUID.randomUUID().toString())
                )
                .get("url")
                .toString();
    }
}
