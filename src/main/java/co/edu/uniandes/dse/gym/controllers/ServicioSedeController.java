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

import co.edu.uniandes.dse.gym.dto.ServicioDetailDTO;
import co.edu.uniandes.dse.gym.dto.SedeDTO;
import co.edu.uniandes.dse.gym.entities.ServicioEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.services.ServicioSedeService;

@RestController
@RequestMapping("/servicios")
public class ServicioSedeController {

    @Autowired
    private ServicioSedeService servicioSedeService;

    @Autowired
    private ModelMapper modelMapper;

    @PutMapping(value = "/{servicioId}/sede")
	@ResponseStatus(code = HttpStatus.OK)
	public ServicioDetailDTO replaceSede(@PathVariable("servicioId") Long servicioId, @RequestBody SedeDTO sedeDTO)
			throws EntityNotFoundException {
		ServicioEntity servicioEntity = servicioSedeService.replaceSede(servicioId, sedeDTO.getId());
		return modelMapper.map(servicioEntity, ServicioDetailDTO.class);
	}

}