package co.edu.uniandes.dse.gym.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.gym.entities.SedeEntity;
import co.edu.uniandes.dse.gym.entities.ActividadEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.ErrorMessage;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.repositories.SedeRepository;
import co.edu.uniandes.dse.gym.repositories.ActividadRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ActividadSedeService {

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private SedeRepository sedeRepository;

    @Transactional
	public SedeEntity addSede(Long actividadId, Long sedeId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociarle una sede a la actividad con id = {0}", actividadId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		Optional<ActividadEntity> actividadEntity = actividadRepository.findById(actividadId);
		if (actividadEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.ACTIVIDAD_NOT_FOUND);

		actividadEntity.get().getSedes().add(sedeEntity.get());
		log.info("Termina proceso de asociarle una sede a la actividad con id = {0}", actividadId);
		return sedeEntity.get();
	}

    @Transactional
	public List<SedeEntity> getSedes(Long actividadId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar todas las sedes de la actividad con id = {0}", actividadId);
		Optional<ActividadEntity> actividadEntity = actividadRepository.findById(actividadId);
		if (actividadEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.ACTIVIDAD_NOT_FOUND);
		log.info("Finaliza proceso de consultar todas las sedes de la actividad con id = {0}", actividadId);
		return actividadEntity.get().getSedes();
	}

    @Transactional
	public SedeEntity getSede(Long actividadId, Long sedeId)
			throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de consultar una sede de la actividad con id = {0}", actividadId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		Optional<ActividadEntity> actividadEntity = actividadRepository.findById(actividadId);

		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		if (actividadEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.ACTIVIDAD_NOT_FOUND);
		log.info("Termina proceso de consultar una sede de la actividad con id = {0}", actividadId);
		if (!actividadEntity.get().getSedes().contains(sedeEntity.get()))
			throw new IllegalOperationException("La sede no esta asociada con la actividad");
		
		return sedeEntity.get();
	}
    
    @Transactional
    public List<SedeEntity> replaceSedes(Long actividadId, List<SedeEntity> list) throws EntityNotFoundException {
		log.info("Inicia proceso de reemplazar las sedes de la actividad con id = {0}", actividadId);
		Optional<ActividadEntity> actividadEntity = actividadRepository.findById(actividadId);
		if (actividadEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.ACTIVIDAD_NOT_FOUND);

		for (SedeEntity sede : list) {
			Optional<SedeEntity> sedeEntity = sedeRepository.findById(sede.getId());
			if (sedeEntity.isEmpty())
				throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

			if (!actividadEntity.get().getSedes().contains(sedeEntity.get()))
				actividadEntity.get().getSedes().add(sedeEntity.get());
		}
		log.info("Termina proceso de reemplazar las sedes de la actividad con id = {0}", actividadId);
		return getSedes(actividadId);
	}

    @Transactional
    public void removeSede(Long actividadId, Long sedeId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar una sede de la actividad con id = {0}", actividadId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		Optional<ActividadEntity> actividadEntity = actividadRepository.findById(actividadId);

		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		if (actividadEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.ACTIVIDAD_NOT_FOUND);

		actividadEntity.get().getSedes().remove(sedeEntity.get());

		log.info("Termina proceso de borrar una sede de la actividad con id = {0}", actividadId);
	}

}
