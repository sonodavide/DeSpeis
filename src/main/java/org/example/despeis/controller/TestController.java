package org.example.despeis.controller;

import org.example.despeis.security.Utils;
import org.example.despeis.services.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    FilmService filmService;
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<?> test(JwtAuthenticationToken auth) {

        if(auth != null) {
            System.out.println(Utils.getUserId(auth));
            //auth.getToken().getClaims().forEach((k, v) -> {System.out.println(k+":"+v);});
        }else{
            System.out.println("nulla");
        }
        return ResponseEntity.ok("Hello World");
    }

    @GetMapping("/casca")
    public ResponseEntity<?> casca(@RequestParam int id){
        try {
        filmService.elimina(id);
        return ResponseEntity.ok("fatto");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok("no");

        }
    }
}
