package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>{
    
}
