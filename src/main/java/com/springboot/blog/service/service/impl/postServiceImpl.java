package com.springboot.blog.service.service.impl;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.entity.PostEntity;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    private PostEntity findById(Long id){
        return this.postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id",id));
    }

    @Override
    public List<PostDto> getAllPost(int pageNo,int pageSize){
        Pageable pageable = PageRequest.of(pageNo,pageSize);
       Page<PostEntity> post =  this.postRepository.findAll(pageable);
       List<PostEntity> listPost = post.getContent();

        return listPost.stream().map(item->mapEntityToDto(item)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(Long id){
        PostEntity post = this.findById(id);
        return mapEntityToDto(post);
    }

    @Override
    public PostDto updatePost(Long id,PostDto post){
        PostEntity postEntity = this.findById(id);
        postEntity.setTitle(post.getTitle());
        postEntity.setContent(post.getContent());
        postEntity.setDescription(post.getDescription());
        this.postRepository.save(postEntity);
        return this.mapEntityToDto(postEntity);
    }

    @Override
    public Long deletePost(Long id){
        PostEntity postEntity = this.findById(id);
        Long postId = postEntity.getId();
        this.postRepository.delete(postEntity);
        return postId;
    }
}
