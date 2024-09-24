package org.example.despeis.services;

import org.example.despeis.model.Spettacolo;
import org.example.despeis.repository.SpettacoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpettacoloService {

    private final SpettacoloRepository spettacoloRepository;
    @Autowired
    public SpettacoloService(SpettacoloRepository spettacoloRepository) {
        this.spettacoloRepository = spettacoloRepository;
    }

    public List<Spettacolo> getAllSpettacoli(){
        return this.spettacoloRepository.findAll();
    }

}
