package co.edu.uniandes.dse.gym.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.gym.entities.PlanEntrenamientoEntity;
import co.edu.uniandes.dse.gym.entities.SedeEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.ErrorMessage;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.repositories.PlanEntrenamientoRepository;
import co.edu.uniandes.dse.gym.repositories.SedeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SedePlanEntrenamientoService {

	@Autowired
	private SedeRepository sedeRepository;

	@Autowired
	private PlanEntrenamientoRepository planEntrenamientoRepository;
	
	@Transactional
	public PlanEntrenamientoEntity addPlanEntrenamiento(Long sedeId, Long planEntrenamientoId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociarle un plan de entrenamiento a la sede con id = {0}", sedeId);
		Optional<PlanEntrenamientoEntity> planEntrenamientoEntity = planEntrenamientoRepository.findById(planEntrenamientoId);
		if (planEntrenamientoEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.PLAN_ENTRENAMIENTO_NOT_FOUND);

		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		sedeEntity.get().getPlanes().add(planEntrenamientoEntity.get());
		log.info("Termina proceso de asociarle un plan de entrenamiento a la sede con id = {0}", sedeId);
		return planEntrenamientoEntity.get();
	}

	@Transactional
	public List<PlanEntrenamientoEntity> getPlanEntrenamientos(Long sedeId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar todos los planes de entrenamiento de la sede con id = {0}", sedeId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);
		log.info("Finaliza proceso de consultar todos los planes de entrenamiento de la sede con id = {0}", sedeId);
		return sedeEntity.get().getPlanes();
	}

	@Transactional
	public PlanEntrenamientoEntity getPlanEntrenamiento(Long sedeId, Long planEntrenamientoId)
			throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de consultar un plan de entrenamiento de la sede con id = {0}", sedeId);
		Optional<PlanEntrenamientoEntity> planEntrenamientoEntity = planEntrenamientoRepository.findById(planEntrenamientoId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);

		if (planEntrenamientoEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.PLAN_ENTRENAMIENTO_NOT_FOUND);

		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);
		log.info("Termina proceso de consultar un plan de entrenamiento de la sede con id = {0}", sedeId);
		if (!sedeEntity.get().getPlanes().contains(planEntrenamientoEntity.get()))
			throw new IllegalOperationException("The planEntrenamiento is not associated to the sede");
		
		return planEntrenamientoEntity.get();
	}

	@Transactional
	public List<PlanEntrenamientoEntity> replacePlanEntrenamientos(Long sedeId, List<PlanEntrenamientoEntity> list) throws EntityNotFoundException {
		log.info("Inicia proceso de reemplazar los planes de entrenamiento de la sede con id = {0}", sedeId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		for (PlanEntrenamientoEntity planEntrenamiento : list) {
			Optional<PlanEntrenamientoEntity> planEntrenamientoEntity = planEntrenamientoRepository.findById(planEntrenamiento.getId());
			if (planEntrenamientoEntity.isEmpty())
				throw new EntityNotFoundException(ErrorMessage.PLAN_ENTRENAMIENTO_NOT_FOUND);

			if (!sedeEntity.get().getPlanes().contains(planEntrenamientoEntity.get()))
				sedeEntity.get().getPlanes().add(planEntrenamientoEntity.get());
		}
		log.info("Termina proceso de reemplazar los planes de entrenamiento de la sede con id = {0}", sedeId);
		return getPlanEntrenamientos(sedeId);
	}

	@Transactional
	public void removePlanEntrenamiento(Long sedeId, Long planEntrenamientoId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar un plan de entrenamiento de la sede con id = {0}", sedeId);
		Optional<PlanEntrenamientoEntity> planEntrenamientoEntity = planEntrenamientoRepository.findById(planEntrenamientoId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);

		if (planEntrenamientoEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.PLAN_ENTRENAMIENTO_NOT_FOUND);

		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		sedeEntity.get().getPlanes().remove(planEntrenamientoEntity.get());

		log.info("Termina proceso de borrar un plan de entrenamiento de la sede con id = {0}", sedeId);
	}
}