package co.edu.uniandes.dse.gym.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import co.edu.uniandes.dse.gym.repositories.ActividadRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Transactional;
import co.edu.uniandes.dse.gym.entities.ActividadEntity;
import co.edu.uniandes.dse.gym.entities.EntrenadorEntity;
import co.edu.uniandes.dse.gym.repositories.EntrenadorRepository;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.ErrorMessage;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ActividadService {

    @Autowired
    ActividadRepository actividadRepository;

    @Autowired
    EntrenadorRepository entrenadorRepository;

    @Transactional
    public ActividadEntity createActividad(ActividadEntity actividadEntity) throws IllegalOperationException {
        log.info("Inicia proceso de creación de la actividad");

        // Una actividad no puede existir si no tiene nombre
        if (actividadEntity.getNombre() == null) {
            throw new IllegalOperationException("La actividad no es valida");
        }

        // Una actividad no puede existir si no tiene un tipo
        if (actividadEntity.getTipo() == null) {
            throw new IllegalOperationException("La actividad no es valida");
        }

        // Una actividad no puede existir si no tiene un entrenador
        if (actividadEntity.getEntrenador() == null) {
            throw new IllegalOperationException("El entrenador no es valido");
        }

        Optional<EntrenadorEntity> entrenadorEntity = entrenadorRepository.findById(actividadEntity.getEntrenador().getId());
		if (entrenadorEntity.isEmpty())
			throw new IllegalOperationException("El entrenador no es valido");

        actividadEntity.setEntrenador(entrenadorEntity.get());
        
        log.info("Termina el proceso de creación de actividad");

        return actividadRepository.save(actividadEntity);

    }

    // Obtener todas las actividades
    @Transactional
    public List<ActividadEntity> getActividades() {

        log.info("Inicia el proceso de consultar todas las actividades");
        return actividadRepository.findAll();
    }

    // Obterner por id
    @Transactional
    public ActividadEntity getActividad(Long actividadId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar la actvidad con id = {0}", actividadId);

        Optional<ActividadEntity> actividadEntity = actividadRepository.findById(actividadId);

        if (actividadEntity.isEmpty()) {
            throw new EntityNotFoundException(ErrorMessage.ACTIVIDAD_NOT_FOUND);
        }

        return actividadEntity.get();
    }

    // Actualizar
    @Transactional
    public ActividadEntity updateActividad(Long actividadId, ActividadEntity actividad) throws EntityNotFoundException {
        log.info("Inicia proceso de actualizar la actividad con id = {0}", actividadId);

        Optional<ActividadEntity> actividadEntity = actividadRepository.findById(actividadId);
        if (actividadEntity.isEmpty()) {
            throw new EntityNotFoundException(ErrorMessage.ACTIVIDAD_NOT_FOUND);
        }

        actividad.setId(actividadId);
        log.info("Termina proceso de actualizar la actividad con id = {0}", actividadId);
        return actividadRepository.save(actividad);
    }

    // Borrar
    @Transactional
    public void deleteActividad(Long actividadId) throws EntityNotFoundException {
        log.info("Inicia proceso de borrar la actividad con id = {0}", actividadId);

        Optional<ActividadEntity> actividadEntity = actividadRepository.findById(actividadId);

        if (actividadEntity.isEmpty()) {
            throw new EntityNotFoundException(ErrorMessage.ACTIVIDAD_NOT_FOUND);
        }

        actividadRepository.deleteById(actividadId);
        log.info("Termina proceso de borrar la actividad con id = {0}", actividadId);
    }

}
