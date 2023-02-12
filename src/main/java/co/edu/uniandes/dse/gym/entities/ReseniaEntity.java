package co.edu.uniandes.dse.gym.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ReseniaEntity extends BaseEntity {

    private String usuario;
    private Double puntuacion;
    private String comentario;

    @ManyToOne
    private SedeEntity sede;

    
}
