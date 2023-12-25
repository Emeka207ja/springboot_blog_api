package com.springboot.blog.controller;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponseDto;
import com.springboot.blog.entity.PostEntity;
import com.springboot.blog.service.service.PostService;
import com.springboot.blog.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping("post")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto post){
        return new ResponseEntity<>(this.postService.createPost(post), HttpStatus.CREATED);
    }

    @GetMapping("posts")
    public ResponseEntity<PostResponseDto>getAllPosts(
            @RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NO,required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir
    ){
        return   ResponseEntity.ok(this.postService.getAllPost(pageNo,pageSize,sortBy,sortDir));

    }

    @GetMapping("posts/{id}")
    public ResponseEntity<PostDto>getPostById(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.postService.getPostById(id));
    }

    @PutMapping("posts/{id}")
    public ResponseEntity<PostDto>updatePost(@PathVariable(name = "id") Long id, @RequestBody PostDto post){
        return new ResponseEntity<>(this.postService.updatePost(id,post),HttpStatus.CREATED );
    }

    @DeleteMapping("posts/{id}")
    public ResponseEntity<Long>deletePost(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(this.postService.deletePost(id));
    }
}
