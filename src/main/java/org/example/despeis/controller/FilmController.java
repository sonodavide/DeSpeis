package org.example.despeis.controller;

import lombok.Getter;
import org.example.despeis.dto.FilmDto;
import org.example.despeis.dto.NuovoFilmDto;
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
    public ResponseEntity<?> getFilms(@RequestParam(name="id", required = false) Integer id){
        ResponseEntity<?> r;
        try{

            r = id==null ? ResponseEntity.ok(filmService.getAll()) : ResponseEntity.ok(filmService.getById(id));
        }catch (Exception e){
            r= ResponseEntity.notFound().build();
        }
        return r;
    }





    @PostMapping("/nuovo")
    public ResponseEntity<?> nuovo(@RequestBody FilmDto film){
        try{
            return ResponseEntity.ok(filmService.nuovoFilm(film));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/elimina")
    public ResponseEntity<?> elimina(@RequestBody FilmDto film){
        try{
            return ResponseEntity.ok(filmService.elimina(film));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/suggest")
    public ResponseEntity<?> suggest(@RequestParam String query) {
        try{
            return ResponseEntity.ok(filmService.ricerca(query));
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
