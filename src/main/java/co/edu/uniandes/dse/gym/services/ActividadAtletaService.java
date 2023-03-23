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
public class ActividadAtletaService {

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private AtletaRepository atletaRepository;

    @Transactional
    public AtletaEntity addAtleta(Long actividadId, Long atletaId) throws EntityNotFoundException {
        log.info("Inicia proceso de asociarle una atleta a la actividad con id = {0}", actividadId);
        Optional<AtletaEntity> atletaEntity = atletaRepository.findById(atletaId);
        if (atletaEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

        Optional<ActividadEntity> actividadEntity = actividadRepository.findById(actividadId);
        if (actividadEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.ACTIVIDAD_NOT_FOUND);

        actividadEntity.get().getAtletasInscritos().add(atletaEntity.get());
        log.info("Termina proceso de asociarle una atleta a la actividad con id = {0}", actividadId);
        return atletaEntity.get();
    }

    @Transactional
    public List<AtletaEntity> getAtletas(Long actividadId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar todas las atletas de la actividad con id = {0}", actividadId);
        Optional<ActividadEntity> actividadEntity = actividadRepository.findById(actividadId);
        if (actividadEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.ACTIVIDAD_NOT_FOUND);
        log.info("Finaliza proceso de consultar todas las atletas de la actividad con id = {0}", actividadId);
        return actividadEntity.get().getAtletasInscritos();
    }

    @Transactional
    public AtletaEntity getAtleta(Long actividadId, Long atletaId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de consultar una atleta de la actividad con id = {0}", actividadId);
        Optional<AtletaEntity> atletaEntity = atletaRepository.findById(atletaId);
        Optional<ActividadEntity> actividadEntity = actividadRepository.findById(actividadId);

        if (atletaEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

        if (actividadEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.ACTIVIDAD_NOT_FOUND);
        log.info("Termina proceso de consultar una atleta de la actividad con id = {0}", actividadId);
        if (!actividadEntity.get().getAtletasInscritos().contains(atletaEntity.get()))
            throw new IllegalOperationException("La atleta no esta asociada con la actividad");

        return atletaEntity.get();
    }

    @Transactional
    public List<AtletaEntity> replaceAtletas(Long actividadId, List<AtletaEntity> list) throws EntityNotFoundException {
        log.info("Inicia proceso de reemplazar las atletas de la actividad con id = {0}", actividadId);
        Optional<ActividadEntity> actividadEntity = actividadRepository.findById(actividadId);
        if (actividadEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.ACTIVIDAD_NOT_FOUND);

        for (AtletaEntity atleta : list) {
            Optional<AtletaEntity> atletaEntity = atletaRepository.findById(atleta.getId());
            if (atletaEntity.isEmpty())
                throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

            if (!actividadEntity.get().getAtletasInscritos().contains(atletaEntity.get()))
                actividadEntity.get().getAtletasInscritos().add(atletaEntity.get());
        }
        log.info("Termina proceso de reemplazar las atletas de la actividad con id = {0}", actividadId);
        return getAtletas(actividadId);
    }

    @Transactional
    public void removeAtleta(Long actividadId, Long atletaId) throws EntityNotFoundException {
        log.info("Inicia proceso de borrar una atleta de la actividad con id = {0}", actividadId);
        Optional<AtletaEntity> atletaEntity = atletaRepository.findById(atletaId);
        Optional<ActividadEntity> actividadEntity = actividadRepository.findById(actividadId);

        if (atletaEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

        if (actividadEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.ACTIVIDAD_NOT_FOUND);

        actividadEntity.get().getAtletasInscritos().remove(atletaEntity.get());

        log.info("Termina proceso de borrar una atleta de la actividad con id = {0}", actividadId);
    }

}
