package com.springboot.blog.service.service.impl;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponseDto;
import com.springboot.blog.entity.PostEntity;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class postServiceImpl implements PostService {
    private PostRepository postRepository;
    private ModelMapper mapper;
    public postServiceImpl(
            PostRepository postRepository,
            ModelMapper mapper
    ){
        this.postRepository = postRepository;
        this.mapper = mapper;
    }
    @Override
    public PostDto createPost(PostDto post) {
        PostEntity postEntity = mapDtoToEntity(post);
        PostEntity savedPost = this.postRepository.save(postEntity);
        return mapEntityToDto(savedPost);
    }
    private PostEntity mapDtoToEntity(PostDto post){
        return this.mapper.map(post,PostEntity.class);
    }

    private PostDto mapEntityToDto(PostEntity post){
        return this.mapper.map(post,PostDto.class);
    }

    private PostDto mapEntityToDtoGetAllPost(PostEntity post){
        return this.mapper.map(post,PostDto.class);
    }

    private PostEntity findById(Long id){
        return this.postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id",id));
    }

    @Override
    public PostResponseDto getAllPost(int pageNo,int pageSize,String sortBy,String sortDir){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
       Page<PostEntity> post =  this.postRepository.findAll(pageable);
       List<PostEntity> listPost = post.getContent();

        List<PostDto> content =  listPost.stream().map(item->mapEntityToDto(item)).collect(Collectors.toList());
        PostResponseDto postResponse = new PostResponseDto();
        postResponse.setContent(content);
        postResponse.setLast(post.isLast());
        postResponse.setPageNo(post.getNumber());
        postResponse.setPageSize(post.getSize());
        postResponse.setTotalPages(post.getTotalPages());
        postResponse.setTotalElement(post.getTotalElements());
        return postResponse;
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
