package com.springboot.blog.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
    String message = "logged in successful";
    String token;
    String tokenType = "Bearer";
}
