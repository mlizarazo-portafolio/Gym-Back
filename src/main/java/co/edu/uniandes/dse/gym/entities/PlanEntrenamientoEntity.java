package co.edu.uniandes.dse.gym.entities;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class PlanEntrenamientoEntity extends BaseEntity{

    private String objetivoBasico;
    private String nombre;
    private String descripcion;
    private Double duracion;
    private Double costo;
    

}
