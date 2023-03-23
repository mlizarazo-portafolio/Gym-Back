package co.edu.uniandes.dse.gym.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.gym.entities.SedeEntity;
import co.edu.uniandes.dse.gym.entities.AtletaEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.ErrorMessage;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.repositories.SedeRepository;
import co.edu.uniandes.dse.gym.repositories.AtletaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SedeAtletaService {

    @Autowired
    AtletaRepository atletaRepository;

    @Autowired
    SedeRepository sedeRepository;

    @Transactional
    public AtletaEntity addAtleta(Long atletaId, Long sedeId) throws EntityNotFoundException {
        log.info("Inicia proceso de agregarle un atleta a la sede con id = {0}", sedeId);

        Optional<AtletaEntity> atletaEntity = atletaRepository.findById(atletaId);
        if (atletaEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.SERVICIO_NOT_FOUND);

        Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
        if (sedeEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

        atletaEntity.get().setSede(sedeEntity.get());
        log.info("Termina proceso de agregarle un atleta a la sede con id = {0}", sedeId);
        return atletaEntity.get();
    }

    @Transactional
    public List<AtletaEntity> getAtletas(Long sedeId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar los atletas asociados a la sede con id = {0}", sedeId);
        Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
        if (sedeEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

        return sedeEntity.get().getAtletas();
    }

    @Transactional
    public AtletaEntity getAtleta(Long sedeId, Long atletaId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de consultar el atleta con id = {0} de la sede con id = " + sedeId, atletaId);

        Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
        if (sedeEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

        Optional<AtletaEntity> atletaEntity = atletaRepository.findById(atletaId);
        if (atletaEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.SERVICIO_NOT_FOUND);

        log.info("Termina proceso de consultar el atleta con id = {0} de la sede con id = " + sedeId, atletaId);

        if (!sedeEntity.get().getAtletas().contains(atletaEntity.get()))
            throw new IllegalOperationException("El atleta no esta asociado con la sede");

        return atletaEntity.get();
    }

    @Transactional
    public List<AtletaEntity> replaceAtletas(Long sedeId, List<AtletaEntity> atletas) throws EntityNotFoundException {
        log.info("Inicia proceso de actualizar la sede con id = {0}", sedeId);
        Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
        if (sedeEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

        for (AtletaEntity atleta : atletas) {
            Optional<AtletaEntity> b = atletaRepository.findById(atleta.getId());
            if (b.isEmpty())
                throw new EntityNotFoundException(ErrorMessage.SERVICIO_NOT_FOUND);

            b.get().setSede(sedeEntity.get());
        }
        return atletas;
    }

}
