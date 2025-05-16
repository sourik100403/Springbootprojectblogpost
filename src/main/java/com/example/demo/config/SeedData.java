package com.example.demo.config;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.models.Account;
import com.example.demo.models.Authority;
import com.example.demo.models.Post;
import com.example.demo.services.AccountService;
import com.example.demo.services.AuthorityService;
import com.example.demo.services.PostService;
import com.example.demo.util.constants.Privillages;
import com.example.demo.util.constants.Roles;
@Component
public class SeedData implements CommandLineRunner {
    @Autowired
    private PostService postService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AuthorityService authorityService;

    @Override
    public void run(String... args) throws Exception {
        for (Privillages auth:Privillages.values()){
            Authority authority=new Authority();
            authority.setId(auth.getId());
            authority.setName(auth.getPrivillage());
            authorityService.save(authority);
        }

        Account account01=new Account();
        Account account02=new Account();
        Account account03=new Account();
        Account account04=new Account();

        account01.setEmail("user@user.com");
        account01.setPassword("12345");
        account01.setFirstname("user");
        account01.setLastname("lastname");
        account01.setGender("Male");
        account01.setAge(25);
        account01.setDate_of_birth(LocalDate.of(1999, 1, 1));

        account02.setEmail("admin@admin.com");
        account02.setPassword("12345");
        account02.setFirstname("admin");
        account02.setLastname("lastname");
        account02.setRole(Roles.ADMIN.getRole());
        account02.setGender("Female");
        account02.setAge(35);
        account02.setDate_of_birth(LocalDate.of(1989, 2, 15));

        account03.setEmail("editor@editor.com");
        account03.setPassword("12345");
        account03.setFirstname("editor");
        account03.setLastname("lastname");
        account03.setGender("Others");
        account03.setAge(30);
        account03.setDate_of_birth(LocalDate.of(1994, 3, 20));
        account03.setRole(Roles.EDITOR.getRole());

        // account04.setEmail("super_editor@editor.com");
        account04.setEmail("sourikparui100403@gmail.com");
        account04.setPassword("12345");
        account04.setFirstname("supereditor");
        account04.setLastname("lastname");
        account04.setGender("Female");
        account04.setAge(40);
        account04.setDate_of_birth(LocalDate.of(1984, 4, 10));
        account04.setRole(Roles.EDITOR.getRole());

        Set<Authority> authorites=new HashSet<>();
        authorityService.findById(Privillages.RESET_ANY_USER_PASSWORD.getId()).ifPresent(authorites::add);
        authorityService.findById(Privillages.ACCESS_ADMIN_PANEL.getId()).ifPresent(authorites::add);
        account04.setAuthorities(authorites);

        accountService.save(account01);
        accountService.save(account02);
        accountService.save(account03);
        accountService.save(account04);

        List<Post> posts=postService.findAll();
        if(posts.size()==0){
            Post post01=new Post();
            post01.setTitle("Post 01");
            post01.setBody("Post 01 body");
            post01.setAccount(account01);
            postService.Save(post01);

            Post post02=new Post();
            post02.setTitle("Post 02");
            post02.setBody("Post 02 body");
            post02.setAccount(account02);
            postService.Save(post02);

            Post post03=new Post();
            post03.setTitle("Post 03");
            post03.setBody("Post03 body");
            post03.setAccount(account03);
            postService.Save(post03);

        }
    }
    
}
