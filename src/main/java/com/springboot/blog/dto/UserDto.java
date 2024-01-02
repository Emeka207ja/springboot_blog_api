package com.springboot.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
    @NotEmpty(message = "username must not be empty")
    private String username;
    @NotEmpty(message = "email must not be empty")
    @Email(message = "must be a valid email")
    private String email;
    @NotEmpty(message = "password must not be empty")
    @Size(min = 5, message = "password must be at least 5 characters")
    private String password;
}
