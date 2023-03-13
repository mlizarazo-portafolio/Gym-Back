package co.edu.uniandes.dse.gym.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;


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
import org.springframework.http.HttpStatus;

import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.services.ReseniaService;
import co.edu.uniandes.dse.gym.dto.ReseniaDetailDTO;
import co.edu.uniandes.dse.gym.dto.ReseniaDTO;
import co.edu.uniandes.dse.gym.entities.ReseniaEntity;

@RestController
@RequestMapping("/resenias")
public class ReseniaController {

    @Autowired
    private ReseniaService reseniaService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ReseniaDTO create(@RequestBody ReseniaDTO reseniaDTO) throws IllegalOperationException, EntityNotFoundException {
            ReseniaEntity reseniaEntity = reseniaService.createResenia(modelMapper.map(reseniaDTO, ReseniaEntity.class));
            return modelMapper.map(reseniaEntity, ReseniaDTO.class);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ReseniaDetailDTO> findAll(){
        List<ReseniaEntity> reseniaes = reseniaService.getResenias();
        return modelMapper.map(reseniaes, new TypeToken<List<ReseniaDetailDTO>>() {
        }.getType());
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ReseniaDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
            ReseniaEntity reseniaEntity = reseniaService.getReseniaById(id);
            return modelMapper.map(reseniaEntity, ReseniaDetailDTO.class);
    }  
    
    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ReseniaDTO update(@PathVariable("id") Long id, @RequestBody ReseniaDTO reseniaDTO)
                            throws EntityNotFoundException, IllegalOperationException {
            ReseniaEntity reseniaEntity = reseniaService.updateResenia(id, modelMapper.map(reseniaDTO, ReseniaEntity.class));
            return modelMapper.map(reseniaEntity, ReseniaDTO.class);
    }    
    
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
            reseniaService.deleteResenia(id);
    }      
    
}
