package co.edu.uniandes.dse.gym.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.gym.entities.ReseniaEntity;
import co.edu.uniandes.dse.gym.entities.SedeEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.ErrorMessage;
import co.edu.uniandes.dse.gym.repositories.ReseniaRepository;
import co.edu.uniandes.dse.gym.repositories.SedeRepository;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class ReseniaSedeService {

    @Autowired
    private ReseniaRepository reseniaRepository;

    @Autowired
    private SedeRepository sedeRepository;

    @Transactional
	public ReseniaEntity replaceSede(Long reseniaId, Long sedeId) throws EntityNotFoundException {
		log.info("Inicia proceso de actualizar resenia con id = {0}", reseniaId);
		Optional<ReseniaEntity> reseniaEntity = reseniaRepository.findById(reseniaId);
		if (reseniaEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.RESENIA_NOT_FOUND);

		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);

		reseniaEntity.get().setSede(sedeEntity.get());
		log.info("Termina proceso de actualizar resenia con id = {0}", reseniaId);

		return reseniaEntity.get();
	}

    @Transactional
	public void removeSede(Long reseniaId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar la sede de la resenia con id = {0}", reseniaId);
		Optional<ReseniaEntity> reseniaEntity = reseniaRepository.findById(reseniaId);
		if (reseniaEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.RESENIA_NOT_FOUND);

		Optional<SedeEntity> sedeEntity = sedeRepository
				.findById(reseniaEntity.get().getSede().getId());
		sedeEntity.ifPresent(sede -> sede.getResenias().remove(reseniaEntity.get()));

		reseniaEntity.get().setSede(null);
		log.info("Termina proceso de borrar la sede de la resenia con id = {0}", reseniaId);
	}
    
}
