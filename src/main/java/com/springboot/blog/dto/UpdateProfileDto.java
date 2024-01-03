package com.springboot.blog.dto;

import lombok.Data;

@Data
public class UpdateProfileDto {
    private String firstname;
    private String lastname;
    private String image;
    private int age;
}
