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

import co.edu.uniandes.dse.gym.dto.RestriccionDTO;
import co.edu.uniandes.dse.gym.dto.RestriccionDetailDTO;
import co.edu.uniandes.dse.gym.entities.RestriccionEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.services.RestriccionService;

@RestController
@RequestMapping("/restricciones")
public class RestriccionController {

    @Autowired
    private RestriccionService restriccionService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<RestriccionDetailDTO> findAll() {
        List<RestriccionEntity> restricciones = restriccionService.getRestricciones();
        return modelMapper.map(restricciones, new TypeToken<List<RestriccionDetailDTO>>() {
        }.getType());
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public RestriccionDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        RestriccionEntity restriccionEntity = restriccionService.getRestriccion(id);
        return modelMapper.map(restriccionEntity, RestriccionDetailDTO.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public RestriccionDTO create(@RequestBody RestriccionDTO restriccionDTO)
            throws IllegalOperationException, EntityNotFoundException {
        RestriccionEntity restriccionEntity = restriccionService
                .createRestriccion(modelMapper.map(restriccionDTO, RestriccionEntity.class));
        return modelMapper.map(restriccionEntity, RestriccionDTO.class);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public RestriccionDTO update(@PathVariable("id") Long id, @RequestBody RestriccionDTO restriccionDTO)
            throws EntityNotFoundException, IllegalOperationException {
        RestriccionEntity restriccionEntity = restriccionService.updateRestriccion(id,
                modelMapper.map(restriccionDTO, RestriccionEntity.class));
        return modelMapper.map(restriccionEntity, RestriccionDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        restriccionService.deleteRestriccion(id);
    }
}
