package org.example.despeis.controller;

import jakarta.validation.constraints.NotNull;
import org.apache.coyote.BadRequestException;
import org.example.despeis.dto.NuovoSpettacoloDto;
import org.example.despeis.dto.SpettacoloDto;
import org.example.despeis.services.PostiSpettacoloService;
import org.example.despeis.services.SpettacoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@CrossOrigin
@RestController
@RequestMapping("/spettacolo")
public class SpettacoloController {

    private final SpettacoloService spettacoloService;
    private final PostiSpettacoloService postiSpettacoloService;
    @Autowired
    public SpettacoloController(SpettacoloService spettacoloService, PostiSpettacoloService postiSpettacoloService) {
        this.postiSpettacoloService = postiSpettacoloService;
        this.spettacoloService = spettacoloService;
    }

    //QUESTO E' QUELLO DEL SITO PER GLI UTENTI NORMALI.
    @PostMapping("/byDate")
    public ResponseEntity<?> getFilmSpettacoloAcquistabileByDate(@RequestParam("date") String date){
        LocalDate d = LocalDate.parse(date);
        try{
            return ResponseEntity.ok(spettacoloService.getFilmSpettacoloAcquistabileByDate(d));
        }catch(Exception e){
            return new ResponseEntity<>(e.toString()
                    , HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/nuovo")
    public ResponseEntity<?> nuovo(@Validated @RequestBody NuovoSpettacoloDto nuovo){

        try{
            return ResponseEntity.ok(spettacoloService.aggiungiSpettacolo(nuovo));
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().build();
        }catch (IllegalStateException e){
            return ResponseEntity.status(409).build();
        }
        catch (Exception e){

            return ResponseEntity.internalServerError().build();
        }
    }
    @PreAuthorize("hasRole('admin')")
    @PostMapping("elimina")
    public ResponseEntity<?> elimina(@Validated @RequestBody NuovoSpettacoloDto spettacolo){
        try{
            spettacoloService.elimina(spettacolo);
            return ResponseEntity.ok().build();
        }catch (Exception e){
e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/postiSpettacoloAcquistabile")
    public ResponseEntity<?> getPostiSpettacoloAcquistabile(@RequestParam("spettacoloId") Integer spettacoloId){
        try{
            return ResponseEntity.ok(postiSpettacoloService.getAcquistabileBySpettacoloId(spettacoloId));
        }catch (Exception e){
e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/postiSpettacolo")
    public ResponseEntity<?> getPostiSpettacolo(@RequestParam("spettacoloId") Integer spettacoloId){
        try{
            return ResponseEntity.ok(postiSpettacoloService.getBySpettacoloId(spettacoloId));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/cerca")
    public ResponseEntity<?> cercaByDate(@RequestParam("date") String date, @RequestParam(name="pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name="pageSize", defaultValue = "5") int pageSize){
        LocalDate d = LocalDate.parse(date);
        try{
            return ResponseEntity.ok(spettacoloService.cerca(d, pageNumber, pageSize));
        }catch(Exception e){
            return new ResponseEntity<>(e.toString()
                    , HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/paged")
    public ResponseEntity<?> getAllPaged(@RequestParam(name="pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name="pageSize", defaultValue = "5") int pageSize){

        try{
            return ResponseEntity.ok(spettacoloService.getAllPaginated(pageNumber, pageSize));
        }catch (Exception e){
e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


    @PreAuthorize("hasRole('admin')")
    @GetMapping("/count")
    public ResponseEntity<?> count(){
        try{
            return ResponseEntity.ok(spettacoloService.count());
        }catch (Exception e){
e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/getById")
    public ResponseEntity<?> getById(@NotNull @RequestParam Integer id){
        try{
            return ResponseEntity.ok(spettacoloService.getById(id));
        }catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
        catch (Exception e){
e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }



    @GetMapping("/getByIdAcquistabile")
    public ResponseEntity<?> getByIdAcquistabile(@NotNull @RequestParam Integer id){
        try{
            return ResponseEntity.ok(spettacoloService.getByIdAcquistabile(id));
        }catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
        catch (Exception e){
e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/getSenzaFilmAcquistabileById")
    public ResponseEntity<?> getSenzaFilmAcquistabileById(@NotNull @RequestParam int id){
        try{
            return ResponseEntity.ok(spettacoloService.getSenzaFilmAcquistabileById(id));
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/getSenzaFilmById")
    public ResponseEntity<?> getSenzaFilmById(@RequestParam int id){
        try{
            return ResponseEntity.ok(spettacoloService.getSenzaFilmById(id));
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/getAllSenzaFilmTags")
    public ResponseEntity<?> getAllSenzaFilmTags(@RequestParam(name="pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name="pageSize", defaultValue = "5") int pageSize) {

        try {
            return ResponseEntity.ok(spettacoloService.getAllSenzaFilmTags(pageNumber, pageSize));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/cercaSenzaFilmTags")
    public ResponseEntity<?> cercaByDateSenzaFilmTags(@RequestParam("date") String date, @RequestParam(name="pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name="pageSize", defaultValue = "5") int pageSize){
        LocalDate d = LocalDate.parse(date);
        try{
            return ResponseEntity.ok(spettacoloService.cercaSenzaFilmTags(d, pageNumber, pageSize));
        }catch(Exception e){
            return new ResponseEntity<>(e.toString()
                    , HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/getNuovoSpettacoloById")
    public ResponseEntity<?> getNuovoSpettacoloById(@NotNull @RequestParam int id){
        try{
            return ResponseEntity.ok(spettacoloService.getNuovoSpettacoloById(id));
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/getStato")
    public ResponseEntity<?> isFinito(@NotNull @RequestParam int id){
        try{
            return ResponseEntity.ok(spettacoloService.getStato(id));
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
