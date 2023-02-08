package co.edu.uniandes.dse.gym.entities;

import java.sql.Date;

import javax.persistence.Entity;

@Entity
public class ActividadEntity extends BaseEntity{
    private String nombre;
    private Date fecha;
    private String ubicacion;
    private String cupos;
    
    
}
