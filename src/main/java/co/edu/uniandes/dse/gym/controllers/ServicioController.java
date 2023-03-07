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
import co.edu.uniandes.dse.gym.services.ServicioService;
import co.edu.uniandes.dse.gym.dto.ServicioDTO;
import co.edu.uniandes.dse.gym.dto.ServicioDetailDTO;
import co.edu.uniandes.dse.gym.entities.ServicioEntity;

@RestController
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private ModelMapper modelMapper;
    
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ServicioDetailDTO> findAll() {
        List<ServicioEntity> servicio = servicioService.getServicios();
        return modelMapper.map(servicio, new TypeToken<List<ServicioDetailDTO>>() {}.getType()); 
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ServicioDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        ServicioEntity servicioEntity = servicioService.getServicioById(id);
        return modelMapper.map(servicioEntity, ServicioDetailDTO.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ServicioDTO create(@RequestBody ServicioDTO servicioDTO) throws IllegalOperationException, EntityNotFoundException {
            ServicioEntity servicioEntity = servicioService.createServicio(modelMapper.map(servicioDTO, ServicioEntity.class));
            return modelMapper.map(servicioEntity, ServicioDTO.class);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ServicioDTO update(@PathVariable("id") Long id, @RequestBody ServicioDTO servicioDTO)
                            throws EntityNotFoundException, IllegalOperationException {
            ServicioEntity servicioEntity = servicioService.updateServicio(id, modelMapper.map(servicioDTO, ServicioEntity.class));
            return modelMapper.map(servicioEntity, ServicioDTO.class);
    }    
    
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
            servicioService.deleteServicio(id);
    }

}