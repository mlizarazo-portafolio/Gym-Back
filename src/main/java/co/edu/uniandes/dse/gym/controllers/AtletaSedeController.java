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
import co.edu.uniandes.dse.gym.dto.SedeDTO;
import co.edu.uniandes.dse.gym.entities.AtletaEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.services.AtletaSedeService;

@RestController
@RequestMapping("/atletas")
public class AtletaSedeController {

    @Autowired
    private AtletaSedeService atletaSedeService;

    @Autowired
    private ModelMapper modelMapper;

    @PutMapping(value = "/{atletaId}/sede")
    @ResponseStatus(code = HttpStatus.OK)
    public AtletaDetailDTO replaceSede(@PathVariable("atletaId") Long atletaId, @RequestBody SedeDTO sedeDTO)
            throws EntityNotFoundException {
        AtletaEntity atletaEntity = atletaSedeService.replaceSede(atletaId, sedeDTO.getId());
        return modelMapper.map(atletaEntity, AtletaDetailDTO.class);
    }

}
