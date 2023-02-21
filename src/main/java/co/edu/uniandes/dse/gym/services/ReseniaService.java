package co.edu.uniandes.dse.gym.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

import co.edu.uniandes.dse.gym.repositories.ReseniaRepository;
import co.edu.uniandes.dse.gym.entities.ReseniaEntity;
import co.edu.uniandes.dse.gym.repositories.SedeRepository;
import co.edu.uniandes.dse.gym.entities.SedeEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.ErrorMessage;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReseniaService {

    @Autowired
    ReseniaRepository reseniaRepository;

    @Autowired
    SedeRepository sedeRepository;

    private String invalidPuntuacion = "Puntuacion no es valida";

    @Transactional
    public ReseniaEntity createResenia (ReseniaEntity reseniaEntity) throws EntityNotFoundException, IllegalOperationException{
        log.info("Inicia proceso de creacion de Resenia");

        if (reseniaEntity.getSede() == null)
            throw new IllegalOperationException("Sede no es valida");

        Optional<SedeEntity> sedeEntity = sedeRepository.findById(reseniaEntity.getSede().getId());
        if (sedeEntity.isEmpty()){
            throw new IllegalOperationException("Sede no es valida");
        }
        if (!validateString(reseniaEntity.getUsuario())){
            throw new IllegalOperationException("Usuario no es valido");
        }
        if (reseniaEntity.getPuntuacion() == null){
            throw new IllegalOperationException(invalidPuntuacion);
        }
        if (reseniaEntity.getPuntuacion()<0 || reseniaEntity.getPuntuacion()>5){
            throw new IllegalOperationException(invalidPuntuacion);
        }
        if (!validateString(reseniaEntity.getComentario())){
            throw new IllegalOperationException("Comentario no es valido");
        }

        reseniaEntity.setSede(sedeEntity.get());
        log.info("Termina proceso de creacion de Resenia");
        return reseniaRepository.save(reseniaEntity);
    }

    @Transactional
    public List<ReseniaEntity> getResenias(){
        log.info("Inicia proceso de consultar todas las resenias");
        return reseniaRepository.findAll();
    }

    @Transactional
    public ReseniaEntity getReseniaById(Long reseniaId) throws EntityNotFoundException{
        log.info("Inicia proceso de consultar la resenia con id = {0}", reseniaId);
        Optional<ReseniaEntity> reseniaEntity = reseniaRepository.findById(reseniaId);
        if (reseniaEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.RESENIA_NOT_FOUND);
        }
        log.info("Termina proceso de consultar la resenia con id = {0}", reseniaId);
        return reseniaEntity.get();
    }

    @Transactional
    public ReseniaEntity updateResenia(Long reseniaId, ReseniaEntity resenia) throws EntityNotFoundException, IllegalOperationException{
        log.info("Inicia proceso de actualizar la resenia con id = {0}", reseniaId);
        Optional<ReseniaEntity> reseniaEntity = reseniaRepository.findById(reseniaId);
        if (reseniaEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.RESENIA_NOT_FOUND);
        }
        
        if (!validateString(resenia.getUsuario())){
            throw new IllegalOperationException("Usuario no es valido");
        }
        if (resenia.getPuntuacion() == null){
            throw new IllegalOperationException(invalidPuntuacion);
        }
        if (resenia.getPuntuacion()<0 || resenia.getPuntuacion()>5){
            throw new IllegalOperationException(invalidPuntuacion);
        }
        if (!validateString(resenia.getComentario())){
            throw new IllegalOperationException("Comentario no es valido");
        }

        resenia.setId(reseniaId);
        log.info("Termina proceso de actualizar la resenia con id = {0}", reseniaId);
        return reseniaRepository.save(resenia);
    }

    @Transactional
    public void deleteResenia(Long reseniaId) throws EntityNotFoundException, IllegalOperationException{
        log.info("Inicia proceso de borrar la resenia con id = {0}", reseniaId);
        Optional<ReseniaEntity> reseniaEntity = reseniaRepository.findById(reseniaId);
        if (reseniaEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.RESENIA_NOT_FOUND);
        }
        reseniaRepository.deleteById(reseniaId);
        log.info("Termina proceso de borrar la resenia con id = {0}", reseniaId);
    }

    public boolean validateString(String string){
        return !(string==null || string.isEmpty());
    }
}
