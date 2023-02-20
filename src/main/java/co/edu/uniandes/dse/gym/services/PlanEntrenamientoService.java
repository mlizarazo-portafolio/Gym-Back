package co.edu.uniandes.dse.gym.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.logging.log4j.message.Message;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.gym.entities.PlanEntrenamientoEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.repositories.PlanEntrenamientoRepository;

@Service
public class PlanEntrenamientoService {

    @Autowired
    PlanEntrenamientoRepository planEntrenamientoRepository;

    /**
     * Obtiene la 
     * 
     */

    @Transactional
    public List<PlanEntrenamientoEntity> getPlanes(){
        return planEntrenamientoRepository.findAll();
    }

    @Transactional
    public PlanEntrenamientoEntity getPlan(Long planId) throws EntityNotFoundException{

        Optional < PlanEntrenamientoEntity > planEntrenamientoEntity = planEntrenamientoRepository.findById(planId);

        if(planEntrenamientoEntity.isEmpty())
            throw new EntityNotFoundException("PLAN_NOT_FOUND");
    
    
        return planEntrenamientoEntity.get();
    }
    
    
    @Transactional
    public PlanEntrenamientoEntity crearPlan(PlanEntrenamientoEntity plan){
        return planEntrenamientoRepository.save(plan);
    }
        
}

