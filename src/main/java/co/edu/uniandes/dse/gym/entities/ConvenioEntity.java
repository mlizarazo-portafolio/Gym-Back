package co.edu.uniandes.dse.gym.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class ConvenioEntity extends  BaseEntity{
/** 
    @ManyToOne 
    private PlanEntrenamientoEntity plan;
*/    
 
    private String nombre;
    private Double descuento;



}
