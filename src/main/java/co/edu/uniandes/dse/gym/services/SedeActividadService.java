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
public class SedeActividadService {

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private SedeRepository sedeRepository;

    @Transactional
	public ActividadEntity addActividad(Long sedeId, Long actividadId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociarle una Actividad a la sede con id = {0}", sedeId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		Optional<ActividadEntity> actividadEntity = actividadRepository.findById(actividadId);

		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		if (actividadEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.ACTIVIDAD_NOT_FOUND);

		actividadEntity.get().getSedes().add(sedeEntity.get());
		log.info("Termina proceso de asociarle una Actividad a la sede con id = {0}", sedeId);
		return actividadEntity.get();
	}

    @Transactional
	public List<ActividadEntity> getActividades(Long sedeId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar todas las actividades de la sede con id = {0}", sedeId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		log.info("Termina proceso de consultar todas las actividades de la sede con id = {0}", sedeId);
		return sedeEntity.get().getActividades();
	}

    @Transactional
	public ActividadEntity getActividad(Long sedeId, Long actividadId) throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de consultar la actividad con id = {0} de la sede con id = " + sedeId, actividadId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		Optional<ActividadEntity> actividadEntity = actividadRepository.findById(actividadId);

		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		if (actividadEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.ACTIVIDAD_NOT_FOUND);

		log.info("Termina proceso de consultar la actividad con id = {0} de la sede con id = " + sedeId, actividadId);
		if (!actividadEntity.get().getSedes().contains(sedeEntity.get()))
			throw new IllegalOperationException("La actividad no esta asociada con la sede");
		
		return actividadEntity.get();
	}

    @Transactional
	public List<ActividadEntity> addActividades(Long sedeId, List<ActividadEntity> actividades) throws EntityNotFoundException {
		log.info("Inicia proceso de reemplazar las actividades asociadas a la sede con id = {0}", sedeId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		for (ActividadEntity actividad : actividades) {
			Optional<ActividadEntity> actividadEntity = actividadRepository.findById(actividad.getId());
			if (actividadEntity.isEmpty())
				throw new EntityNotFoundException(ErrorMessage.ACTIVIDAD_NOT_FOUND);

			if (!actividadEntity.get().getSedes().contains(sedeEntity.get()))
				actividadEntity.get().getSedes().add(sedeEntity.get());
		}
		log.info("Finaliza proceso de reemplazar las actividades asociadas a la sede con id = {0}", sedeId);
		sedeEntity.get().setActividades(actividades);
		return sedeEntity.get().getActividades();
	}

    @Transactional
	public void removeActividad(Long sedeId, Long actividadId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar una actividad de la sede con id = {0}", sedeId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		Optional<ActividadEntity> actividadEntity = actividadRepository.findById(actividadId);
		if (actividadEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.ACTIVIDAD_NOT_FOUND);

		actividadEntity.get().getSedes().remove(sedeEntity.get());
		sedeEntity.get().getActividades().remove(actividadEntity.get());
		log.info("Finaliza proceso de borrar una actividad de la sede con id = {0}", sedeId);
	}

    
}
