package co.edu.uniandes.dse.gym.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;


import java.util.List;
import java.util.ArrayList;

@Setter
@Getter
@Entity

public class PlanEntrenamientoEntity extends  BaseEntity{

@PodamExclude
@ManyToMany
private List<SedeEntity> sedes = new ArrayList<>();

@PodamExclude
@OneToMany (mappedBy = "plan",  cascade = CascadeType.PERSIST, orphanRemoval = true)
private List<ConvenioEntity> convenios = new ArrayList<>();
  

@PodamExclude
@OneToMany (mappedBy = "plan", cascade = CascadeType.PERSIST, orphanRemoval = true)
private List<AtletaEntity> atletasInscritos = new ArrayList<>();
 
 
    
                  
    private Long id;
    private String nombre;
    private String objetivoBasico;
    private String descripcion;
    private String dirrecion;
    private Integer duracion;
    private Integer costo;
    public void add(PlanEntrenamientoEntity planEntrenamientoEntity) {
    }

    

}

