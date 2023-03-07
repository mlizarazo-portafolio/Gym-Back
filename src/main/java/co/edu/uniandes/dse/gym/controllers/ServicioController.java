package co.edu.uniandes.dse.gym.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

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

}