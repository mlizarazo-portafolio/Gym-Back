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
import co.edu.uniandes.dse.gym.services.SedeAtletaService;

@RestController
@RequestMapping("/sedes")
public class SedeAtletaController {

	@Autowired
	private SedeAtletaService sedeAtletaService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping(value = "/{sedeId}/atletas/{atletaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public AtletaDTO addAtleta(@PathVariable("sedeId") Long sedeId, @PathVariable("atletaId") Long atletaId)
			throws EntityNotFoundException {
		AtletaEntity atletaEntity = sedeAtletaService.addAtleta(atletaId, sedeId);
		return modelMapper.map(atletaEntity, AtletaDTO.class);
	}

	@GetMapping(value = "/{sedeId}/atletas")
	@ResponseStatus(code = HttpStatus.OK)
	public List<AtletaDetailDTO> getAtletas(@PathVariable("sedeId") Long sedeId) throws EntityNotFoundException {
		List<AtletaEntity> atletaList = sedeAtletaService.getAtletas(sedeId);
		return modelMapper.map(atletaList, new TypeToken<List<AtletaDetailDTO>>() {
		}.getType());
	}

	@GetMapping(value = "/{sedeId}/atletas/{atletaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public AtletaDetailDTO getAtleta(@PathVariable("sedeId") Long sedeId, @PathVariable("atletaId") Long atletaId)
			throws EntityNotFoundException, IllegalOperationException {
		AtletaEntity atletaEntity = sedeAtletaService.getAtleta(sedeId, atletaId);
		return modelMapper.map(atletaEntity, AtletaDetailDTO.class);
	}

	@PutMapping(value = "/{sedeId}/atletas")
	@ResponseStatus(code = HttpStatus.OK)
	public List<AtletaDetailDTO> replaceAtletas(@PathVariable("sedeId") Long sedesId,
			@RequestBody List<AtletaDetailDTO> atletas) throws EntityNotFoundException {
		List<AtletaEntity> atletasList = modelMapper.map(atletas, new TypeToken<List<AtletaEntity>>() {
		}.getType());
		List<AtletaEntity> result = sedeAtletaService.replaceAtletas(sedesId, atletasList);
		return modelMapper.map(result, new TypeToken<List<AtletaDetailDTO>>() {
		}.getType());
	}

}
