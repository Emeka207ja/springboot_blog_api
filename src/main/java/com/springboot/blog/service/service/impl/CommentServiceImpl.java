package com.springboot.blog.service.service.impl;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.entity.CommentEntity;
import com.springboot.blog.entity.PostEntity;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper commentMapper;
    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository,ModelMapper commentMapper){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.commentMapper = commentMapper;
    }

    private  CommentDto mapEntityToDto(CommentEntity comment){
        return commentMapper.map(comment,CommentDto.class);
    }
    private CommentEntity mapDtoToEntity(CommentDto comment){
        return commentMapper.map(comment,CommentEntity.class);
    }
    private CommentEntity findCommentById(Long comment_id){
        return this.commentRepository.findById(comment_id).orElseThrow(()->new ResourceNotFoundException("comment","id",comment_id));
    }

    private PostEntity findPostById(Long post_id){
        return this.postRepository.findById(post_id).orElseThrow(()->new ResourceNotFoundException("post","id", post_id));
    }
    @Override
    public CommentDto createComment(Long Post_id,CommentDto comment) {
        PostEntity post = findPostById(Post_id) ;
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
    @Override
    public CommentDto getCommentById(Long post_id,Long comment_id){
        PostEntity post = this.postRepository.findById(post_id).orElseThrow(()->new ResourceNotFoundException("post","id",post_id));
        CommentEntity comment = this.commentRepository.findById(comment_id).orElseThrow(()->new ResourceNotFoundException("comment","id",comment_id));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new ResourceNotFoundException("comment", "comment id",comment_id);
        }
        return this.mapEntityToDto(comment);
    }

    @Override
    public CommentDto updateCommentById(Long post_id,Long commentId, CommentDto comment) {
        PostEntity post = this.findPostById(post_id);
        CommentEntity commentEntity = this.findCommentById(commentId);
        if(!commentEntity.getPost().getId().equals(post.getId())) throw  new ResourceNotFoundException("comment","id",commentId);

        commentEntity.setName(comment.getName());
        commentEntity.setBody(comment.getBody());
        commentEntity.setEmail(comment.getEmail());
        CommentEntity response = this.commentRepository.save(commentEntity);
        return this.mapEntityToDto(response);
    }

    @Override
    public Long deleteCommentById(Long post_id,Long comment_id) {
        PostEntity post = this.findPostById(post_id);
        CommentEntity comment = this.findCommentById(comment_id);
        if(!comment.getPost().getId().equals(post.getId()))throw  new ResourceNotFoundException("comment","id",comment_id);
        this.commentRepository.deleteById(comment_id);
        return comment.getId();
    }
}
