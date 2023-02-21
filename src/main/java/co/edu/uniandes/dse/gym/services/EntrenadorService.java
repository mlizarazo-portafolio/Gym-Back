package co.edu.uniandes.dse.gym.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.exceptions.ErrorMessage;
import co.edu.uniandes.dse.gym.repositories.EntrenadorRepository;
import lombok.extern.slf4j.Slf4j;
import co.edu.uniandes.dse.gym.entities.EntrenadorEntity;

@Slf4j
@Service
public class EntrenadorService {

    @Autowired
    EntrenadorRepository entrenadorRepository;

    @Transactional
    public EntrenadorEntity createEntrenador (EntrenadorEntity entrenadorEntity) throws EntityNotFoundException, IllegalOperationException{
        log.info("Inicia proceso de creacion de Entrenador");

        if (!validateString(entrenadorEntity.getNombre())){
            throw new IllegalOperationException("Nombre no es valido");
        }

        if(!entrenadorRepository.findByNombre(entrenadorEntity.getNombre()).isEmpty()){
            throw new IllegalOperationException("Ya existe un entrenador con ese nombre");
        }

        if (!validateString(entrenadorEntity.getFoto())){
            throw new IllegalOperationException("Foto no es valida");
        }
        if (!validateString(entrenadorEntity.getTrayectoria())){
            throw new IllegalOperationException("Trayectoria no es valida");
        }
        
        log.info("Termina proceso de creacion de Entrenador");
        return entrenadorRepository.save(entrenadorEntity);
    }

    @Transactional
    public List<EntrenadorEntity> getEntrenadores(){
        log.info("Inicia proceso de consultar todos los entrenadores");
        return entrenadorRepository.findAll();
    }

    @Transactional
    public EntrenadorEntity getEntrenadorById(Long entrenadorId) throws EntityNotFoundException{
        log.info("Inicia proceso de consultar el entrenador con id = {0}", entrenadorId);
        Optional<EntrenadorEntity> entrenadorEntity = entrenadorRepository.findById(entrenadorId);
        if(entrenadorEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.ENTRENADOR_NOT_FOUND);
        }
        log.info("Termina proceso de consultar el entrenador con id = {0}", entrenadorId);
        return entrenadorEntity.get();
    }

    @Transactional
    public EntrenadorEntity updateEntrenador(Long entrenadorId, EntrenadorEntity entrenador) throws EntityNotFoundException, IllegalOperationException{
        log.info("Inicia proceso de actualizar el entrenador con id = {0}", entrenadorId);
        Optional<EntrenadorEntity> entrenadorEntity = entrenadorRepository.findById(entrenadorId);
        if (entrenadorEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.ENTRENADOR_NOT_FOUND);
        }
        
        if (!validateString(entrenador.getNombre())){
            throw new IllegalOperationException("Nombre no es valido");
        }

        if(!entrenadorRepository.findByNombre(entrenador.getNombre()).isEmpty()){
            throw new IllegalOperationException("Ya existe un entrenador con ese nombre");
        }

        if (!validateString(entrenador.getFoto())){
            throw new IllegalOperationException("Foto no es valida");
        }
        if (!validateString(entrenador.getTrayectoria())){
            throw new IllegalOperationException("Trayectoria no es valida");
        }

        entrenador.setId(entrenadorId);
        log.info("Termina proceso de actualizar el entrenador con id = {0}", entrenadorId);
        return entrenadorRepository.save(entrenador);
    }

    @Transactional
    public void deleteEntrenador(Long entrenadorId) throws EntityNotFoundException, IllegalOperationException{
        log.info("Inicia proceso de borrar el entrenador con id = {0}", entrenadorId);
        Optional<EntrenadorEntity> entrenadorEntity = entrenadorRepository.findById(entrenadorId);
        if (entrenadorEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.ENTRENADOR_NOT_FOUND);
        }
        entrenadorRepository.deleteById(entrenadorId);
        log.info("Termina proceso de borrar el entrenador con id = {0}", entrenadorId);
    }

    public boolean validateString(String string){
        return !(string==null || string.isEmpty());
    }
    
}
