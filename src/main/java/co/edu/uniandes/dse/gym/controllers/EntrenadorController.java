package co.edu.uniandes.dse.gym.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import co.edu.uniandes.dse.gym.services.EntrenadorService;
import co.edu.uniandes.dse.gym.dto.EntrenadorDetailDTO;
import co.edu.uniandes.dse.gym.entities.EntrenadorEntity;

@RestController
@RequestMapping("/entrenadores")
public class EntrenadorController {

    @Autowired
    private EntrenadorService entrenadorService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<EntrenadorDetailDTO> findAll(){
        List<EntrenadorEntity> entrenadores = entrenadorService.getEntrenadores();
        return modelMapper.map(entrenadores, new TypeToken<List<EntrenadorDetailDTO>>() {
        }.getType());
    }
    
}
