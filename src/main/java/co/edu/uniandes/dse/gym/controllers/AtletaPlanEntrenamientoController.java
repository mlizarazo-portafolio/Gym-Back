package co.edu.uniandes.dse.gym.controllers;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.gym.dto.AtletaDetailDTO;
import co.edu.uniandes.dse.gym.dto.PlanEntrenamientoDTO;
import co.edu.uniandes.dse.gym.entities.AtletaEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.services.AtletaPlanEntrenamientoService;

@RestController
@RequestMapping("/atletas")
public class AtletaPlanEntrenamientoController {

    @Autowired
    private AtletaPlanEntrenamientoService atletaPlanEntrenamientoService;

    @Autowired
    private ModelMapper modelMapper;

    @PutMapping(value = "/{atletaId}/planEntrenamiento")
    @ResponseStatus(code = HttpStatus.OK)
    public AtletaDetailDTO replacePlanEntrenamiento(@PathVariable("atletaId") Long atletaId,
            @RequestBody PlanEntrenamientoDTO planEntrenamientoDTO)
            throws EntityNotFoundException {
        AtletaEntity atletaEntity = atletaPlanEntrenamientoService.replacePlanEntrenamiento(atletaId,
                planEntrenamientoDTO.getId());
        return modelMapper.map(atletaEntity, AtletaDetailDTO.class);
    }

}
