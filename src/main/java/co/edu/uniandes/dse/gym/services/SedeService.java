package co.edu.uniandes.dse.gym.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.gym.entities.SedeEntity;
import co.edu.uniandes.dse.gym.repositories.SedeRepository;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.ErrorMessage;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SedeService {
    
    @Autowired
    SedeRepository sedeRepository;

    @Transactional
    public SedeEntity createSede(SedeEntity sedeEntity) throws EntityNotFoundException, IllegalOperationException {
        
        log.info("Inicio proceso de creación de Sede");

        if(sedeEntity.getNombre() == null) throw new IllegalOperationException("Nombre no es válido");
        if(sedeEntity.getDireccion() == null) throw new IllegalOperationException("Direccion no es válida");
        if(sedeEntity.getTelefono() == null) throw new IllegalOperationException("Telefono no es válido");
        
        log.info("Termina proceso de creación de Sede");

        return sedeRepository.save(sedeEntity);
    }

    @Transactional
    public List<SedeEntity> getSedes() {
        log.info("Inicia proceso de consultar todos los sedes");
        return sedeRepository.findAll();
    }

    @Transactional
    public SedeEntity getSedeById(Long sedeId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar la Sede con id = {0}", sedeId);
        Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);

        if(sedeEntity.isEmpty()) throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

        log.info("Finaliza proceso de consultar la Sede con id = {0}", sedeId);
        return sedeEntity.get();
    }
    
    @Transactional
    public SedeEntity updateSede(Long sedeId, SedeEntity sede) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de actualizar la sede con id = {0}", sedeId);

        Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);

        if (sedeEntity.isEmpty()) throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

        sede.setId(sedeId);

        log.info("Termina proceso de actualizar la sede con id = {0}", sedeId);
            
        return sedeRepository.save(sede);
    }
    
    @Transactional
    public void deleteSede(Long sedeId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de borrar la sede con id = {0}", sedeId);

        Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);

        if (sedeEntity.isEmpty()) throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

        sedeRepository.deleteById(sedeId);
        
        log.info("Termina proceso de borrar la sede con id = {0}", sedeId);
    }

}