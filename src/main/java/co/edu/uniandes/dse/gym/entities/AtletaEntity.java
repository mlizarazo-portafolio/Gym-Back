package co.edu.uniandes.dse.gym.entities;

import javax.persistence.Entity;

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
    private String planAdquirido;

}
