package co.edu.uniandes.dse.gym.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter; 
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

@Getter
@Setter
@Entity
public class ReseniaEntity extends BaseEntity {

    private String usuario;
    private Integer puntuacion;
    private String comentario;

    @PodamExclude
    @ManyToOne
    private SedeEntity sede;

    
}
