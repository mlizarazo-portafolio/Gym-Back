package co.edu.uniandes.dse.gym.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class PlanEntrenamientoDTO{

    private Long id;
    private String nombre;
    private String objetivoBasico;
    private String descripcion;
    private String dirrecion;
    private Integer duracion;
    private Integer costo;

}