package co.edu.uniandes.dse.gym.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.gym.entities.AtletaEntity;
import co.edu.uniandes.dse.gym.entities.ActividadEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.ErrorMessage;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.repositories.AtletaRepository;
import co.edu.uniandes.dse.gym.repositories.ActividadRepository;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class AtletaActividadService {

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private AtletaRepository atletaRepository;

    @Transactional
	public ActividadEntity addActividad(Long atletaId, Long actividadId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociarle una Actividad a la atleta con id = {0}", atletaId);
		Optional<AtletaEntity> atletaEntity = atletaRepository.findById(atletaId);
		Optional<ActividadEntity> actividadEntity = actividadRepository.findById(actividadId);

		if (atletaEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		if (actividadEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.ACTIVIDAD_NOT_FOUND);

		actividadEntity.get().getAtletasInscritos().add(atletaEntity.get());
		log.info("Termina proceso de asociarle una Actividad a la atleta con id = {0}", atletaId);
		return actividadEntity.get();
	}

    @Transactional
	public List<ActividadEntity> getActividades(Long atletaId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar todas las actividades de la atleta con id = {0}", atletaId);
		Optional<AtletaEntity> atletaEntity = atletaRepository.findById(atletaId);
		if (atletaEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		log.info("Termina proceso de consultar todas las actividades de la atleta con id = {0}", atletaId);
		return atletaEntity.get().getActividadesInscritas();
	}

    @Transactional
	public ActividadEntity getActividad(Long atletaId, Long actividadId) throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de consultar la actividad con id = {0} de la atleta con id = " + atletaId, actividadId);
		Optional<AtletaEntity> atletaEntity = atletaRepository.findById(atletaId);
		Optional<ActividadEntity> actividadEntity = actividadRepository.findById(actividadId);

		if (atletaEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		if (actividadEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.ACTIVIDAD_NOT_FOUND);

		log.info("Termina proceso de consultar la actividad con id = {0} de la atleta con id = " + atletaId, actividadId);
		if (!actividadEntity.get().getAtletasInscritos().contains(atletaEntity.get()))
			throw new IllegalOperationException("La actividad no esta asociada con la atleta");
		
		return actividadEntity.get();
	}

    @Transactional
	public List<ActividadEntity> addActividades(Long atletaId, List<ActividadEntity> actividades) throws EntityNotFoundException {
		log.info("Inicia proceso de reemplazar las actividades asociadas a la atleta con id = {0}", atletaId);
		Optional<AtletaEntity> atletaEntity = atletaRepository.findById(atletaId);
		if (atletaEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		for (ActividadEntity actividad : actividades) {
			Optional<ActividadEntity> actividadEntity = actividadRepository.findById(actividad.getId());
			if (actividadEntity.isEmpty())
				throw new EntityNotFoundException(ErrorMessage.ACTIVIDAD_NOT_FOUND);

			if (!actividadEntity.get().getAtletasInscritos().contains(atletaEntity.get()))
				actividadEntity.get().getAtletasInscritos().add(atletaEntity.get());
		}
		log.info("Finaliza proceso de reemplazar las actividades asociadas a la atleta con id = {0}", atletaId);
		atletaEntity.get().setActividadesInscritas(actividades);
		return atletaEntity.get().getActividadesInscritas();
	}

    @Transactional
	public void removeActividad(Long atletaId, Long actividadId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar una actividad de la atleta con id = {0}", atletaId);
		Optional<AtletaEntity> atletaEntity = atletaRepository.findById(atletaId);
		if (atletaEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		Optional<ActividadEntity> actividadEntity = actividadRepository.findById(actividadId);
		if (actividadEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.ACTIVIDAD_NOT_FOUND);

		actividadEntity.get().getAtletasInscritos().remove(atletaEntity.get());
		atletaEntity.get().getActividadesInscritas().remove(actividadEntity.get());
		log.info("Finaliza proceso de borrar una actividad de la atleta con id = {0}", atletaId);
	}
}