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
import co.edu.uniandes.dse.gym.dto.DeportologoDTO;
import co.edu.uniandes.dse.gym.entities.AtletaEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.services.AtletaDeportologoService;

@RestController
@RequestMapping("/atletas")
public class AtletaDeportologoController {
    @Autowired
    private AtletaDeportologoService ateltaDeportologoService;

    @Autowired
    private ModelMapper modelMapper;

    @PutMapping(value = "/{atletaId}/deportologo")
    @ResponseStatus(code = HttpStatus.OK)
    public AtletaDetailDTO replaceDeportologo(@PathVariable("atletaId") Long atletaId, @RequestBody DeportologoDTO deportologoDTO)
            throws EntityNotFoundException {
        AtletaEntity atletaEntity = ateltaDeportologoService.replaceDeportologo(atletaId, deportologoDTO.getId());
        return modelMapper.map(atletaEntity, AtletaDetailDTO.class);
    }
}
