package co.edu.uniandes.dse.gym.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

public class ActividadEntity extends BaseEntity {
    
    private String nombre;
    private String ubicacion;
    private String cupos;
    private String horario;

    @OneToOne(mappedBy = "actividad")
    private EntrenadorEntity entrenador;

    @OneToOne(mappedBy = "actividad")
    private RestriccionEntity restriccion;

    @ManyToMany
    private List<AtletaEntity> atletasInscritos;

}
