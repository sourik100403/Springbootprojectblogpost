package com.example.demo.Controller;

import org.springframework.web.bind.annotation.GetMapping;

public class ErrorController {
    
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied"; // Points to access-denied.html
    }
}
