package com.springboot.blog.service.service;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.entity.PostEntity;

import java.util.ArrayList;
import java.util.List;

public interface PostService {
    PostDto createPost(PostDto post);
    List<PostDto> getAllPost();
    PostDto getPostById(Long id);
    PostDto updatePost(Long id,PostDto post);
    Long deletePost(Long id);
}
