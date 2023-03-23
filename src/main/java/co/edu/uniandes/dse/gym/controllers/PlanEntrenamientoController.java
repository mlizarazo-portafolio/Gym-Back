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
import co.edu.uniandes.dse.gym.services.PlanEntrenamientoService;

@RestController
@RequestMapping("/planes")
public class PlanEntrenamientoController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PlanEntrenamientoService planEntrenamientoService;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<PlanEntrenamientoDetailDTO> findAlL(){
        List<PlanEntrenamientoEntity> planes = planEntrenamientoService.getPlanes();
        return modelMapper.map(planes, ((TypeToken<List<PlanEntrenamientoDetailDTO>>) new TypeToken<List<PlanEntrenamientoDetailDTO>>(){
        }).getType());
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public PlanEntrenamientoDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException{
        PlanEntrenamientoEntity planEntrenamientoEntity = planEntrenamientoService.getPlanById(id);
        return modelMapper.map(planEntrenamientoEntity, PlanEntrenamientoDetailDTO.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public PlanEntrenamientoDTO create(@RequestBody PlanEntrenamientoDTO planEntrenamientoDTO) throws IllegalOperationException, EntityNotFoundException{
        PlanEntrenamientoEntity planEntrenamientoEntity = planEntrenamientoService.crearPlan(modelMapper.map(planEntrenamientoDTO, PlanEntrenamientoEntity.class));
        return modelMapper.map(planEntrenamientoEntity, PlanEntrenamientoDTO.class);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public PlanEntrenamientoDTO update(@PathVariable("id") Long id, @RequestBody PlanEntrenamientoDTO planEntrenamientoDTO)throws EntityNotFoundException, IllegalOperationException{
        PlanEntrenamientoEntity planEntrenamientoEntity = planEntrenamientoService.updatePlan(id, modelMapper.map(planEntrenamientoDTO, PlanEntrenamientoEntity.class));
        return modelMapper.map(planEntrenamientoEntity, PlanEntrenamientoDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException,IllegalOperationException{
        planEntrenamientoService.deletePlan(id);
    }


}
