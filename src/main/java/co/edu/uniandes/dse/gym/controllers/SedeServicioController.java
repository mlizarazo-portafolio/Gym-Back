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

import co.edu.uniandes.dse.gym.dto.ServicioDTO;
import co.edu.uniandes.dse.gym.dto.ServicioDetailDTO;
import co.edu.uniandes.dse.gym.entities.ServicioEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.services.SedeServicioService;

@RestController
@RequestMapping("/sedes")
public class SedeServicioController {

    @Autowired
    private SedeServicioService sedeServicioService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "/{sedeId}/servicios/{servicioId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ServicioDTO addServicio(@PathVariable("sedeId") Long sedeId, @PathVariable("servicioId") Long servicioId)
			throws EntityNotFoundException {
		ServicioEntity servicioEntity = sedeServicioService.addServicio(servicioId, sedeId);
		return modelMapper.map(servicioEntity, ServicioDTO.class);
	}

    @GetMapping(value = "/{sedeId}/servicios")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ServicioDetailDTO> getServicios(@PathVariable("sedeId") Long sedeId) throws EntityNotFoundException {
		List<ServicioEntity> servicioList = sedeServicioService.getServicios(sedeId);
		return modelMapper.map(servicioList, new TypeToken<List<ServicioDetailDTO>>() {
		}.getType());
	}

    @GetMapping(value = "/{sedeId}/servicios/{servicioId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ServicioDetailDTO getServicio(@PathVariable("sedeId") Long sedeId, @PathVariable("servicioId") Long servicioId)
			throws EntityNotFoundException, IllegalOperationException {
		ServicioEntity servicioEntity = sedeServicioService.getServicio(sedeId, servicioId);
		return modelMapper.map(servicioEntity, ServicioDetailDTO.class);
	}

    @PutMapping(value = "/{sedeId}/servicios")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ServicioDetailDTO> replaceServicios(@PathVariable("sedeId") Long sedesId,
			@RequestBody List<ServicioDetailDTO> servicios) throws EntityNotFoundException {
		List<ServicioEntity> serviciosList = modelMapper.map(servicios, new TypeToken<List<ServicioEntity>>() {
		}.getType());
		List<ServicioEntity> result = sedeServicioService.replaceServicios(sedesId, serviciosList);
		return modelMapper.map(result, new TypeToken<List<ServicioDetailDTO>>() {
		}.getType());
	}
    
}