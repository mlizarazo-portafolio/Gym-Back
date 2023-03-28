package co.edu.uniandes.dse.gym.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.gym.entities.AtletaEntity;
import co.edu.uniandes.dse.gym.entities.PlanEntrenamientoEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.ErrorMessage;
import co.edu.uniandes.dse.gym.repositories.AtletaRepository;
import co.edu.uniandes.dse.gym.repositories.PlanEntrenamientoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AtletaPlanEntrenamientoService {

    @Autowired
    PlanEntrenamientoRepository planEntrenamientoRepository;

    @Autowired
    AtletaRepository atletaRepository;

    @Transactional
    public AtletaEntity replacePlanEntrenamiento(Long atletaId, Long planEntrenamientoId)
            throws EntityNotFoundException {
        log.info("Inicia proceso de actualizar atleta con id = {0}", atletaId);
        Optional<AtletaEntity> atletaEntity = atletaRepository.findById(atletaId);
        if (atletaEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.SERVICIO_NOT_FOUND);

        Optional<PlanEntrenamientoEntity> planEntrenamientoEntity = planEntrenamientoRepository
                .findById(planEntrenamientoId);
        if (planEntrenamientoEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

        atletaEntity.get().setPlan(planEntrenamientoEntity.get());
        log.info("Termina proceso de actualizar atleta con id = {0}", atletaId);

        return atletaEntity.get();
    }

}
