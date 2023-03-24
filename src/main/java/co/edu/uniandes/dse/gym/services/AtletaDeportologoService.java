package co.edu.uniandes.dse.gym.services;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.gym.entities.AtletaEntity;
import co.edu.uniandes.dse.gym.entities.DeportologoEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.ErrorMessage;
import co.edu.uniandes.dse.gym.repositories.AtletaRepository;
import co.edu.uniandes.dse.gym.repositories.DeportologoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AtletaDeportologoService {

	@Autowired
	private AtletaRepository atletaRepository;

	@Autowired
	private DeportologoRepository deportologoRepository;
	
	@Transactional
	public AtletaEntity replaceDeportologo(Long atletaId, Long deportologoId) throws EntityNotFoundException {
		log.info("Inicia proceso de actualizar el atelta con id = {0}", atletaId);
		Optional<AtletaEntity> atletaEntity = atletaRepository.findById(atletaId);
		if (atletaEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.ATLETA_NOT_FOUND);

		Optional<DeportologoEntity> deportologoEntity = deportologoRepository.findById(deportologoId);
		if (deportologoEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.DEPORTOLOGO_NOT_FOUND);

		atletaEntity.get().setDeportologo(deportologoEntity.get());
		log.info("Termina proceso de actualizar el atleta con id = {0}", atletaId);

		return atletaEntity.get();
	}

	@Transactional
	public void removeDeportologo(Long atletaId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar el Deportologo del atleta con id = {0}", atletaId);
		Optional<AtletaEntity> atletaEntity = atletaRepository.findById(atletaId);
		if (atletaEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.ATLETA_NOT_FOUND);

		Optional<DeportologoEntity> deportologoEntity = deportologoRepository
				.findById(atletaEntity.get().getDeportologo().getId());
		deportologoEntity.ifPresent(deportologo -> deportologo.getValoracionAtletas().remove(atletaEntity.get()));

		atletaEntity.get().setDeportologo(null);
		log.info("Termina proceso de borrar el Deportologo del atleta con id = {0}", atletaId);
	}
}
