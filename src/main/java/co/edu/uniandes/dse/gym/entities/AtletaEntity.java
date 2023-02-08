package co.edu.uniandes.dse.gym.entities;

import javax.persistence.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Array;

@Entity

public class AtletaEntity extends BaseEntity {
    private String nombre;
    private int edad;
    private String sexo;
    private String tipoSangre;
    // private String alergias

    private String direccion;
    private String telefono;
    private String correo;
    private String documento;
    private String fechaNacimiento;
    private PlanEntrenamientoEntity planAdquirido;
    private SedeEntity sede;
    // private DeportologoEntity deportologo;
    private ArrayList<ActividadEntity> clasesInscritas;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

}
