package com.springboot.blog.controller;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.service.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/post")
public class CommentController {
    private final CommentService commentService;
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }
    @PostMapping("/{id}/comment")
    public ResponseEntity<CommentDto>createComment(
            @PathVariable("id") Long id,@RequestBody() CommentDto comment
    ){

        return new ResponseEntity<>(this.commentService.createComment(id,comment), HttpStatus.CREATED);
    }
    @GetMapping("/{id}/comment")
    public ResponseEntity<List<CommentDto>>getAllComments(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.commentService.getAllComment(id));
    }
}
