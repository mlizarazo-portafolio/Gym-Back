package co.edu.uniandes.dse.gym.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.gym.entities.ServicioEntity;
import co.edu.uniandes.dse.gym.repositories.ServicioRepository;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;


@Service
public class ServicioService {
    
    @Autowired
    ServicioRepository servicioRepository;

    @Transactional
    public ServicioEntity createServicio(ServicioEntity servicioEntity) throws EntityNotFoundException, IllegalOperationException {
        return servicioRepository.save(servicioEntity);
    }

    @Transactional
    public List<ServicioEntity> getServicios() {
        return servicioRepository.findAll();
    }

    @Transactional
    public ServicioEntity getServicioById(Long servicioId) throws EntityNotFoundException {
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);

        if(servicioEntity.isEmpty()) {
            throw new EntityNotFoundException("SERVICIO NOT FOUND");
        }
        return servicioEntity.get();
    }

}