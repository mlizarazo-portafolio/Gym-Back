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
import co.edu.uniandes.dse.gym.services.PlanEntrenamientoAtletaService;

@RestController
@RequestMapping("/planEntrenamientos")
public class PlanEntrenamientoAtletaController {

    @Autowired
    private PlanEntrenamientoAtletaService planEntrenamientoAtletaService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "/{planEntrenamientoId}/atletas/{atletaId}")
    @ResponseStatus(code = HttpStatus.OK)
    public AtletaDTO addAtleta(@PathVariable("planEntrenamientoId") Long planEntrenamientoId,
            @PathVariable("atletaId") Long atletaId)
            throws EntityNotFoundException {
        AtletaEntity atletaEntity = planEntrenamientoAtletaService.addAtleta(atletaId, planEntrenamientoId);
        return modelMapper.map(atletaEntity, AtletaDTO.class);
    }

    @GetMapping(value = "/{planEntrenamientoId}/atletas")
    @ResponseStatus(code = HttpStatus.OK)
    public List<AtletaDetailDTO> getAtletas(@PathVariable("planEntrenamientoId") Long planEntrenamientoId)
            throws EntityNotFoundException {
        List<AtletaEntity> atletaList = planEntrenamientoAtletaService.getAtletas(planEntrenamientoId);
        return modelMapper.map(atletaList, new TypeToken<List<AtletaDetailDTO>>() {
        }.getType());
    }

    @GetMapping(value = "/{planEntrenamientoId}/atletas/{atletaId}")
    @ResponseStatus(code = HttpStatus.OK)
    public AtletaDetailDTO getAtleta(@PathVariable("planEntrenamientoId") Long planEntrenamientoId,
            @PathVariable("atletaId") Long atletaId)
            throws EntityNotFoundException, IllegalOperationException {
        AtletaEntity atletaEntity = planEntrenamientoAtletaService.getAtleta(planEntrenamientoId, atletaId);
        return modelMapper.map(atletaEntity, AtletaDetailDTO.class);
    }

    @PutMapping(value = "/{planEntrenamientoId}/atletas")
    @ResponseStatus(code = HttpStatus.OK)
    public List<AtletaDetailDTO> replaceAtletas(@PathVariable("planEntrenamientoId") Long planEntrenamientosId,
            @RequestBody List<AtletaDetailDTO> atletas) throws EntityNotFoundException {
        List<AtletaEntity> atletasList = modelMapper.map(atletas, new TypeToken<List<AtletaEntity>>() {
        }.getType());
        List<AtletaEntity> result = planEntrenamientoAtletaService.replaceAtletas(planEntrenamientosId, atletasList);
        return modelMapper.map(result, new TypeToken<List<AtletaDetailDTO>>() {
        }.getType());
    }

}