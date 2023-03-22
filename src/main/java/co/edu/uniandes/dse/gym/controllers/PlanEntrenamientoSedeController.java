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

import co.edu.uniandes.dse.gym.dto.SedeDTO;
import co.edu.uniandes.dse.gym.dto.SedeDetailDTO;
import co.edu.uniandes.dse.gym.entities.SedeEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.services.PlanEntrenamientoSedeService;

@RestController
@RequestMapping("/planEntrenamientos")
public class PlanEntrenamientoSedeController {

	@Autowired
	private PlanEntrenamientoSedeService planEntrenamientoSedeService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping(value = "/{planEntrenamientoId}/sedes/{sedeId}")
	@ResponseStatus(code = HttpStatus.OK)
	public SedeDetailDTO addSede(@PathVariable("sedeId") Long sedeId, @PathVariable("planEntrenamientoId") Long planEntrenamientoId)
			throws EntityNotFoundException {
		SedeEntity sedeEntity = planEntrenamientoSedeService.addSede(planEntrenamientoId, sedeId);
		return modelMapper.map(sedeEntity, SedeDetailDTO.class);
	}

	@GetMapping(value = "/{planEntrenamientoId}/sedes/{sedeId}")
	@ResponseStatus(code = HttpStatus.OK)
	public SedeDetailDTO getSede(@PathVariable("sedeId") Long sedeId, @PathVariable("planEntrenamientoId") Long planEntrenamientoId)
			throws EntityNotFoundException, IllegalOperationException {
		SedeEntity sedeEntity = planEntrenamientoSedeService.getSede(planEntrenamientoId, sedeId);
		return modelMapper.map(sedeEntity, SedeDetailDTO.class);
	}

	@PutMapping(value = "/{planEntrenamientoId}/sedes")
	@ResponseStatus(code = HttpStatus.OK)
	public List<SedeDetailDTO> addSedes(@PathVariable("planEntrenamientoId") Long planEntrenamientoId, @RequestBody List<SedeDTO> sedes)
			throws EntityNotFoundException {
		List<SedeEntity> entities = modelMapper.map(sedes, new TypeToken<List<SedeEntity>>() {
		}.getType());
		List<SedeEntity> sedesList = planEntrenamientoSedeService.replaceSedes(planEntrenamientoId, entities);
		return modelMapper.map(sedesList, new TypeToken<List<SedeDetailDTO>>() {
		}.getType());
	}

	@GetMapping(value = "/{planEntrenamientoId}/sedes")
	@ResponseStatus(code = HttpStatus.OK)
	public List<SedeDetailDTO> getSedes(@PathVariable("planEntrenamientoId") Long planEntrenamientoId) throws EntityNotFoundException {
		List<SedeEntity> sedeEntity = planEntrenamientoSedeService.getSedes(planEntrenamientoId);
		return modelMapper.map(sedeEntity, new TypeToken<List<SedeDetailDTO>>() {
		}.getType());
	}

	@DeleteMapping(value = "/{planEntrenamientoId}/sedes/{sedeId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removeSede(@PathVariable("sedeId") Long sedeId, @PathVariable("planEntrenamientoId") Long planEntrenamientoId)
			throws EntityNotFoundException {
		planEntrenamientoSedeService.removeSede(planEntrenamientoId, sedeId);
	}
}