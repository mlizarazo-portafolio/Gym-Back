package co.edu.uniandes.dse.gym.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

import uk.co.jemos.podam.common.PodamExclude;

@Getter
@Setter
@Entity

public class ServicioEntity extends BaseEntity {

    private String servicio;
    private Boolean disponible;

    @PodamExclude
    @ManyToOne
    private SedeEntity sede;
    
}