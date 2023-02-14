package co.edu.uniandes.dse.gym.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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

/* 
@ManyToMany
private List<SedeEntity> sedes = new ArrayList<>();
  
@OneToMany (mappedBy = "plan",  cascade = CascadeType.PERSIST, orphanRemoval = true)
private List<ConvenioEntity> convenios = new ArrayList<>();
  
 
@OneToMany (mappedBy = "plan", cascade = CascadeType.PERSIST, orphanRemoval = true)
private List<AtletaEntity> atletasInscritos = new ArrayList<>();

*/ 
 
    
                  

    private String objetivoBasico;
    private String nombre;
    private String descripcion;
    private String dirrecion;
    private String coordenada;
    private Integer duracion;
    private Integer costo;


    

}

