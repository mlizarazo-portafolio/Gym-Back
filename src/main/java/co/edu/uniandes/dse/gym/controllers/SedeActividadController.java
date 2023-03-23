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

import co.edu.uniandes.dse.gym.dto.ActividadDTO;
import co.edu.uniandes.dse.gym.dto.ActividadDetailDTO;
import co.edu.uniandes.dse.gym.entities.ActividadEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.services.SedeActividadService;


@RestController
@RequestMapping("/sedes")
public class SedeActividadController {

    @Autowired
    private SedeActividadService sedeActividadService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/{sedeId}/actividades/{actividadId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ActividadDetailDTO getActividad(@PathVariable("sedeId") Long sedeId, @PathVariable("actividadId") Long actividadId)
			throws EntityNotFoundException, IllegalOperationException {
		ActividadEntity actividadEntity = sedeActividadService.getActividad(sedeId, actividadId);
		return modelMapper.map(actividadEntity, ActividadDetailDTO.class);
	}

    @GetMapping(value = "/{sedeId}/actividades")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ActividadDetailDTO> getActividades(@PathVariable("sedeId") Long sedeId) throws EntityNotFoundException {
		List<ActividadEntity> actividadEntity = sedeActividadService.getActividades(sedeId);
		return modelMapper.map(actividadEntity, new TypeToken<List<ActividadDetailDTO>>() {
		}.getType());
	}

    @PostMapping(value = "/{sedeId}/actividades/{actividadId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ActividadDetailDTO addActividad(@PathVariable("sedeId") Long sedeId, @PathVariable("actividadId") Long actividadId)
			throws EntityNotFoundException {
		ActividadEntity actividadEntity = sedeActividadService.addActividad(sedeId, actividadId);
		return modelMapper.map(actividadEntity, ActividadDetailDTO.class);
	}

    @PutMapping(value = "/{sedeId}/actividades")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ActividadDetailDTO> replaceActividades(@PathVariable("sedeId") Long sedeId, @RequestBody List<ActividadDTO> actividades)
			throws EntityNotFoundException {
		List<ActividadEntity> entities = modelMapper.map(actividades, new TypeToken<List<ActividadEntity>>() {
		}.getType());
		List<ActividadEntity> actividadesList = sedeActividadService.addActividades(sedeId, entities);
		return modelMapper.map(actividadesList, new TypeToken<List<ActividadDetailDTO>>() {
		}.getType());

	}

    @DeleteMapping(value = "/{sedeId}/actividades/{actividadId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removeActividad(@PathVariable("sedeId") Long sedeId, @PathVariable("actividadId") Long actividadId)
			throws EntityNotFoundException {
		sedeActividadService.removeActividad(sedeId, actividadId);
	}
    
}
