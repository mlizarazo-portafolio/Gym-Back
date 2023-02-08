package co.edu.uniandes.dse.gym.entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class EntrenadorEntity extends BaseEntity{

    private String nombreEntrenador;
    private String imagenEntrenador;
    private String trayectoriaProfesional;

    
}
