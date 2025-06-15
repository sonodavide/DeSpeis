package org.example.despeis.controller;

import org.example.despeis.model.Spettacolo;
import org.example.despeis.services.SpettacoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/spettacolo")
public class SpettacoloController {
    @Autowired
    private SpettacoloService spettacoloService;

    @GetMapping("/hey")
    public List<Spettacolo> getAll(){
        return spettacoloService.getAllSpettacoli();
    }
}
