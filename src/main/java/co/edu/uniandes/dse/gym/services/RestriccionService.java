package co.edu.uniandes.dse.gym.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.gym.entities.RestriccionEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.repositories.RestriccionRepository;

@Service
public class RestriccionService{
    
    @Autowired
    RestriccionRepository restriccionRepository;



    @Transactional
    public List<RestriccionEntity> getRestricciones(){

        return restriccionRepository.findAll();
    }

    @Transactional
    public RestriccionEntity getRestriccion(Long restriccionId) throws EntityNotFoundException
    {
       

        Optional<RestriccionEntity> restriccionEntity = restriccionRepository.findById(restriccionId);

        if(restriccionEntity.isEmpty())
            throw new EntityNotFoundException("ACTIVIDAD_NOT_FOUND");
        
     
        return restriccionEntity.get(); 
    }

    @Transactional
    public RestriccionEntity createRestriccion(RestriccionEntity restriccionEntity) throws IllegalOperationException {
        
        return restriccionRepository.save(restriccionEntity);
    }


}