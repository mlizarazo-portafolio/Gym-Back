package co.edu.uniandes.dse.gym.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Array;
import java.sql.Date;

@Entity

public class AtletaEntity extends BaseEntity {
    private String nombre;
    private String login;
    private String tipoSangre;
    private String direccion;
    private Date fechaNacimiento;
    private Integer altura;
    private Integer peso;
    // private PlanEntrenamientoEntity planEntrenamiento;
    // private SedeEntity sedeGimnasio;
    // private DeportologoEntity deportologo;
    // private ArrayList<ActividadEntity> clasesInscritas;
    @ManyToOne
    public SedeEntity sede;
    @ManyToOne
    public PlanEntrenamientoEntity plan;
    @ManyToMany(mappedBy = "atletasInscritos")
    public ArrayList<ActividadEntity> actividadesInscritas;
    // @ManyToOne
    // public DeportologoEntity deportologo;

}