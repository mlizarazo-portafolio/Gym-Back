package co.edu.uniandes.dse.gym.services;

import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.Period;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.ErrorMessage;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.gym.entities.AtletaEntity;
import co.edu.uniandes.dse.gym.entities.DeportologoEntity;
import co.edu.uniandes.dse.gym.repositories.AtletaRepository;
import co.edu.uniandes.dse.gym.repositories.DeportologoRepository;
import co.edu.uniandes.dse.gym.repositories.SedeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AtletaService {

    @Autowired
    AtletaRepository atletaRepository;

    @Autowired
    SedeRepository sedeRepository;

    @Autowired
    DeportologoRepository deportologoRepository;

    @Transactional
    public List<AtletaEntity> getAtletas() {
        log.info("Inicia proceso de consultar todos los atletas");
        return atletaRepository.findAll();
    }

    @Transactional
    public AtletaEntity getAtleta(Long id) throws EntityNotFoundException {
        Optional<AtletaEntity> atleta = atletaRepository.findById(id);
        if (atleta.isPresent()) {
            return atleta.get();
        } else {
            throw new EntityNotFoundException("No existe un atleta con el id " + id);
        }
    }

    @Transactional
    public AtletaEntity createAtleta(AtletaEntity atleta) throws EntityNotFoundException, IllegalOperationException {
        if (atleta.getDeportologo() == null) {
            throw new IllegalOperationException("No se puede crear un atleta sin un atleta");
        }
        Optional<DeportologoEntity> atletaEntity = deportologoRepository.findById(atleta.getDeportologo().getId());
        if (!validateNacimiento(atleta.getFechaNacimiento())) {
            throw new IllegalOperationException("La fecha de nacimiento no es válida");
        }
        if (atleta.getDireccion() == null) {
            throw new IllegalOperationException("La dirección no es válida");
        }

        if (!validateSangre(atleta.getTipoSangre())) {
            throw new IllegalOperationException("El tipo de sangre no es válido");
        }
        if (!validateAltura(atleta.getAltura())) {
            throw new IllegalOperationException("La altura no es válida");
        }
        if (!validatePeso(atleta.getPeso())) {
            throw new IllegalOperationException("El peso no es válido");
        }
        if (atleta.getNombre() != null) {
            if (atleta.getNombre().equals("")) {
                throw new IllegalOperationException("Tiene que tener un nombre");
            }
        }
        if (atleta.getNombre() == null) {
            throw new IllegalOperationException("Tiene que tener un nombre");
        }

        log.info("Termina proceso de creación del atleta");
        atleta.setDeportologo(atletaEntity.get());
        return atletaRepository.save(atleta);
    }

    @Transactional
    public AtletaEntity updateAtleta(Long id, AtletaEntity atleta)
            throws EntityNotFoundException, IllegalOperationException {
        if (atleta.getId() != null && !atleta.getId().equals(id)) {
            throw new IllegalOperationException("No se puede actualizar el id de un atleta");
        }
        if (atleta.getDeportologo() == null) {
            throw new IllegalOperationException("No se puede actualizar un atleta sin un atleta");
        }
        if (!validateNacimiento(atleta.getFechaNacimiento())) {
            throw new IllegalOperationException("La fecha de nacimiento no es válida");
        }
        if (atleta.getDireccion() == null) {
            throw new IllegalOperationException("La dirección no es válida");
        }

        if (!validateSangre(atleta.getTipoSangre())) {
            throw new IllegalOperationException("El tipo de sangre no es válido");
        }
        if (!validateAltura(atleta.getAltura())) {
            throw new IllegalOperationException("La altura no es válida");
        }
        if (!validatePeso(atleta.getPeso())) {
            throw new IllegalOperationException("El peso no es válido");
        }
        Optional<AtletaEntity> atletaExistente = atletaRepository.findById(id);
        if (atletaExistente.isPresent()) {
            return atletaRepository.save(atleta);
        } else {
            throw new EntityNotFoundException("No existe un atleta con el id " + id);
        }

    }

    @Transactional
    public void deleteAtleta(Long atletaId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de borrar el atleta con id = {0}", atletaId);
        Optional<AtletaEntity> atletaEntity = atletaRepository.findById(atletaId);
        if (atletaEntity.isEmpty()) {
            throw new EntityNotFoundException(ErrorMessage.ATLETA_NOT_FOUND);
        }
        atletaRepository.deleteById(atletaId);
        log.info("Termina proceso de borrar el atleta con id = {0}", atletaId);
    }

    private boolean validatePeso(Integer peso) {
        String weight = Integer.toString(peso);
        String regex = "^([1-9][0-9]{0,2}|0)(\\.[0-9]{1,2})?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(weight);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }

    }

    private boolean validateAltura(Integer altura) {
        String s = Integer.toString(altura);
        String stringValidador = "^(1[0-9]{2}|[5-9][0-9])|^(1\\.[0-9]{2}|[5-9]\\.[0-9]{1,2})$";
        Pattern pattern = Pattern.compile(stringValidador);
        Matcher matcher = pattern.matcher(s);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }

    }

    private boolean validateSangre(String tipoSangre) {
        String stringValidador = "^(A|B|AB|O)[+-]$";
        Pattern pattern = Pattern.compile(stringValidador);
        Matcher matcher = pattern.matcher(tipoSangre);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }

    }

    private boolean validateNacimiento(Date fechaNacimiento) {
        LocalDate localBirthdate = fechaNacimiento.toInstant().atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();
        LocalDate today = LocalDate.now();
        Period age = Period.between(localBirthdate, today);
        if (age.getYears() >= 16) {
            return true;
        } else {
            return false;
        }

    }

}
