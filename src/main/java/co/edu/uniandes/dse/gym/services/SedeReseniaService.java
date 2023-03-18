package co.edu.uniandes.dse.gym.services;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.gym.entities.ReseniaEntity;
import co.edu.uniandes.dse.gym.entities.SedeEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.ErrorMessage;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.repositories.ReseniaRepository;
import co.edu.uniandes.dse.gym.repositories.SedeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SedeReseniaService {

    @Autowired
	private ReseniaRepository reseniaRepository;

	@Autowired
	private SedeRepository sedeRepository;

    @Transactional
	public ReseniaEntity addResenia(Long reseniaId, Long sedeId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociarle una resenia a la sede con id = {0}", sedeId);
		
		Optional<ReseniaEntity> reseniaEntity = reseniaRepository.findById(reseniaId);
		if(reseniaEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.RESENIA_NOT_FOUND);
		
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		if(sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);
		
		reseniaEntity.get().setSede(sedeEntity.get());
		log.info("Termina proceso de agregarle una resenia a la sede con id = {0}", sedeId);
		return reseniaEntity.get();
	}

    @Transactional
	public List<ReseniaEntity> getResenias(Long sedeId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar las resenias asociadas a la sede con id = {0}", sedeId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		if(sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);
		
		return sedeEntity.get().getResenias();
	}

    @Transactional
	public ReseniaEntity getResenia(Long sedeId, Long reseniaId) throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de consultar la resenia con id = {0} de la sede con id = " + sedeId, reseniaId);
		
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		if(sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);
		
		Optional<ReseniaEntity> reseniaEntity = reseniaRepository.findById(reseniaId);
		if(reseniaEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.RESENIA_NOT_FOUND);
				
		log.info("Termina proceso de consultar la resenia con id = {0} de la sede con id = " + sedeId, reseniaId);
		
		if(!sedeEntity.get().getResenias().contains(reseniaEntity.get()))
			throw new IllegalOperationException("La resenia no esta asociada con la sede");
		
		return reseniaEntity.get();
	}

    @Transactional
	public List<ReseniaEntity> replaceResenias(Long sedeId, List<ReseniaEntity> resenias) throws EntityNotFoundException {
		log.info("Inicia proceso de actualizar la sede con id = {0}", sedeId);
		Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
		if(sedeEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.SEDE_NOT_FOUND);
		
		for(ReseniaEntity resenia : resenias) {
			Optional<ReseniaEntity> r = reseniaRepository.findById(resenia.getId());
			if(r.isEmpty())
				throw new EntityNotFoundException(ErrorMessage.RESENIA_NOT_FOUND);
			
			r.get().setSede(sedeEntity.get());
		}		
		return resenias;
	}
    
}
