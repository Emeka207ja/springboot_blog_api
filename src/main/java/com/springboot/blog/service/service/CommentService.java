package com.springboot.blog.service.service;

import com.springboot.blog.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long Post_id,CommentDto comment);
    List<CommentDto> getAllComment(Long id);
}
