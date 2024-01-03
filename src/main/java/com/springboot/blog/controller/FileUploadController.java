package com.springboot.blog.controller;

import com.springboot.blog.service.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/file")
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    ResponseEntity<String> uploadFile(@RequestParam("image") MultipartFile multipartFile) throws  Exception{
        return new ResponseEntity<>(this.fileUploadService.uploadFile(multipartFile), HttpStatus.CREATED);
    }
}
