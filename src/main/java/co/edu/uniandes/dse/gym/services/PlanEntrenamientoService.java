package co.edu.uniandes.dse.gym.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;


import co.edu.uniandes.dse.gym.exceptions.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.gym.entities.PlanEntrenamientoEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.repositories.PlanEntrenamientoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service

public class PlanEntrenamientoService {

    @Autowired
    PlanEntrenamientoRepository planEntrenamientoRepository;

    /**
     * Obtiene la 
     * 
     */
    
    @Transactional
    public PlanEntrenamientoEntity crearPlan(PlanEntrenamientoEntity planEntrenamientoEntity) throws IllegalOperationException{
        
        log.info("inicia el proceso de crear Plan de Entrenamiento");

       
        if(planEntrenamientoEntity.getNombre() == null) throw new IllegalOperationException("Nombre no es válido");
        if(planEntrenamientoEntity.getObjetivoBasico() == null) throw new IllegalOperationException("Direccion no es válida");
        if(planEntrenamientoEntity.getDescripcion() == null) throw new IllegalOperationException("Telefono no es válido");
        if(planEntrenamientoEntity.getDireccion() == null) throw new IllegalOperationException("Telefono no es válido");
        if(planEntrenamientoEntity.getDuracion() == null) throw new IllegalOperationException("Telefono no es válido");
        if(planEntrenamientoEntity.getCosto() == null) throw new IllegalOperationException("Telefono no es válido");
    
        log.info("Termina proceso de creación de Plan de Entrenamiento");

        return planEntrenamientoRepository.save(planEntrenamientoEntity);
    }
        
    @Transactional
    public List<PlanEntrenamientoEntity> getPlanes(){
        log.info("Inicia proceso de consultar todos los planes");
        return planEntrenamientoRepository.findAll();
    }

    @Transactional
    public PlanEntrenamientoEntity getPlanById(Long planId) throws EntityNotFoundException{
        log.info("Inicia proceso de consultar el Plan con id = {0}", planId);
        Optional <PlanEntrenamientoEntity> planEntrenamientoEntity = planEntrenamientoRepository.findById(planId);

        if(planEntrenamientoEntity.isEmpty())throw new EntityNotFoundException(ErrorMessage.PLAN_ENTRENAMIENTO_NOT_FOUND);
    
        log.info("Finaliza proceso de consultar el Plan con id = {0}", planId);
        return planEntrenamientoEntity.get();
    }

    @Transactional
    public PlanEntrenamientoEntity updatePlan(Long planId, PlanEntrenamientoEntity plan) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de actualizar el plan con id = {0}", planId);
        Optional < PlanEntrenamientoEntity > planEntrenamientoEntity = planEntrenamientoRepository.findById(planId);

        if (planEntrenamientoEntity.isEmpty()) throw new EntityNotFoundException(ErrorMessage.PLAN_ENTRENAMIENTO_NOT_FOUND);

        plan.setId(planId);
        log.info("Termina proceso de actualizar el plan con id = {0}", planId);      
        return planEntrenamientoRepository.save(plan);
    }


    @Transactional
    public void deletePlan(Long planId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de borrar el plan con id = {0}", planId);

        Optional < PlanEntrenamientoEntity > planEntrenamientoEntity = planEntrenamientoRepository.findById(planId);

        if (planEntrenamientoEntity.isEmpty()) throw new EntityNotFoundException(ErrorMessage.PLAN_ENTRENAMIENTO_NOT_FOUND);

        planEntrenamientoRepository.deleteById(planId);
        
        log.info("Termina proceso de borrar el plan con id = {0}", planId);
    }

    public boolean validateString(String string){
        return !(string==null || string.isEmpty());
    }
}

