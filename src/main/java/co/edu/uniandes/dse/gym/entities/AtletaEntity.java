package co.edu.uniandes.dse.gym.entities;

import javax.persistence.Entity;

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

}
