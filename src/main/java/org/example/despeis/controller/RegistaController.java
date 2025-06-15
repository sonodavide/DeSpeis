package org.example.despeis.controller;

import org.apache.coyote.BadRequestException;
import org.example.despeis.dto.RegistaDto;
import org.example.despeis.services.RegistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/regista")
public class RegistaController {

    private final RegistaService registaService;
    @Autowired
    public RegistaController(RegistaService registaService) {
        this.registaService = registaService;
    }
    @PreAuthorize("hasRole('admin')")
    @PostMapping("nuovo")
    public ResponseEntity<?> nuovo(@RequestBody RegistaDto registaDto){
        try{
            return ResponseEntity.ok(registaService.nuovo(registaDto));
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().build();
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/elimina")
    public ResponseEntity<?> elimina(@RequestBody RegistaDto registaDto){
        try{
            registaService.delete(registaDto);
            return ResponseEntity.ok("ok");
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("/cerca")
    public ResponseEntity<?> suggest(@RequestParam String query, @RequestParam(name="pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name="pageSize", defaultValue = "5") int pageSize) {
        try{
            return ResponseEntity.ok(registaService.ricerca(query, pageNumber, pageSize));
        }catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/paged")
    public ResponseEntity<?> getAllPaged(@RequestParam(name="pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name="pageSize", defaultValue = "5") int pageSize){

        try{
            return ResponseEntity.ok(registaService.getAllPaginated(pageNumber, pageSize));
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/count")
    public ResponseEntity<?> count(){
        try{
            return ResponseEntity.ok(registaService.count());
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("/getNomeById")
    public ResponseEntity<?> getNomeById(@RequestParam Integer id){
        try{
            return ResponseEntity.ok(registaService.getNomeById(id));
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
