package org.example.despeis.controller;

import lombok.Getter;
import org.apache.coyote.BadRequestException;
import org.example.despeis.dto.FilmDto;
import org.example.despeis.dto.NuovoFilmDto;
import org.example.despeis.services.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

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

            r = ResponseEntity.ok(filmService.getById(id));
        }catch (NoSuchElementException e){
            r= ResponseEntity.notFound().build();
        }catch (Exception e){
            r = ResponseEntity.internalServerError().build();
        }
        return r;
    }




    @PreAuthorize("hasRole('admin')")
    @PostMapping("/nuovo")
    public ResponseEntity<?> nuovo(@RequestBody FilmDto film){
        try{
            return ResponseEntity.ok(filmService.nuovoFilm(film));
        }catch (IllegalStateException e){
                return ResponseEntity.status(409).build();
        }
        catch (BadRequestException e){
            return ResponseEntity.badRequest().build();
        }
        catch (Exception e){
e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/elimina")
    public ResponseEntity<?> elimina(@RequestBody FilmDto film){
        try{
            return ResponseEntity.ok(filmService.elimina(film));
        }catch (Exception e){
e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/cerca")
    public ResponseEntity<?> suggest(@RequestParam String query, @RequestParam(name="pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name="pageSize", defaultValue = "5") int pageSize) {
        try{
            return ResponseEntity.ok(filmService.ricerca(query, pageNumber, pageSize));
        }catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/paged")
    public ResponseEntity<?> getAllPaged(@RequestParam(name="pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name="pageSize", defaultValue = "5") int pageSize){

        try{
            return ResponseEntity.ok(filmService.getAllPaginated(pageNumber, pageSize));
        }catch (Exception e){
e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/count")
    public ResponseEntity<?> count(){
        try{
            return ResponseEntity.ok(filmService.count());
        }catch (Exception e){
e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("cercaTag")
    public ResponseEntity<?> getCercaTag(@RequestParam String tag, @RequestParam Integer id, @RequestParam(name="pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name="pageSize", defaultValue = "5") int pageSize){
        try{
            return ResponseEntity.ok(filmService.cercaTag(tag, id, pageNumber, pageSize));
        }catch (Exception e){
e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
