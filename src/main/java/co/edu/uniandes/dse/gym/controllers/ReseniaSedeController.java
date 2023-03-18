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

import co.edu.uniandes.dse.gym.dto.ReseniaDetailDTO;
import co.edu.uniandes.dse.gym.dto.SedeDTO;
import co.edu.uniandes.dse.gym.entities.ReseniaEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.services.ReseniaSedeService;

@RestController
@RequestMapping("/resenias")
public class ReseniaSedeController {

    @Autowired
    private ReseniaSedeService reseniaSedeService;

    @Autowired
    private ModelMapper modelMapper;

    @PutMapping(value = "/{reseniaId}/sede")
	@ResponseStatus(code = HttpStatus.OK)
	public ReseniaDetailDTO replaceSede(@PathVariable("reseniaId") Long reseniaId, @RequestBody SedeDTO sedeDTO)
			throws EntityNotFoundException {
		ReseniaEntity reseniaEntity = reseniaSedeService.replaceSede(reseniaId, sedeDTO.getId());
		return modelMapper.map(reseniaEntity, ReseniaDetailDTO.class);
	}

}
