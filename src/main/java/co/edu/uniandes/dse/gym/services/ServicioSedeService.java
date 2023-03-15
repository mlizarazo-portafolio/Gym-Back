package co.edu.uniandes.dse.gym.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.gym.entities.ServicioEntity;
import co.edu.uniandes.dse.gym.entities.SedeEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.ErrorMessage;
import co.edu.uniandes.dse.gym.repositories.ServicioRepository;
import co.edu.uniandes.dse.gym.repositories.SedeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ServicioSedeService {
    
    @Autowired
    SedeRepository sedeRepository;

    @Autowired
    ServicioRepository servicioRepository;

	@Transactional
	public ServicioEntity replaceSede(Long servicioId, Long sedeId) throws EntityNotFoundException {
		log.info("Inicia proceso de actualizar servicio con id = {0}", servicioId);
		Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
		if (servicioEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SERVICIO_NOT_FOUND);

		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		servicioEntity.get().setSede(sedeEntity.get());
		log.info("Termina proceso de actualizar servicio con id = {0}", servicioId);

		return servicioEntity.get();
	}

	// @Transactional
	// public void removeSede(Long servicioId) throws EntityNotFoundException {
	// 	log.info("Inicia proceso de borrar la sede del servicio con id = {0}", servicioId);
	// 	Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
	// 	if (servicioEntity.isEmpty())
	// 		throw new EntityNotFoundException(ErrorMessage.SERVICIO_NOT_FOUND);

	// 	Optional<SedeEntity> sedeEntity = sedeRepository
	// 			.findById(servicioEntity.get().getSede().getId());
	// 	sedeEntity.ifPresent(sede -> sede.getServiciosDisponibles().remove(servicioEntity.get()));

	// 	servicioEntity.get().setSede(null);
	// 	log.info("Termina proceso de borrar la sede del libro con id = {0}", servicioId);
	// }

}   