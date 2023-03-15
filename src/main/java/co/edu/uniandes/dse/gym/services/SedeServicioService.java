package co.edu.uniandes.dse.gym.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.gym.entities.SedeEntity;
import co.edu.uniandes.dse.gym.entities.ServicioEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.ErrorMessage;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.repositories.SedeRepository;
import co.edu.uniandes.dse.gym.repositories.ServicioRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SedeServicioService {
    
    @Autowired
    ServicioRepository servicioRepository;

    @Autowired
    SedeRepository sedeRepository;

    @Transactional
	public ServicioEntity addServicio(Long servicioId, Long sedeId) throws EntityNotFoundException {
		log.info("Inicia proceso de agregarle un servicio a la sede con id = {0}", sedeId);
		
		Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
		if(servicioEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SERVICIO_NOT_FOUND);
		
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		if(sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);
		
		servicioEntity.get().setSede(sedeEntity.get());
		log.info("Termina proceso de agregarle un servicio a la sede con id = {0}", sedeId);
		return servicioEntity.get();
	}

	@Transactional
	public List<ServicioEntity> getServicios(Long sedeId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar los servicios asociados a la sede con id = {0}", sedeId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		if(sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);
		
		return sedeEntity.get().getServiciosDisponibles();
	}

	@Transactional
	public ServicioEntity getServicio(Long sedeId, Long servicioId) throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de consultar el servicio con id = {0} de la sede con id = " + sedeId, servicioId);
		
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		if(sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);
		
		Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
		if(servicioEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SERVICIO_NOT_FOUND);
				
		log.info("Termina proceso de consultar el servicio con id = {0} de la sede con id = " + sedeId, servicioId);
		
		if(!sedeEntity.get().getServiciosDisponibles().contains(servicioEntity.get()))
			throw new IllegalOperationException("The servicio is not associated to the sede");
		
		return servicioEntity.get();
	}

	@Transactional
	public List<ServicioEntity> replaceServicios(Long sedeId, List<ServicioEntity> servicios) throws EntityNotFoundException {
		log.info("Inicia proceso de actualizar la sede con id = {0}", sedeId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		if(sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);
		
		for(ServicioEntity servicio : servicios) {
			Optional<ServicioEntity> b = servicioRepository.findById(servicio.getId());
			if(b.isEmpty())
				throw new EntityNotFoundException(ErrorMessage.SERVICIO_NOT_FOUND);
			
			b.get().setSede(sedeEntity.get());
		}		
		return servicios;
	}

}