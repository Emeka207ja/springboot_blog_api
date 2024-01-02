package com.springboot.blog.dto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.Set;
@Data
public class PostDto {
    private Long id;
    @NotEmpty(message="title can not be empty")
    @Size(min = 3, message = "title must be at least three characters")
    private String title;
    @NotEmpty(message="description can not be empty")
    @Size(min = 10, message = "description must be at least ten characters")
    private String description;
    @NotEmpty(message="content can not be empty")
    @Size(min = 10, message = "content must be at least ten characters")
    private String content;
    Set<CommentDto>comments;
}
