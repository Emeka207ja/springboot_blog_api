package com.springboot.blog.repository;

import com.springboot.blog.entity.postEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface postRepository extends JpaRepository<postEntity,Long> {
}
