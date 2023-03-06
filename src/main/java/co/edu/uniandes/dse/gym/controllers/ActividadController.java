package co.edu.uniandes.dse.gym.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;

import co.edu.uniandes.dse.gym.services.ActividadService;
import co.edu.uniandes.dse.gym.dto.ActividadDTO;
import co.edu.uniandes.dse.gym.dto.ActividadDetailDTO;
import co.edu.uniandes.dse.gym.entities.ActividadEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;

import org.springframework.http.HttpStatus;
import org.modelmapper.TypeToken;

@RestController
@RequestMapping("/actividades")
public class ActividadController {
   
    
    @Autowired
    private ActividadService actividadService;

    @Autowired
	private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ActividadDetailDTO> findAll()
    {
        List<ActividadEntity> actividades = actividadService.getActividades();
        return modelMapper.map(actividades, new TypeToken<List<ActividadDetailDTO>>() {
		}.getType());

    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ActividadDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        ActividadEntity actividadEntity = actividadService.getActividad(id);
        return modelMapper.map(actividadEntity, ActividadDetailDTO.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ActividadDTO create(@RequestBody ActividadDTO actividadDTO) throws IllegalOperationException, EntityNotFoundException {
        ActividadEntity actividadEntity = actividadService.createActividad(modelMapper.map(actividadDTO, ActividadEntity.class));
        return modelMapper.map(actividadEntity, ActividadDTO.class);
     }

     @PutMapping(value = "/{id}")
     @ResponseStatus(code = HttpStatus.OK)
     public ActividadDTO update(@PathVariable("id") Long id, @RequestBody ActividadDTO actividadDTO)
                         throws EntityNotFoundException, IllegalOperationException {
         ActividadEntity ActividadEntity = actividadService.updateActividad(id, modelMapper.map(actividadDTO, ActividadEntity.class));
         return modelMapper.map(ActividadEntity, ActividadDTO.class);
     }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        actividadService.deleteActividad(id);
    }
}
