package org.example.despeis.services;

import org.example.despeis.model.Spettacolo;
import org.example.despeis.repository.SpettacoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpettacoloService {
    @Autowired
    private SpettacoloRepository spettacoloRepository;



    public List<Spettacolo> getAllSpettacoli(){
        return this.spettacoloRepository.findAll();
    }

}
