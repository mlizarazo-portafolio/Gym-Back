package co.edu.uniandes.dse.gym.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.gym.entities.AtletaEntity;
import co.edu.uniandes.dse.gym.entities.DeportologoEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.ErrorMessage;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.repositories.AtletaRepository;
import co.edu.uniandes.dse.gym.repositories.DeportologoRepository;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class DeportologoAtletaService {

	@Autowired
	private AtletaRepository atletaRepository;

	@Autowired
	private DeportologoRepository deportologoRepository;
	

	@Transactional
	public AtletaEntity addAtleta(Long atletaId, Long deportologoId) throws EntityNotFoundException {
		log.info("Inicia proceso de agregarle un atleta al deportologo con id = {0}", deportologoId);
		
		Optional<AtletaEntity> atletaEntity = atletaRepository.findById(atletaId);
		if(atletaEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.ATLETA_NOT_FOUND);
		
		Optional<DeportologoEntity> deportologoEntity = deportologoRepository.findById(deportologoId);
		if(deportologoEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.DEPORTOLOGO_NOT_FOUND);
		
		atletaEntity.get().setDeportologo(deportologoEntity.get());
		log.info("Termina proceso de agregarle un atleta al deportologo con id = {0}", deportologoId);
		return atletaEntity.get();
	}

	@Transactional
	public List<AtletaEntity> getAtletas(Long deportologoId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar los atletas asociados al deportologo con id = {0}", deportologoId);
		Optional<DeportologoEntity> deportologoEntity = deportologoRepository.findById(deportologoId);
		if(deportologoEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.DEPORTOLOGO_NOT_FOUND);
		
		return deportologoEntity.get().getAtletas();
	}


	@Transactional
	public AtletaEntity getAtleta(Long deportologoId, Long atletaId) throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de consultar el atleta con id = {0} del deportologo con id = " + deportologoId, atletaId);
		
		Optional<DeportologoEntity> deportologoEntity = deportologoRepository.findById(deportologoId);
		if(deportologoEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.DEPORTOLOGO_NOT_FOUND);
		
		Optional<AtletaEntity> atletaEntity = atletaRepository.findById(atletaId);
		if(atletaEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.ATLETA_NOT_FOUND);
				
		log.info("Termina proceso de consultar el atleta con id = {0} del deportologo con id = " + deportologoId, atletaId);
		
		if(!deportologoEntity.get().getAtletas().contains(atletaEntity.get()))
			throw new IllegalOperationException("The atleta is not associated to the deportologo");
		
		return atletaEntity.get();
	}


	@Transactional
	public List<AtletaEntity> replaceAtletas(Long deportologoId, List<AtletaEntity> atletas) throws EntityNotFoundException {
		log.info("Inicia proceso de actualizar el deportologo con id = {0}", deportologoId);
		Optional<DeportologoEntity> deportologoEntity = deportologoRepository.findById(deportologoId);
		if(deportologoEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.DEPORTOLOGO_NOT_FOUND);
		
		for(AtletaEntity atleta : atletas) {
			Optional<AtletaEntity> b = atletaRepository.findById(atleta.getId());
			if(b.isEmpty())
				throw new EntityNotFoundException(ErrorMessage.ATLETA_NOT_FOUND);
			
			b.get().setDeportologo(deportologoEntity.get());
		}		
		return atletas;
	}
}