package com.example.demo.Controller;

import java.util.List;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Post;
import com.example.demo.services.PostService;

@RestController
@RequestMapping("/api/v1")
public class HomeRestController {
    @Autowired
    private PostService postService;

    Logger logger =org.slf4j.LoggerFactory.getLogger(HomeRestController.class);

    @GetMapping("/")
    public List<Post> home(){
        logger.error("this is the text error log");
        return postService.findAll();
    }

}
