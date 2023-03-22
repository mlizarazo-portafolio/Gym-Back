package co.edu.uniandes.dse.gym.services;

import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.Period;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.gym.entities.AtletaEntity;
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
        if (atleta.getId() != null) {
            throw new IllegalOperationException("No se puede crear un atleta sin un id");
        }
        if (atleta.getSede() != null) {
            throw new IllegalOperationException("No se puede crear un atleta con una sede");
        }
        if (atleta.getDeportologo() == null) {
            throw new IllegalOperationException("No se puede crear un atleta sin un deportologo");
        }
        if (!validateNacimiento(atleta.getFechaNacimiento())) {
            throw new IllegalOperationException("La fecha de nacimiento no es válida");
        }
        if (atleta.getDireccion() == null) {
            throw new IllegalOperationException("La dirección no es válida");
        }
        if (!validateDireccion(atleta.getDireccion())) {
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

        if (atleta.getNombre().equals("") || atleta.getNombre() == null) {
            throw new IllegalOperationException("Tiene que tener un nombre");
        }
        log.info("Termina proceso de creación del atleta");

        return atletaRepository.save(atleta);
    }

    @Transactional
    public AtletaEntity updateAtleta(Long id, AtletaEntity atleta)
            throws EntityNotFoundException, IllegalOperationException {
        if (atleta.getId() == null) {
            throw new IllegalOperationException("No se puede actualizar un atleta sin un id");
        }
        if (atleta.getSede() != null) {
            throw new IllegalOperationException("No se puede actualizar un atleta con una sede");
        }
        if (atleta.getDeportologo() == null) {
            throw new IllegalOperationException("No se puede actualizar un atleta sin un deportologo");
        }
        if (!validateNacimiento(atleta.getFechaNacimiento())) {
            throw new IllegalOperationException("La fecha de nacimiento no es válida");
        }
        if (atleta.getDireccion() == null) {
            throw new IllegalOperationException("La dirección no es válida");
        }
        if (!validateDireccion(atleta.getDireccion())) {
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
        log.info("Termina proceso de actualización del atleta");
        return atletaRepository.save(atleta);
    }

    @Transactional
    public void deleteAtleta(Long id) throws EntityNotFoundException {
        Optional<AtletaEntity> atleta = atletaRepository.findById(id);
        if (atleta.isPresent()) {
            atletaRepository.delete(atleta.get());
        } else {
            throw new EntityNotFoundException("No existe un atleta con el id " + id);
        }
        log.info("Termina proceso de borrado del atleta con id = " + id);
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
        String regex = "^(1[0-9]{2}|[5-9][0-9])|^(1\\.[0-9]{2}|[5-9]\\.[0-9]{1,2})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }

    }

    private boolean validateSangre(String tipoSangre) {
        String regex = "^(A|B|AB|O)[+-]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(tipoSangre);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }

    }

    private boolean validateDireccion(String address) {
        String regex = "\\b(Carrera|Calle)\\s\\d+\\s#\\s?\\d+[-a-cA-C ]*\\s?,\\s\\p{L}+,\\sColombia\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(address);
        if (matcher.matches()) {
            // System.out.println("The address is valid.");
            return true;
        } else {
            // System.out.println("The address is not valid.");
            return false;
        }
    }

    private boolean validateNacimiento(Date birthdate) {
        LocalDate localBirthdate = birthdate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();
        Period age = Period.between(localBirthdate, today);
        if (age.getYears() >= 16) {
            return true;
        } else {
            return false;
        }

    }

}
