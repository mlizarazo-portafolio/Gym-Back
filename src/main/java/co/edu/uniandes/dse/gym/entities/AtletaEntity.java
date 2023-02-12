package co.edu.uniandes.dse.gym.entities;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Array;
import java.sql.Date;

@Getter
@Setter
@Entity
public class AtletaEntity extends BaseEntity {
    private String nombre;
    private String login;
    private String tipoSangre;
    private String direccion;
    private Date fechaNacimiento;
    private Double altura;
    private Double peso;

    // private PlanEntrenamientoEntity planEntrenamiento;
    // private SedeEntity sedeGimnasio;
    // private DeportologoEntity deportologo;
    // private ArrayList<ActividadEntity> clasesInscritas;

}
