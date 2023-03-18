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

import co.edu.uniandes.dse.gym.dto.ReseniaDTO;
import co.edu.uniandes.dse.gym.dto.ReseniaDetailDTO;
import co.edu.uniandes.dse.gym.entities.ReseniaEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.services.SedeReseniaService;

@RestController
@RequestMapping("/sedes")
public class SedeReseniaController {

    @Autowired
    private SedeReseniaService sedeReseniaService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "/{sedeId}/resenias/{reseniaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ReseniaDTO addResenia(@PathVariable("sedeId") Long sedeId, @PathVariable("reseniaId") Long reseniaId)
			throws EntityNotFoundException {
		ReseniaEntity reseniaEntity = sedeReseniaService.addResenia(reseniaId, sedeId);
		return modelMapper.map(reseniaEntity, ReseniaDTO.class);
	}

    @GetMapping(value = "/{sedeId}/resenias")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ReseniaDetailDTO> getResenias(@PathVariable("sedeId") Long sedeId) throws EntityNotFoundException {
		List<ReseniaEntity> reseniaList = sedeReseniaService.getResenias(sedeId);
		return modelMapper.map(reseniaList, new TypeToken<List<ReseniaDetailDTO>>() {
		}.getType());
	}

    @GetMapping(value = "/{sedeId}/resenias/{reseniaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ReseniaDetailDTO getResenia(@PathVariable("sedeId") Long sedeId, @PathVariable("reseniaId") Long reseniaId)
			throws EntityNotFoundException, IllegalOperationException {
		ReseniaEntity reseniaEntity = sedeReseniaService.getResenia(sedeId, reseniaId);
		return modelMapper.map(reseniaEntity, ReseniaDetailDTO.class);
	}

    @PutMapping(value = "/{sedeId}/resenias")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ReseniaDetailDTO> replaceResenias(@PathVariable("sedeId") Long sedesId,
			@RequestBody List<ReseniaDetailDTO> resenias) throws EntityNotFoundException {
		List<ReseniaEntity> reseniasList = modelMapper.map(resenias, new TypeToken<List<ReseniaEntity>>() {
		}.getType());
		List<ReseniaEntity> result = sedeReseniaService.replaceResenias(sedesId, reseniasList);
		return modelMapper.map(result, new TypeToken<List<ReseniaDetailDTO>>() {
		}.getType());
	}
    
}
