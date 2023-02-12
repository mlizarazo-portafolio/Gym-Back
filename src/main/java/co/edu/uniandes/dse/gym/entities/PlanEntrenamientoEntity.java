package co.edu.uniandes.dse.gym.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;

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

