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

import co.edu.uniandes.dse.gym.dto.AtletaDTO;
import co.edu.uniandes.dse.gym.dto.AtletaDetailDTO;
import co.edu.uniandes.dse.gym.entities.AtletaEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.services.ActividadAtletaService;

@RestController
@RequestMapping("/actividades")
public class ActividadAtletaController {
    @Autowired
    private ActividadAtletaService actividadAtletaService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "/{actividadId}/atletas/{atletaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public AtletaDetailDTO addAtleta(@PathVariable("atletaId") Long atletaId, @PathVariable("actividadId") Long actividadId)
			throws EntityNotFoundException {
		AtletaEntity atletaEntity = actividadAtletaService.addAtleta(actividadId, atletaId);
		return modelMapper.map(atletaEntity, AtletaDetailDTO.class);
	}

    @GetMapping(value = "/{actividadId}/atletas/{atletaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public AtletaDetailDTO getAtleta(@PathVariable("atletaId") Long atletaId, @PathVariable("actividadId") Long actividadId)
			throws EntityNotFoundException, IllegalOperationException {
		AtletaEntity atletaEntity = actividadAtletaService.getAtleta(actividadId, atletaId);
		return modelMapper.map(atletaEntity, AtletaDetailDTO.class);
	}

    @PutMapping(value = "/{actividadId}/atletas")
	@ResponseStatus(code = HttpStatus.OK)
	public List<AtletaDetailDTO> addAtletas(@PathVariable("actividadId") Long actividadId, @RequestBody List<AtletaDTO> atletas)
			throws EntityNotFoundException {
		List<AtletaEntity> entities = modelMapper.map(atletas, new TypeToken<List<AtletaEntity>>() {
		}.getType());
		List<AtletaEntity> atletasList = actividadAtletaService.replaceAtletas(actividadId, entities);
		return modelMapper.map(atletasList, new TypeToken<List<AtletaDetailDTO>>() {
		}.getType());
	}

    @GetMapping(value = "/{actividadId}/atletas")
	@ResponseStatus(code = HttpStatus.OK)
	public List<AtletaDetailDTO> getAtletas(@PathVariable("actividadId") Long actividadId) throws EntityNotFoundException {
		List<AtletaEntity> atletaEntity = actividadAtletaService.getAtletas(actividadId);
		return modelMapper.map(atletaEntity, new TypeToken<List<AtletaDetailDTO>>() {
		}.getType());
	}

    @DeleteMapping(value = "/{actividadId}/atletas/{atletaId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removeAtleta(@PathVariable("atletaId") Long atletaId, @PathVariable("actividadId") Long actividadId)
			throws EntityNotFoundException {
		actividadAtletaService.removeAtleta(actividadId, atletaId);
	}
}
