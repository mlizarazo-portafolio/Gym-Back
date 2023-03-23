package co.edu.uniandes.dse.gym.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.gym.dto.AtletaDTO;
import co.edu.uniandes.dse.gym.dto.AtletaDetailDTO;
import co.edu.uniandes.dse.gym.entities.AtletaEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.services.AtletaService;

@RestController
@RequestMapping("/atletas")
public class AtletaController {

    @Autowired
    private AtletaService atletaService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<AtletaDetailDTO> findAll() {
        List<AtletaEntity> atletas = atletaService.getAtletas();
        return modelMapper.map(atletas, new TypeToken<List<AtletaDetailDTO>>() {
        }.getType());
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public AtletaDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        AtletaEntity atletaEntity = atletaService.getAtleta(id);
        return modelMapper.map(atletaEntity, AtletaDetailDTO.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public AtletaDTO create(@RequestBody AtletaDTO atletaDTO)
            throws IllegalOperationException, EntityNotFoundException {
        AtletaEntity atletaEntity = atletaService.createAtleta(modelMapper.map(atletaDTO, AtletaEntity.class));
        return modelMapper.map(atletaEntity, AtletaDTO.class);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public AtletaDTO update(@PathVariable("id") Long id, @RequestBody AtletaDTO atletaDTO)
            throws EntityNotFoundException, IllegalOperationException {
        AtletaEntity atletaEntity = atletaService.updateAtleta(id, modelMapper.map(atletaDTO, AtletaEntity.class));
        return modelMapper.map(atletaEntity, AtletaDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        atletaService.deleteAtleta(id);
    }
}
