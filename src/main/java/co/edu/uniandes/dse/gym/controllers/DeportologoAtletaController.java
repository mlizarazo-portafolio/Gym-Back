package co.edu.uniandes.dse.gym.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import co.edu.uniandes.dse.gym.services.DeportologoAtletaService;

@RestController
@RequestMapping("/deportologos")
public class DeportologoAtletaController {
    

    @Autowired
    private DeportologoAtletaService deportologoAtletaService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "/{deportologoId}/atletas/{atletaId}")
    @ResponseStatus(code = HttpStatus.OK)
    public AtletaDTO addAtleta(@PathVariable("deportologoId") Long deportologoId, @PathVariable("atletaId") Long atletaId)
			throws EntityNotFoundException {
		AtletaEntity atletaEntity = deportologoAtletaService.addAtleta(atletaId, deportologoId);
		return modelMapper.map(atletaEntity, AtletaDTO.class);
	}

    @GetMapping(value = "/{deportologoId}/atletas")
	@ResponseStatus(code = HttpStatus.OK)
	public List<AtletaDetailDTO> getAtletas(@PathVariable("deportologoId") Long deportologoId) throws EntityNotFoundException {
		List<AtletaEntity> atletaList = deportologoAtletaService.getAtletas(deportologoId);
		return modelMapper.map(atletaList, new TypeToken<List<AtletaDetailDTO>>() {
		}.getType());
	}

    @GetMapping(value = "/{deportologoId}/atletas/{atletaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public AtletaDetailDTO getAtleta(@PathVariable("deportologoId") Long deportologoId, @PathVariable("atletaId") Long atletaId)
			throws EntityNotFoundException, IllegalOperationException {
		AtletaEntity atletaEntity = deportologoAtletaService.getAtleta(deportologoId, atletaId);
		return modelMapper.map(atletaEntity, AtletaDetailDTO.class);
	}

    @PutMapping(value = "/{deportologoId}/atletas")
	@ResponseStatus(code = HttpStatus.OK)
	public List<AtletaDetailDTO> replaceAtletas(@PathVariable("deportologoId") Long deportologosId,
			@RequestBody List<AtletaDetailDTO> atletas) throws EntityNotFoundException {
		List<AtletaEntity> atletasList = modelMapper.map(atletas, new TypeToken<List<AtletaEntity>>() {
		}.getType());
		List<AtletaEntity> result = deportologoAtletaService.replaceAtletas(deportologosId, atletasList);
		return modelMapper.map(result, new TypeToken<List<AtletaDetailDTO>>() {
		}.getType());
	}
}
