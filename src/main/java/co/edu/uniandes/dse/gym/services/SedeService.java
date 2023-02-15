package co.edu.uniandes.dse.gym.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.gym.entities.SedeEntity;
import co.edu.uniandes.dse.gym.repositories.SedeRepository;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;


@Service
public class SedeService {
    
    @Autowired
    SedeRepository sedeRepository;

    @Transactional
    public SedeEntity createSede(SedeEntity sedeEntity) throws EntityNotFoundException, IllegalOperationException {
        return sedeRepository.save(sedeEntity);
    }

    @Transactional
    public List<SedeEntity> getSedes() {
        return sedeRepository.findAll();
    }

    @Transactional
    public SedeEntity getSedeById(Long sedeId) throws EntityNotFoundException {
        Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);

        if(sedeEntity.isEmpty()) {
            throw new EntityNotFoundException("SEDE NOT FOUND");
        }
        return sedeEntity.get();
    }

}