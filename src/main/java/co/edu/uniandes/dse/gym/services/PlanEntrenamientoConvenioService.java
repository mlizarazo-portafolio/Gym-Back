package co.edu.uniandes.dse.gym.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.gym.entities.AtletaEntity;
import co.edu.uniandes.dse.gym.entities.ConvenioEntity;
import co.edu.uniandes.dse.gym.entities.DeportologoEntity;
import co.edu.uniandes.dse.gym.entities.PlanEntrenamientoEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.ErrorMessage;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.repositories.AtletaRepository;
import co.edu.uniandes.dse.gym.repositories.ConvenioRepository;
import co.edu.uniandes.dse.gym.repositories.DeportologoRepository;
import co.edu.uniandes.dse.gym.repositories.PlanEntrenamientoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlanEntrenamientoConvenioService {

    @Autowired
    private PlanEntrenamientoRepository planEntrenamientoRepository;

    @Autowired
    private ConvenioRepository convenioRepository;

    @Transactional
    public PlanEntrenamientoEntity addPlan(Long planId, Long convenioId) throws EntityNotFoundException {
        log.info("Inicia proceso de agregarle un convenio al plan con id = {0}", convenioId);

        Optional<PlanEntrenamientoEntity> planEntrenamientoEntity = planEntrenamientoRepository.findById(planId);
        if (planEntrenamientoEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.PLAN_ENTRENAMIENTO_NOT_FOUND);

        Optional<ConvenioEntity> convenioEntity = convenioRepository.findById(convenioId);
        if (convenioEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.CONVENIO_NOT_FOUND);

        planEntrenamientoEntity.get().setConvenios(convenioEntity.get());
        log.info("Termina proceso de agregarle un convenio al plan con id = {0}", convenioId);
        return planEntrenamientoEntity.get();
    }

}
