package org.example.despeis.controller;

import lombok.Getter;
import org.example.despeis.services.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/film")
public class FilmController {

    private final FilmService filmService;
    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }
    @GetMapping
    public ResponseEntity<?> getFilms(){
        try{
            return ResponseEntity.ok(filmService.getAll());
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<?> getFilmById(@RequestParam("id") int id) {
        try{
            return ResponseEntity.ok(filmService.getById(id));
        }catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cerca")
    public ResponseEntity<?> getFilmsByTitolo(@RequestParam("titolo") String titolo) {
        System.out.println(titolo);
        System.out.println(titolo);
        try{
            return ResponseEntity.ok(filmService.getAllByTitolo(titolo));
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}
