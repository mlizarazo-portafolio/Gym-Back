package co.edu.uniandes.dse.gym.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestriccionDTO {
    private Long id;
    private Integer edad;
    private String condFisica;
    private ActividadDTO actividad;
}