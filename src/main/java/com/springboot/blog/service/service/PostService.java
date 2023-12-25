package com.springboot.blog.service.service;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponseDto;
import com.springboot.blog.entity.PostEntity;
import org.hibernate.query.Page;

import java.util.ArrayList;
import java.util.List;

public interface PostService {
    PostDto createPost(PostDto post);
    PostResponseDto getAllPost(int pageNo, int pageSize,String sortBy,String sortDir);
    PostDto getPostById(Long id);
    PostDto updatePost(Long id,PostDto post);
    Long deletePost(Long id);
}
