package co.edu.uniandes.dse.gym.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;

import co.edu.uniandes.dse.gym.services.ActividadService;
import co.edu.uniandes.dse.gym.dto.ActividadDTO;
import co.edu.uniandes.dse.gym.dto.ActividadDetailDTO;
import co.edu.uniandes.dse.gym.entities.ActividadEntity;    
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
}
