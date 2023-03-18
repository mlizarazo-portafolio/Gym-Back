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

import co.edu.uniandes.dse.gym.dto.PlanEntrenamientoDTO;
import co.edu.uniandes.dse.gym.dto.PlanEntrenamientoDetailDTO;
import co.edu.uniandes.dse.gym.entities.PlanEntrenamientoEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.services.SedePlanEntrenamientoService;

@RestController
@RequestMapping("/sedes")
public class SedePlanEntrenamientoController {

	@Autowired
	private SedePlanEntrenamientoService sedePlanEntrenamientoService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping(value = "/{sedeId}/planEntrenamientos/{planEntrenamientoId}")
	@ResponseStatus(code = HttpStatus.OK)
	public PlanEntrenamientoDetailDTO addPlanEntrenamiento(@PathVariable("planEntrenamientoId") Long planEntrenamientoId, @PathVariable("sedeId") Long sedeId)
			throws EntityNotFoundException {
		PlanEntrenamientoEntity planEntrenamientoEntity = sedePlanEntrenamientoService.addPlanEntrenamiento(sedeId, planEntrenamientoId);
		return modelMapper.map(planEntrenamientoEntity, PlanEntrenamientoDetailDTO.class);
	}

	@GetMapping(value = "/{sedeId}/planEntrenamientos/{planEntrenamientoId}")
	@ResponseStatus(code = HttpStatus.OK)
	public PlanEntrenamientoDetailDTO getPlanEntrenamiento(@PathVariable("planEntrenamientoId") Long planEntrenamientoId, @PathVariable("sedeId") Long sedeId)
			throws EntityNotFoundException, IllegalOperationException {
		PlanEntrenamientoEntity planEntrenamientoEntity = sedePlanEntrenamientoService.getPlanEntrenamiento(sedeId, planEntrenamientoId);
		return modelMapper.map(planEntrenamientoEntity, PlanEntrenamientoDetailDTO.class);
	}

	@PutMapping(value = "/{sedeId}/planEntrenamientos")
	@ResponseStatus(code = HttpStatus.OK)
	public List<PlanEntrenamientoDetailDTO> addPlanEntrenamientos(@PathVariable("sedeId") Long sedeId, @RequestBody List<PlanEntrenamientoDTO> planEntrenamientos)
			throws EntityNotFoundException {
		List<PlanEntrenamientoEntity> entities = modelMapper.map(planEntrenamientos, new TypeToken<List<PlanEntrenamientoEntity>>() {
		}.getType());
		List<PlanEntrenamientoEntity> planEntrenamientosList = sedePlanEntrenamientoService.replacePlanEntrenamientos(sedeId, entities);
		return modelMapper.map(planEntrenamientosList, new TypeToken<List<PlanEntrenamientoDetailDTO>>() {
		}.getType());
	}

	@GetMapping(value = "/{sedeId}/planEntrenamientos")
	@ResponseStatus(code = HttpStatus.OK)
	public List<PlanEntrenamientoDetailDTO> getPlanEntrenamientos(@PathVariable("sedeId") Long sedeId) throws EntityNotFoundException {
		List<PlanEntrenamientoEntity> planEntrenamientoEntity = sedePlanEntrenamientoService.getPlanEntrenamientos(sedeId);
		return modelMapper.map(planEntrenamientoEntity, new TypeToken<List<PlanEntrenamientoDetailDTO>>() {
		}.getType());
	}

	@DeleteMapping(value = "/{sedeId}/planEntrenamientos/{planEntrenamientoId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removePlanEntrenamiento(@PathVariable("planEntrenamientoId") Long planEntrenamientoId, @PathVariable("sedeId") Long sedeId)
			throws EntityNotFoundException {
		sedePlanEntrenamientoService.removePlanEntrenamiento(sedeId, planEntrenamientoId);
	}
}