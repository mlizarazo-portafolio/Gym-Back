package co.edu.uniandes.dse.gym.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import co.edu.uniandes.dse.gym.entities.ActividadEntity;
import co.edu.uniandes.dse.gym.entities.RestriccionEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.ErrorMessage;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.repositories.ActividadRepository;
import co.edu.uniandes.dse.gym.repositories.RestriccionRepository;


@Slf4j
@Service
public class RestriccionService{
    
    @Autowired
    RestriccionRepository restriccionRepository;

    @Autowired
    ActividadRepository actividadRepository;


    @Transactional
    public RestriccionEntity createRestriccion(RestriccionEntity restriccionEntity) throws IllegalOperationException {
        log.info("Inicia proceso de creación de la restriccion");
        
        if(restriccionEntity.getEdad()<0)
        {
            throw new IllegalOperationException("Edad no valida");
        }

        if (restriccionEntity.getActividad() == null) {
            throw new IllegalOperationException("La actividad no es valida");
        }

        Optional<ActividadEntity> actividadEntity = actividadRepository.findById(restriccionEntity.getActividad().getId());
		if (actividadEntity.isEmpty())
			throw new IllegalOperationException("La actividad no es valida");

        restriccionEntity.setActividad(actividadEntity.get());

        log.info("Termina proceso de creación de la restriccion");
        return restriccionRepository.save(restriccionEntity);
    }

    //Obtener todas las restricciones
    @Transactional
    public List<RestriccionEntity> getRestricciones(){
        log.info("Inicia proceso de consultar todas las restricciones");
        return restriccionRepository.findAll();
    }


    //Obtener una restriccion
    @Transactional
    public RestriccionEntity getRestriccion(Long restriccionId) throws EntityNotFoundException
    {
       
        log.info("Inicia proceso de consultar la restriccion con id = {0}", restriccionId);
        Optional<RestriccionEntity> restriccionEntity = restriccionRepository.findById(restriccionId);

        if(restriccionEntity.isEmpty())
        {
            throw new EntityNotFoundException(ErrorMessage.RESTRICCION_NOT_FOUND);
        }

        log.info("Termina proceso de consultar la restriccion con id = {0}", restriccionId);
        return restriccionEntity.get(); 
    }

    //Actualizar
    @Transactional
    public RestriccionEntity updateRestriccion(Long restriccionId, RestriccionEntity restriccion)  throws EntityNotFoundException
    {
        log.info("Inicia proceso de actualizar la restriccion con id = {0}", restriccionId);
        Optional<RestriccionEntity> restriccionEntity = restriccionRepository.findById(restriccionId);
        if(restriccionEntity.isEmpty())
        {
            throw new EntityNotFoundException(ErrorMessage.RESTRICCION_NOT_FOUND);
        }

        restriccion.setId(restriccionId);
        log.info("Termina proceso de actualizar la restriccion con id = {0}", restriccionId);
        return restriccionRepository.save(restriccion);
    }

    //Borrar
    @Transactional
    public void deleteRestriccion(Long restriccionId) throws EntityNotFoundException
    {
        log.info("Inicia proceso de borrar la restriccion con id = {0}", restriccionId);
        
        Optional<RestriccionEntity> restriccionEntity = restriccionRepository.findById(restriccionId);
        if(restriccionEntity.isEmpty())
        {
            throw new EntityNotFoundException(ErrorMessage.RESTRICCION_NOT_FOUND);
        }
        restriccionRepository.deleteById(restriccionId);
        log.info("Termina proceso de borrar la restriccion con id = {0}", restriccionId);
    }

}