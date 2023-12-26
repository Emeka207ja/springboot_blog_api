package com.springboot.blog.service.service.impl;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.entity.CommentEntity;
import com.springboot.blog.entity.PostEntity;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    private  CommentDto mapEntityToDto(CommentEntity comment){
         CommentDto commentResponse = new CommentDto();
         commentResponse.setId(comment.getId());
         commentResponse.setName(comment.getName());
         commentResponse.setEmail(comment.getEmail());
         commentResponse.setBody(comment.getBody());
        return commentResponse;
    }
    private CommentEntity mapDtoToEntity(CommentDto comment){
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setBody(comment.getBody());
        commentEntity.setName(comment.getName());
        commentEntity.setEmail(comment.getEmail());
        return commentEntity;
    }
    @Override
    public CommentDto createComment(Long Post_id,CommentDto comment) {
        PostEntity post = this.postRepository.findById(Post_id).orElseThrow(()->new ResourceNotFoundException("post","id", Post_id));
        CommentEntity commentEntity = this.mapDtoToEntity(comment);
        commentEntity.setPost(post);
        CommentEntity commentResponse = this.commentRepository.save(commentEntity);
        return this.mapEntityToDto(commentResponse);
    }

    @Override
    public List<CommentDto> getAllComment(Long id) {
        List<CommentEntity> comments = this.commentRepository.findByPostId(id);
        return comments.stream().map(item->mapEntityToDto(item)).collect(Collectors.toList());

    }
}
