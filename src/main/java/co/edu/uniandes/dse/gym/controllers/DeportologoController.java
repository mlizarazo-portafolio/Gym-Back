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

import co.edu.uniandes.dse.gym.dto.DeportologoDTO;
import co.edu.uniandes.dse.gym.dto.DeportologoDetailDTO;
import co.edu.uniandes.dse.gym.entities.DeportologoEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.services.DeportologoService;

@RestController
@RequestMapping("/deportologos")
public class DeportologoController {

    @Autowired
    private DeportologoService deportologoService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<DeportologoDetailDTO> findAll() {
        List<DeportologoEntity> deportologos = deportologoService.getDeportologos();
        return modelMapper.map(deportologos, new TypeToken<List<DeportologoDetailDTO>>() {
        }.getType());
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public DeportologoDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        DeportologoEntity deportologoEntity = deportologoService.getDeportologo(id);
        return modelMapper.map(deportologoEntity, DeportologoDetailDTO.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public DeportologoDTO create(@RequestBody DeportologoDTO deportologoDTO)
            throws IllegalOperationException, EntityNotFoundException {
        DeportologoEntity deportologoEntity = deportologoService
                .createDeportologo(modelMapper.map(deportologoDTO, DeportologoEntity.class));
        return modelMapper.map(deportologoEntity, DeportologoDTO.class);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public DeportologoDTO update(@PathVariable("id") Long id, @RequestBody DeportologoDTO deportologoDTO)
            throws EntityNotFoundException, IllegalOperationException {
        DeportologoEntity deportologoEntity = deportologoService.updateDeportologo(id,
                modelMapper.map(deportologoDTO, DeportologoEntity.class));
        return modelMapper.map(deportologoEntity, DeportologoDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        deportologoService.deleteDeportologo(id);
    }

}
