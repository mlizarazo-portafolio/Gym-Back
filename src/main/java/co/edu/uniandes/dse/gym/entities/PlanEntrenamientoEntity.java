package co.edu.uniandes.dse.gym.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;

@Setter
@Getter
@Entity

public class PlanEntrenamientoEntity extends BaseEntity{

    private String objetivoBasico;
    private String nombre;
    private String descripcion;
    private Double duracion;
    private Double costo;
    
    @ManyToMany
    private List<SedeEntity> sedes = new ArrayList<>();

}
