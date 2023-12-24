package com.springboot.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {
    private List<PostDto> content;
    private int pageNo;
    private int pageSize;
    private int totalPages;
    private Long totalElement;
    private boolean last;

}
