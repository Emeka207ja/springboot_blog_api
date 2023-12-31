package com.springboot.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    @NotEmpty(message = "name must not be empty")
    @Size(min = 3, message = "name must be at least 3 character")
    private String name;
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message = "must be a valid email")
    private String email;
    @Size(min = 10,message = "comment body must be at least 10 characters")
    private String body;
}
