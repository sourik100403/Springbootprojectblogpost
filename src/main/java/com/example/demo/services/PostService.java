package com.example.demo.services;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.models.Post;
import com.example.demo.repositories.PostRepository;

import groovyjarjarantlr4.v4.analysis.LeftRecursiveRuleAnalyzer.ASSOC;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    public Optional<Post> getId(Long id){
        return postRepository.findById(id);
    }

    public List<Post> findAll(){
        return postRepository.findAll();
    }
    public Page<Post> findAll(int offset,int pagesize,String field){
        // return postRepository.findAll(PageRequest.of(offset,pagesize).withSort(ASC, field));
        return postRepository.findAll(PageRequest.of(offset, pagesize, Sort.by(Sort.Direction.ASC, field)));
    }
    

    public void delete(Post post){
       postRepository.delete(post);
    }

    public Post Save(Post post){
        if(post.getId()==null){
            post.setCreateAt(LocalDateTime.now());
        }
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

}
