package co.edu.uniandes.dse.gym.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.gym.entities.DeportologoEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.repositories.AtletaRepository;
import co.edu.uniandes.dse.gym.repositories.DeportologoRepository;
import co.edu.uniandes.dse.gym.repositories.SedeRepository;

@Service
public class DeportologoService {
    @Autowired
    DeportologoRepository deportologoRepository;

    @Autowired
    SedeRepository sedeRepository;

    @Autowired
    AtletaRepository atletaRepository;

    @Transactional
    public List<DeportologoEntity> getDeportologos() {
        return deportologoRepository.findAll();
    }

    @Transactional
    public DeportologoEntity getDeportologo(Long id) throws EntityNotFoundException {
        Optional<DeportologoEntity> deportologo = deportologoRepository.findById(id);
        if (deportologo.isPresent()) {
            return deportologo.get();
        } else {
            throw new EntityNotFoundException("No existe un deportologo con el id " + id);
        }
    }

    @Transactional
    public DeportologoEntity createDeportologo(DeportologoEntity deportologo)
            throws EntityNotFoundException, IllegalOperationException {

        if (deportologo.getId() != null) {
            throw new IllegalOperationException("No se puede crear un deportologo sin un id");
        }
        if (deportologo.getSede() != null) {
            throw new IllegalOperationException("No se puede crear un deportologo sin una sede");
        }
        if (deportologo.getValoracionAtletas() != null) {

            throw new IllegalOperationException("No se puede crear un deportologo sin una valoracion de atletas");
        }
        if (deportologo.getExperiencia() != null) {

            throw new IllegalOperationException("Tiene que tener alguna experiencia");

        }
        if (deportologo.getNombre() != null) {

            throw new IllegalOperationException("Tiene que tener un nombre");

        }
        if (deportologo.getFoto() != null) {
            throw new IllegalOperationException("Tiene que tener una foto");
        }

        return deportologoRepository.save(deportologo);
        // Optional < DeportologoEntity > deportologoExistente =
        // deportologoRepository.findById(deportologo.getId());

    }

    @Transactional
    public DeportologoEntity updateDeportologo(Long id, DeportologoEntity deportologo)
            throws EntityNotFoundException, IllegalOperationException {
        if (deportologo.getId() != null && !deportologo.getId().equals(id)) {
            throw new IllegalOperationException("No se puede actualizar el id de un deportologo");
        }
        if (deportologo.getSede() != null) {
            throw new IllegalOperationException("No se puede crear un deportologo sin una sede");
        }
        if (deportologo.getValoracionAtletas() != null) {
            throw new IllegalOperationException("No se puede crear un deportologo sin una valoracion de atletas");
        }
        if (deportologo.getExperiencia() != null) {

            throw new IllegalOperationException("Tiene que tener alguna experiencia");

        }
        if (deportologo.getNombre() != null) {

            throw new IllegalOperationException("Tiene que tener un nombre");

        }
        if (deportologo.getFoto() != null) {
            throw new IllegalOperationException("Tiene que tener una foto");
        }
        Optional<DeportologoEntity> deportologoExistente = deportologoRepository.findById(id);
        if (deportologoExistente.isPresent()) {
            DeportologoEntity deportologoActualizado = deportologoExistente.get();
            deportologoActualizado.setNombre(deportologo.getNombre());
            deportologoActualizado.setExperiencia(deportologo.getExperiencia());
            deportologoActualizado.setFoto(deportologo.getFoto());
            deportologoActualizado.setSede(deportologo.getSede());
            deportologoActualizado.setValoracionAtletas(deportologo.getValoracionAtletas());
            return deportologoRepository.save(deportologoActualizado);
        } else {
            throw new EntityNotFoundException("No existe un deportologo con el id " + id);
        }
    }

    @Transactional
    public void deleteDeportologo(Long id) throws EntityNotFoundException {
        Optional<DeportologoEntity> deportologo = deportologoRepository.findById(id);
        if (deportologo.isPresent()) {
            deportologoRepository.delete(deportologo.get());
        } else {
            throw new EntityNotFoundException("No existe un deportologo con el id " + id);
        }
    }

}
