package co.edu.uniandes.dse.gym.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

@Getter
@Setter
@Entity

public class ActividadEntity extends BaseEntity {
    
    private String nombre;
    private String ubicacion;
    private String cupos;
    private String horario;

    @PodamExclude
    @OneToOne(mappedBy = "actividad")
    private EntrenadorEntity entrenador;

    @PodamExclude
    @OneToOne(mappedBy = "actividad")
    private RestriccionEntity restriccion;

    @PodamExclude
    @ManyToMany
    private List<AtletaEntity> atletasInscritos;

}
