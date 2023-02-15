package co.edu.uniandes.dse.gym.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import co.edu.uniandes.dse.gym.repositories.ActividadRepository;
import org.springframework.transaction.annotation.Transactional;
import co.edu.uniandes.dse.gym.entities.ActividadEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;

import java.util.List;
import java.util.Optional;

@Service
public class ActividadService {
    
    @Autowired
    ActividadRepository actividadRepository;



    @Transactional
    public List<ActividadEntity> getActividades(){

        return actividadRepository.findAll();
    }

    @Transactional
    public ActividadEntity getActividad(Long actividadId) throws EntityNotFoundException
    {
       

        Optional<ActividadEntity> actividadEntity = actividadRepository.findById(actividadId);

        if(actividadEntity.isEmpty())
            throw new EntityNotFoundException("ACTIVIDAD_NOT_FOUND");
        
     
        return actividadEntity.get(); 
    }

    @Transactional
    public ActividadEntity createActividad(ActividadEntity actividadEntity) throws IllegalOperationException {
        
        return actividadRepository.save(actividadEntity);
    }


}
