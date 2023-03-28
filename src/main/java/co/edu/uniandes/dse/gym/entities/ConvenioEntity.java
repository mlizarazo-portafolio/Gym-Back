package co.edu.uniandes.dse.gym.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

@Setter
@Getter
@Entity
public class ConvenioEntity extends  BaseEntity{
 
    @PodamExclude
    @ManyToOne 
    private ConvenioEntity plan;
 
    
    private String nombre;
    private Double descuento;


  



}
