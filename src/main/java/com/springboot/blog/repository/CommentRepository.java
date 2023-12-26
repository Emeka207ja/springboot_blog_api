package com.springboot.blog.repository;

import com.springboot.blog.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity,Long> {

    List<CommentEntity> findByPostId(Long postId);
}
