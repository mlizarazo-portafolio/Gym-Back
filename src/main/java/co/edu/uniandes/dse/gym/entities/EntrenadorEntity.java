package co.edu.uniandes.dse.gym.entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class EntrenadorEntity extends BaseEntity{

    private String nombreEntrenador;
    private String imagenEntrenador;
    private String trayectoriaProfesional;
    @OneToOne
    private ActividadEntity actividad;
    
}
