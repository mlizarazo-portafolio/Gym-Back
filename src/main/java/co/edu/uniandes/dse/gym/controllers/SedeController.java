package co.edu.uniandes.dse.gym.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.services.SedeService;
import co.edu.uniandes.dse.gym.dto.SedeDTO;
import co.edu.uniandes.dse.gym.dto.SedeDetailDTO;
import co.edu.uniandes.dse.gym.entities.SedeEntity;

@RestController
@RequestMapping("/sedes")
public class SedeController {
    
    @Autowired
    private SedeService sedeService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<SedeDetailDTO> findAll() {
        List<SedeEntity> sedes = sedeService.getSedes();
        return modelMapper.map(sedes, new TypeToken<List<SedeDetailDTO>>() {}.getType()); 
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public SedeDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        SedeEntity sedeEntity = sedeService.getSedeById(id);
        return modelMapper.map(sedeEntity, SedeDetailDTO.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public SedeDTO create(@RequestBody SedeDTO sedeDTO) throws IllegalOperationException, EntityNotFoundException {
            SedeEntity sedeEntity = sedeService.createSede(modelMapper.map(sedeDTO, SedeEntity.class));
            return modelMapper.map(sedeEntity, SedeDTO.class);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public SedeDTO update(@PathVariable("id") Long id, @RequestBody SedeDTO sedeDTO)
                            throws EntityNotFoundException, IllegalOperationException {
            SedeEntity sedeEntity = sedeService.updateSede(id, modelMapper.map(sedeDTO, SedeEntity.class));
            return modelMapper.map(sedeEntity, SedeDTO.class);
    }    
    
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
            sedeService.deleteSede(id);
    }
}