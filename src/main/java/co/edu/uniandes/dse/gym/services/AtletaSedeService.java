package co.edu.uniandes.dse.gym.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.gym.entities.AtletaEntity;
import co.edu.uniandes.dse.gym.entities.SedeEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.ErrorMessage;
import co.edu.uniandes.dse.gym.repositories.AtletaRepository;
import co.edu.uniandes.dse.gym.repositories.SedeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AtletaSedeService {

    @Autowired
    SedeRepository sedeRepository;

    @Autowired
    AtletaRepository atletaRepository;

    @Transactional
    public AtletaEntity replaceSede(Long atletaId, Long sedeId) throws EntityNotFoundException {
        log.info("Inicia proceso de actualizar atleta con id = {0}", atletaId);
        Optional<AtletaEntity> atletaEntity = atletaRepository.findById(atletaId);
        if (atletaEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.SERVICIO_NOT_FOUND);

        Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
        if (sedeEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

        atletaEntity.get().setSede(sedeEntity.get());
        log.info("Termina proceso de actualizar atleta con id = {0}", atletaId);

        return atletaEntity.get();
    }

}
