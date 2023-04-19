package co.edu.uniandes.dse.gym.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.gym.entities.ActividadEntity;
import co.edu.uniandes.dse.gym.entities.AtletaEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.ErrorMessage;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.repositories.ActividadRepository;
import co.edu.uniandes.dse.gym.repositories.AtletaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ActividadAtletaService {
    @Autowired
	private AtletaRepository atletaRepository;

	@Autowired
	private ActividadRepository actividadRepository;

	@Transactional
	public AtletaEntity addAtleta(Long actividadId, Long atletaId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociarle un atleta a la actividad con id = {0}", actividadId);
		Optional<ActividadEntity> actividadEntity = actividadRepository.findById(actividadId);
		Optional<AtletaEntity> atletaEntity = atletaRepository.findById(atletaId);

		if (actividadEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.ACTIVIDAD_NOT_FOUND);

		if (atletaEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.ATLETA_NOT_FOUND);

		atletaEntity.get().getActividadesInscritas().add(actividadEntity.get());
		log.info("Termina proceso de asociarle un atleta a la actividad con id = {0}", actividadId);
		return atletaEntity.get();
	}


	@Transactional
	public List<AtletaEntity> getAtletas(Long actividadId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar todos los atletas de la actividad con id = {0}", actividadId);
		Optional<ActividadEntity> actividadEntity = actividadRepository.findById(actividadId);
		if (actividadEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.ACTIVIDAD_NOT_FOUND);

		log.info("Termina proceso de consultar todos los atletas de la actividad con id = {0}", actividadId);
		return actividadEntity.get().getAtletas();
	}


	@Transactional
	public AtletaEntity getAtleta(Long actividadId, Long atletaId) throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de consultar el atleta con id = {0} de la actividad con id = " + actividadId, atletaId);
		Optional<ActividadEntity> actividadEntity = actividadRepository.findById(actividadId);
		Optional<AtletaEntity> atletaEntity = atletaRepository.findById(atletaId);

		if (actividadEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.ACTIVIDAD_NOT_FOUND);

		if (atletaEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.ATLETA_NOT_FOUND);

		log.info("Termina proceso de consultar el atleta con id = {0} de la actividad con id = " + actividadId, atletaId);
		if (!atletaEntity.get().getActividadesInscritas().contains(actividadEntity.get()))
			throw new IllegalOperationException("The atleta is not associated to the actividad");
		
		return atletaEntity.get();
	}


	@Transactional
	public List<AtletaEntity> replaceAtletas(Long actividadId, List<AtletaEntity> atletas) throws EntityNotFoundException {
		log.info("Inicia proceso de reemplazar los atletas asociados a la actividad con id = {0}", actividadId);
		Optional<ActividadEntity> actividadEntity = actividadRepository.findById(actividadId);
		if (actividadEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.ACTIVIDAD_NOT_FOUND);

		for (AtletaEntity atleta : atletas) {
			Optional<AtletaEntity> atletaEntity = atletaRepository.findById(atleta.getId());
			if (atletaEntity.isEmpty())
				throw new EntityNotFoundException(ErrorMessage.ATLETA_NOT_FOUND);

			if (!atletaEntity.get().getActividadesInscritas().contains(actividadEntity.get()))
				atletaEntity.get().getActividadesInscritas().add(actividadEntity.get());
		}
		log.info("Finaliza proceso de reemplazar los atletas asociados a la actividad con id = {0}", actividadId);
		actividadEntity.get().setAtletas(atletas);
		return actividadEntity.get().getAtletas();
	}

	@Transactional
	public void removeAtleta(Long actividadId, Long atletaId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar un atleta de la actividad con id = {0}", actividadId);
		Optional<ActividadEntity> actividadEntity = actividadRepository.findById(actividadId);
		if (actividadEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.ACTIVIDAD_NOT_FOUND);

		Optional<AtletaEntity> atletaEntity = atletaRepository.findById(atletaId);
		if (atletaEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.ATLETA_NOT_FOUND);

		atletaEntity.get().getActividadesInscritas().remove(actividadEntity.get());
		actividadEntity.get().getAtletas().remove(atletaEntity.get());
		log.info("Finaliza proceso de borrar un atleta de la actividad con id = {0}", actividadId);
	}
}