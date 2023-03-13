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

import co.edu.uniandes.dse.gym.dto.ConvenioDTO;
import co.edu.uniandes.dse.gym.dto.ConvenioDetailDTO;
import co.edu.uniandes.dse.gym.entities.ConvenioEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.services.ConvenioService;


@RestController
@RequestMapping("/convenios")
public class ConvenioController {


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ConvenioService convenioService;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ConvenioDetailDTO> findAlL(){
        List<ConvenioEntity> planes = convenioService.getConvenios();
        return modelMapper.map(planes, ((TypeToken<List<ConvenioDetailDTO>>) new TypeToken<List<ConvenioDetailDTO>>(){
        }).getType());
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ConvenioDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException{
        ConvenioEntity convenioEntity = convenioService.getConvenio(id);
        return modelMapper.map(convenioEntity, ConvenioDetailDTO.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ConvenioDTO create(@RequestBody ConvenioDTO convenioDTO) throws IllegalOperationException, EntityNotFoundException{
        ConvenioEntity convenioEntity = convenioService.crearConvenio(modelMapper.map(convenioDTO, ConvenioEntity.class));
        return modelMapper.map(convenioEntity, ConvenioDTO.class);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ConvenioDTO update(@PathVariable("id") Long id, @RequestBody ConvenioDTO convenioDTO)throws EntityNotFoundException, IllegalOperationException{
        ConvenioEntity convenioEntity = convenioService.updateConvenio(id, modelMapper.map(convenioDTO, ConvenioEntity.class));
        return modelMapper.map(convenioEntity, ConvenioDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException,IllegalOperationException{
        convenioService.deletConvenio(id);
    }
}
