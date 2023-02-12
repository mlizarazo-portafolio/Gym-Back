package co.edu.uniandes.dse.gym.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

public class ActividadEntity extends BaseEntity{
    private String nombre;
    private Date fecha;
    private String ubicacion;
    private String cupos;
    
    @OneToOne(mappedBy = "actividad")
    private EntrenadorEntity entrenador; 

}
