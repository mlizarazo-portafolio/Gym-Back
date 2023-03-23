package co.edu.uniandes.dse.gym.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.gym.entities.ConvenioEntity;
import co.edu.uniandes.dse.gym.entities.PlanEntrenamientoEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.ErrorMessage;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.repositories.ConvenioRepository;
import co.edu.uniandes.dse.gym.repositories.PlanEntrenamientoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConvenioPlanEntrenamientoService {
    
	@Autowired
	private PlanEntrenamientoRepository planEntrenamientoRepository;

	@Autowired
	private ConvenioRepository convenioRepository;
	
	@Transactional
	public ConvenioEntity addConvenio(Long planEntrenamientoId, Long convenioId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociarle un convenio a la planEntrenamiento con id = {0}", planEntrenamientoId);
		Optional<ConvenioEntity> convenioEntity = convenioRepository.findById(convenioId);
		if (convenioEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.PLAN_ENTRENAMIENTO_NOT_FOUND);

		Optional<PlanEntrenamientoEntity> planEntrenamientoEntity = planEntrenamientoRepository.findById(planEntrenamientoId);
		if (planEntrenamientoEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		planEntrenamientoEntity.get().getConvenios().add(convenioEntity.get());
		log.info("Termina proceso de asociarle un convenio a la planEntrenamiento con id = {0}", planEntrenamientoId);
		return convenioEntity.get();
	}

	@Transactional
	public List<ConvenioEntity> getConvenios(Long planEntrenamientoId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar todos los planes de entrenamiento de la planEntrenamiento con id = {0}", planEntrenamientoId);
		Optional<PlanEntrenamientoEntity> planEntrenamientoEntity = planEntrenamientoRepository.findById(planEntrenamientoId);
		if (planEntrenamientoEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);
		log.info("Finaliza proceso de consultar todos los planes de entrenamiento de la planEntrenamiento con id = {0}", planEntrenamientoId);
		return planEntrenamientoEntity.get().getConvenios();
	}

	@Transactional
	public ConvenioEntity getConvenio(Long planEntrenamientoId, Long convenioId)
			throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de consultar un convenio de la planEntrenamiento con id = {0}", planEntrenamientoId);
		Optional<ConvenioEntity> convenioEntity = convenioRepository.findById(convenioId);
		Optional<PlanEntrenamientoEntity> planEntrenamientoEntity = planEntrenamientoRepository.findById(planEntrenamientoId);

		if (convenioEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.PLAN_ENTRENAMIENTO_NOT_FOUND);

		if (planEntrenamientoEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);
		log.info("Termina proceso de consultar un convenio de la planEntrenamiento con id = {0}", planEntrenamientoId);
		if (!planEntrenamientoEntity.get().getConvenios().contains(convenioEntity.get()))
			throw new IllegalOperationException("The convenio is not associated to the planEntrenamiento");
		
		return convenioEntity.get();
	}

	@Transactional
	public List<ConvenioEntity> replaceConvenios(Long planEntrenamientoId, List<ConvenioEntity> list) throws EntityNotFoundException {
		log.info("Inicia proceso de reemplazar los planes de entrenamiento de la planEntrenamiento con id = {0}", planEntrenamientoId);
		Optional<PlanEntrenamientoEntity> planEntrenamientoEntity = planEntrenamientoRepository.findById(planEntrenamientoId);
		if (planEntrenamientoEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		for (ConvenioEntity convenio : list) {
			Optional<ConvenioEntity> convenioEntity = convenioRepository.findById(convenio.getId());
			if (convenioEntity.isEmpty())
				throw new EntityNotFoundException(ErrorMessage.PLAN_ENTRENAMIENTO_NOT_FOUND);

			if (!planEntrenamientoEntity.get().getConvenios().contains(convenioEntity.get()))
				planEntrenamientoEntity.get().getConvenios().add(convenioEntity.get());
		}
		log.info("Termina proceso de reemplazar los planes de entrenamiento de la planEntrenamiento con id = {0}", planEntrenamientoId);
		return getConvenios(planEntrenamientoId);
	}

	@Transactional
	public void removeConvenio(Long planEntrenamientoId, Long convenioId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar un convenio de la planEntrenamiento con id = {0}", planEntrenamientoId);
		Optional<ConvenioEntity> convenioEntity = convenioRepository.findById(convenioId);
		Optional<PlanEntrenamientoEntity> planEntrenamientoEntity = planEntrenamientoRepository.findById(planEntrenamientoId);

		if (convenioEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.PLAN_ENTRENAMIENTO_NOT_FOUND);

		if (planEntrenamientoEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		planEntrenamientoEntity.get().getConvenios().remove(convenioEntity.get());

		log.info("Termina proceso de borrar un convenio de la planEntrenamiento con id = {0}", planEntrenamientoId);
	}
    
}
