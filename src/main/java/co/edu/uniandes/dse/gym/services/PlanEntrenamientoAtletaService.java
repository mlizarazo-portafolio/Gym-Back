package co.edu.uniandes.dse.gym.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.gym.entities.PlanEntrenamientoEntity;
import co.edu.uniandes.dse.gym.entities.AtletaEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.ErrorMessage;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.repositories.PlanEntrenamientoRepository;
import co.edu.uniandes.dse.gym.repositories.AtletaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlanEntrenamientoAtletaService {

    @Autowired
    AtletaRepository atletaRepository;

    @Autowired
    PlanEntrenamientoRepository planEntrenamientoRepository;

    @Transactional
    public AtletaEntity addAtleta(Long atletaId, Long planEntrenamientoId) throws EntityNotFoundException {
        log.info("Inicia proceso de agregarle un atleta a la planEntrenamiento con id = {0}", planEntrenamientoId);

        Optional<AtletaEntity> atletaEntity = atletaRepository.findById(atletaId);
        if (atletaEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.SERVICIO_NOT_FOUND);

        Optional<PlanEntrenamientoEntity> planEntrenamientoEntity = planEntrenamientoRepository
                .findById(planEntrenamientoId);
        if (planEntrenamientoEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

        atletaEntity.get().setPlan(planEntrenamientoEntity.get());
        log.info("Termina proceso de agregarle un atleta a la planEntrenamiento con id = {0}", planEntrenamientoId);
        return atletaEntity.get();
    }

    @Transactional
    public List<AtletaEntity> getAtletas(Long planEntrenamientoId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar los atletas asociados a la planEntrenamiento con id = {0}",
                planEntrenamientoId);
        Optional<PlanEntrenamientoEntity> planEntrenamientoEntity = planEntrenamientoRepository
                .findById(planEntrenamientoId);
        if (planEntrenamientoEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

        return planEntrenamientoEntity.get().getAtletasInscritos();
    }

    @Transactional
    public AtletaEntity getAtleta(Long planEntrenamientoId, Long atletaId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de consultar el atleta con id = {0} de la planEntrenamiento con id = "
                + planEntrenamientoId, atletaId);

        Optional<PlanEntrenamientoEntity> planEntrenamientoEntity = planEntrenamientoRepository
                .findById(planEntrenamientoId);
        if (planEntrenamientoEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

        Optional<AtletaEntity> atletaEntity = atletaRepository.findById(atletaId);
        if (atletaEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.SERVICIO_NOT_FOUND);

        log.info("Termina proceso de consultar el atleta con id = {0} de la planEntrenamiento con id = "
                + planEntrenamientoId, atletaId);

        if (!planEntrenamientoEntity.get().getAtletasInscritos().contains(atletaEntity.get()))
            throw new IllegalOperationException("El atleta no esta asociado con la planEntrenamiento");

        return atletaEntity.get();
    }

    @Transactional
    public List<AtletaEntity> replaceAtletas(Long planEntrenamientoId, List<AtletaEntity> atletas)
            throws EntityNotFoundException {
        log.info("Inicia proceso de actualizar la planEntrenamiento con id = {0}", planEntrenamientoId);
        Optional<PlanEntrenamientoEntity> planEntrenamientoEntity = planEntrenamientoRepository
                .findById(planEntrenamientoId);
        if (planEntrenamientoEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

        for (AtletaEntity atleta : atletas) {
            Optional<AtletaEntity> b = atletaRepository.findById(atleta.getId());
            if (b.isEmpty())
                throw new EntityNotFoundException(ErrorMessage.SERVICIO_NOT_FOUND);

            b.get().setPlan(planEntrenamientoEntity.get());
        }
        return atletas;
    }

}