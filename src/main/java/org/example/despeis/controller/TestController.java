package org.example.despeis.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping
    public ResponseEntity<String> test(Authentication auth) {
        if(auth != null) {
        System.out.println(auth.getAuthorities().toString());
        }else{
            System.out.println("nulla");
        }
        return ResponseEntity.ok("Hello World");
    }
}
