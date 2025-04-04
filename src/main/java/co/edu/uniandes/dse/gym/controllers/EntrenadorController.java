package co.edu.uniandes.dse.gym.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.services.EntrenadorService;
import co.edu.uniandes.dse.gym.dto.EntrenadorDetailDTO;
import co.edu.uniandes.dse.gym.dto.EntrenadorDTO;
import co.edu.uniandes.dse.gym.entities.EntrenadorEntity;

@RestController
@RequestMapping("/entrenadores")
public class EntrenadorController {

    @Autowired
    private EntrenadorService entrenadorService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public EntrenadorDTO create(@RequestBody EntrenadorDTO entrenadorDTO) throws IllegalOperationException, EntityNotFoundException {
            EntrenadorEntity entrenadorEntity = entrenadorService.createEntrenador(modelMapper.map(entrenadorDTO, EntrenadorEntity.class));
            return modelMapper.map(entrenadorEntity, EntrenadorDTO.class);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<EntrenadorDetailDTO> findAll(){
        List<EntrenadorEntity> entrenadores = entrenadorService.getEntrenadores();
        return modelMapper.map(entrenadores, new TypeToken<List<EntrenadorDetailDTO>>() {
        }.getType());
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public EntrenadorDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
            EntrenadorEntity entrenadorEntity = entrenadorService.getEntrenadorById(id);
            return modelMapper.map(entrenadorEntity, EntrenadorDetailDTO.class);
    }  
    
    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public EntrenadorDTO update(@PathVariable("id") Long id, @RequestBody EntrenadorDTO entrenadorDTO)
                            throws EntityNotFoundException, IllegalOperationException {
            EntrenadorEntity entrenadorEntity = entrenadorService.updateEntrenador(id, modelMapper.map(entrenadorDTO, EntrenadorEntity.class));
            return modelMapper.map(entrenadorEntity, EntrenadorDTO.class);
    }    
    
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
            entrenadorService.deleteEntrenador(id);
    }    
}
