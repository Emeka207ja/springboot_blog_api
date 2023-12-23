package com.springboot.blog.service.service.impl;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.entity.PostEntity;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.service.PostService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class postServiceImpl implements PostService {
    private PostRepository postRepository;
    public postServiceImpl(PostRepository postRepository){
        this.postRepository = postRepository;
    }
    @Override
    public PostDto createPost(PostDto post) {
        PostEntity postEntity = mapDtoToEntity(post);
        PostEntity savedPost = this.postRepository.save(postEntity);
        return mapEntityToDto(savedPost);
    }
    private PostEntity mapDtoToEntity(PostDto post){
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(post.getTitle());
        postEntity.setContent(post.getContent());
        postEntity.setDescription(post.getDescription());
        return  postEntity;
    }

    private PostDto mapEntityToDto(PostEntity post){
        PostDto postResponse = new PostDto();
        postResponse.setId(post.getId());
        postResponse.setTitle(post.getTitle());
        postResponse.setContent(post.getContent());
        postResponse.setDescription(post.getDescription());
        return postResponse;
    }

    @Override
    public List<PostDto> getAllPost(){
        List<PostEntity> post =  this.postRepository.findAll();

        return post.stream().map(item->mapEntityToDto(item)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(Long id){
        PostEntity post = this.postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id",id));
        return mapEntityToDto(post);
    }

}
