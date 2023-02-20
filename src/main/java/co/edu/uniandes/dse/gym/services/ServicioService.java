package co.edu.uniandes.dse.gym.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.gym.entities.SedeEntity;
import co.edu.uniandes.dse.gym.entities.ServicioEntity;
import co.edu.uniandes.dse.gym.repositories.SedeRepository;
import co.edu.uniandes.dse.gym.repositories.ServicioRepository;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.ErrorMessage;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ServicioService {
    
    @Autowired
    ServicioRepository servicioRepository;

    @Autowired
    SedeRepository sedeRepository;

    @Transactional
    public ServicioEntity createServicio(ServicioEntity servicioEntity) throws EntityNotFoundException, IllegalOperationException {
        
        log.info("Inicio proceso de creación de Servicio");
        
        if(servicioEntity.getSede() == null) throw new IllegalOperationException("Sede no es válida");
        Optional<SedeEntity> sedeEntity = sedeRepository.findById(servicioEntity.getSede().getId());
        if(sedeEntity.isEmpty()) throw new IllegalOperationException("Sede no es válida");

        if(servicioEntity.getServicio() == null) throw new IllegalOperationException("Servicio no es válido");
        if(servicioEntity.getDisponible() == null) throw new IllegalOperationException("Disponiblidad no es válida");
        
        servicioEntity.setSede(sedeEntity.get());
        log.info("Termina proceso de creación de Servicio");
        
        return servicioRepository.save(servicioEntity);
    }

    @Transactional
    public List<ServicioEntity> getServicios() {
        log.info("Inicia proceso de consultar todos los servicios");
        return servicioRepository.findAll();
    }

    @Transactional
    public ServicioEntity getServicioById(Long servicioId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar el Servicio con id = {0}", servicioId);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);

        if(servicioEntity.isEmpty()) throw new EntityNotFoundException(ErrorMessage.SERVICIO_NOT_FOUND);

        log.info("Finaliza proceso de consultar el Servicio con id = {0}", servicioId);
        return servicioEntity.get();
    }

    @Transactional
    public ServicioEntity updateServicio(Long servicioId, ServicioEntity servicio) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de actualizar el servicio con id = {0}", servicioId);

        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);

        if (servicioEntity.isEmpty()) throw new EntityNotFoundException(ErrorMessage.SERVICIO_NOT_FOUND);

        servicio.setId(servicioId);

        log.info("Termina proceso de actualizar el servicio con id = {0}", servicioId);
            
        return servicioRepository.save(servicio);
    }
    
    @Transactional
    public void deleteServicio(Long servicioId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de borrar el servicio con id = {0}", servicioId);

        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);

        if (servicioEntity.isEmpty()) throw new EntityNotFoundException(ErrorMessage.SERVICIO_NOT_FOUND);

        servicioRepository.deleteById(servicioId);

        log.info("Termina proceso de borrar el servicio con id = {0}", servicioId);
    }

}