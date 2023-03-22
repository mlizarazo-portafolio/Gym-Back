package co.edu.uniandes.dse.gym.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.gym.entities.SedeEntity;
import co.edu.uniandes.dse.gym.entities.PlanEntrenamientoEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.ErrorMessage;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.repositories.SedeRepository;
import co.edu.uniandes.dse.gym.repositories.PlanEntrenamientoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlanEntrenamientoSedeService {

	@Autowired
	private PlanEntrenamientoRepository planEntrenamientoRepository;

	@Autowired
	private SedeRepository sedeRepository;
	
	@Transactional
	public SedeEntity addSede(Long planEntrenamientoId, Long sedeId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociarle una sede a el plan de entrenamiento con id = {0}", planEntrenamientoId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.PLAN_ENTRENAMIENTO_NOT_FOUND);

		Optional<PlanEntrenamientoEntity> planEntrenamientoEntity = planEntrenamientoRepository.findById(planEntrenamientoId);
		if (planEntrenamientoEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		planEntrenamientoEntity.get().getSedes().add(sedeEntity.get());
		log.info("Termina proceso de asociarle una sede a el plan de entrenamiento con id = {0}", planEntrenamientoId);
		return sedeEntity.get();
	}

	@Transactional
	public List<SedeEntity> getSedes(Long planEntrenamientoId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar todas las sedes de el plan de entrenamiento con id = {0}", planEntrenamientoId);
		Optional<PlanEntrenamientoEntity> planEntrenamientoEntity = planEntrenamientoRepository.findById(planEntrenamientoId);
		if (planEntrenamientoEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);
		log.info("Finaliza proceso de consultar todas las sedes de el plan de entrenamiento con id = {0}", planEntrenamientoId);
		return planEntrenamientoEntity.get().getSedes();
	}

	@Transactional
	public SedeEntity getSede(Long planEntrenamientoId, Long sedeId)
			throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de consultar una sede de el plan de entrenamiento con id = {0}", planEntrenamientoId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		Optional<PlanEntrenamientoEntity> planEntrenamientoEntity = planEntrenamientoRepository.findById(planEntrenamientoId);

		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.PLAN_ENTRENAMIENTO_NOT_FOUND);

		if (planEntrenamientoEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);
		log.info("Termina proceso de consultar una sede de el plan de entrenamiento con id = {0}", planEntrenamientoId);
		if (!planEntrenamientoEntity.get().getSedes().contains(sedeEntity.get()))
			throw new IllegalOperationException("The sede is not associated to the plan de entrenamiento");
		
		return sedeEntity.get();
	}

	@Transactional
	public List<SedeEntity> replaceSedes(Long planEntrenamientoId, List<SedeEntity> list) throws EntityNotFoundException {
		log.info("Inicia proceso de reemplazar las sedes de el plan de entrenamiento con id = {0}", planEntrenamientoId);
		Optional<PlanEntrenamientoEntity> planEntrenamientoEntity = planEntrenamientoRepository.findById(planEntrenamientoId);
		if (planEntrenamientoEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		for (SedeEntity sede : list) {
			Optional<SedeEntity> sedeEntity = sedeRepository.findById(sede.getId());
			if (sedeEntity.isEmpty())
				throw new EntityNotFoundException(ErrorMessage.PLAN_ENTRENAMIENTO_NOT_FOUND);

			if (!planEntrenamientoEntity.get().getSedes().contains(sedeEntity.get()))
				planEntrenamientoEntity.get().getSedes().add(sedeEntity.get());
		}
		log.info("Termina proceso de reemplazar las sedes de el plan de entrenamiento con id = {0}", planEntrenamientoId);
		return getSedes(planEntrenamientoId);
	}

	@Transactional
	public void removeSede(Long planEntrenamientoId, Long sedeId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar una sede de el plan de entrenamiento con id = {0}", planEntrenamientoId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		Optional<PlanEntrenamientoEntity> planEntrenamientoEntity = planEntrenamientoRepository.findById(planEntrenamientoId);

		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.PLAN_ENTRENAMIENTO_NOT_FOUND);

		if (planEntrenamientoEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		planEntrenamientoEntity.get().getSedes().remove(sedeEntity.get());

		log.info("Termina proceso de borrar una sede de el plan de entrenamiento con id = {0}", planEntrenamientoId);
	}
}