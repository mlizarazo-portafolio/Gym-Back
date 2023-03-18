package co.edu.uniandes.dse.gym.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.gym.entities.ConvenioEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.ErrorMessage;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.repositories.ConvenioRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConvenioService {
    
    @Autowired
    ConvenioRepository convenioRepository;


    @Transactional
    public ConvenioEntity crearConvenio(ConvenioEntity convenioEntity) throws IllegalOperationException{
        
        log.info("inicia el proceos de crear Plan de Entrenamiento");

        if(convenioEntity.getNombre() == null) throw new IllegalOperationException("Nombre no es válido");
        if(convenioEntity.getDescuento() == null) throw new IllegalOperationException("Descuento no es válida");
 
        log.info("Termina proceso de creación de Plan de Convenio");

        return convenioRepository.save(convenioEntity);
    }

    @Transactional
    public List<ConvenioEntity> getConvenios(){
        log.info("Inicia proceso de consultar todos los convenios");
        return convenioRepository.findAll();
    }

    @Transactional
    public ConvenioEntity getConvenio(Long convenioId) throws EntityNotFoundException{
        log.info("Inicia proceso de consultar el Convenio con id = {0}", convenioId);
        Optional < ConvenioEntity > convenioEntity = convenioRepository.findById( convenioId);

        if(convenioEntity.isEmpty())throw new EntityNotFoundException(ErrorMessage.CONVENIO_NOT_FOUND);
    
        log.info("Finaliza proceso de consultar el Convenio con id = {0}", convenioId);
        return convenioEntity.get();
    }


    @Transactional
    public ConvenioEntity updateConvenio(Long convenioId, ConvenioEntity convenio) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de actualizar el convenio con id = {0}", convenioId);

        Optional < ConvenioEntity > convenioEntity = convenioRepository.findById(convenioId);

        if (convenioEntity.isEmpty()) throw new EntityNotFoundException(ErrorMessage.CONVENIO_NOT_FOUND);

        convenio.setId(convenioId);

        log.info("Termina proceso de actualizar el convenio con id = {0}", convenioId);
            
        return convenioRepository.save(convenioId);
    }
    


    @Transactional
    public void deletConvenio(Long convenioId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de borrar el convenio con id = {0}", convenioId);

        Optional < ConvenioEntity > convenioEntity = convenioRepository.findById(convenioId);

        if (convenioEntity.isEmpty()) throw new EntityNotFoundException(ErrorMessage.CONVENIO_NOT_FOUND);

        convenioRepository.deleteById(convenioId);
        
        log.info("Termina proceso de borrar el convenio con id = {0}", convenioId);
    }

}   
