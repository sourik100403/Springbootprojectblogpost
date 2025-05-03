package com.example.demo.Controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // ✅ Correct import
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.models.Account;
import com.example.demo.models.Post;
import com.example.demo.services.AccountService;
import com.example.demo.services.PostService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;




@Controller
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable Long id, Model model,Principal principal) {
        Optional<Post> optionalPost = postService.getId(id);
        String authUser="email";
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            if(principal != null){
                authUser=principal.getName();
            }
            if(authUser.equals(post.getAccount().getEmail())){
                model.addAttribute("isOwner", true);
            }
            else{
                model.addAttribute("isOwner", false);
            }
            return "post_views/post";
        } else {
            return "404";
        }
    }
    @GetMapping("/posts/add")
    @PreAuthorize("isAuthenticated()")
    public String addPost(Model model,Principal principal) {
        String authUser="email";
        if(principal!=null){
            authUser=principal.getName();
        }
        Optional<Account> optionalAccount=accountService.findOneByEmail(authUser);
        if(optionalAccount.isPresent()){
            Post post=new Post();
            post.setAccount(optionalAccount.get());
            model.addAttribute("post", post);
            return "post_views/post_add";
        }
        else{
            return "redirect:/";
        }
        
    }

    @PostMapping("/posts/add")
    @PreAuthorize("isAuthenticated()")
    public String addPostHandler(@Valid @ModelAttribute Post post,BindingResult bindingResult,Principal principal) {
        if(bindingResult.hasErrors()){
            return "post_views/post_add";
        }
        String authUser="email";
        if(principal!=null){
            authUser=principal.getName();
        }
        if(post.getAccount().getEmail().compareToIgnoreCase(authUser)<0){
            return "redirect:/?error";
        }
        postService.Save(post);
        return "redirect:/posts/"+post.getId();
    }
    @GetMapping("/posts/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String getPostForEdit(@PathVariable Long id,Model model){
        Optional<Post> optionalPost=postService.getId(id);
        if(optionalPost.isPresent()){
            Post post=optionalPost.get();
            model.addAttribute("post", post);
            return "post_views/post_edit";
        }
        else{
            return "404";
        }  
    }
    @PostMapping("/posts/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String updatePost(@Valid @ModelAttribute Post post,BindingResult bindingResult,@PathVariable Long id){
        if(bindingResult.hasErrors()){
            return "post_views/post_edit";
        }
        Optional<Post> optionalPost=postService.getId(id);
        if(optionalPost.isPresent()){
           Post existingPost=optionalPost.get();
           existingPost.setTitle(post.getTitle());
           existingPost.setBody(post.getBody());
           postService.Save(existingPost);
           return "redirect:/posts/"+post.getId();
        }
        else{
            return "404";
        }  
    }

    @GetMapping("/posts/{id}/delete")
    @PreAuthorize("isAuthenticated()")
    public String deletePost(@PathVariable Long id,@ModelAttribute Post post){
        Optional<Post> optionalPost=postService.getId(id);
        if(optionalPost.isPresent()){
           Post deletepost=optionalPost.get();
           postService.delete(deletepost);
           return "redirect:/";
        }
        else{
            return "redirect:/?error";
        }  
    }

    
}
