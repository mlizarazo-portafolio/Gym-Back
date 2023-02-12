package co.edu.uniandes.dse.gym.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class PlanEntrenamientoEntity extends  BaseEntity{

/** 
 *     @ManyToOne
    private SedeEntity sede;
 * 
 * */    
    
                  

    private String objetivoBasico;
    private String nombre;
    private String descripcion;
    private String dirrecion;
    private String coordenada;
    private Integer duracion;
    private Integer costo;


    

}

