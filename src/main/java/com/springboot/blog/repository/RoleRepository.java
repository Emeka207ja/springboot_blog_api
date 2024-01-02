package com.springboot.blog.repository;

import com.springboot.blog.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RolesEntity,Long> {
    Optional<RolesEntity> findByName(String name);
}
