package co.edu.uniandes.dse.gym.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicioDTO {
    private Long id;
    private String servicio;
    private Boolean disponible;
}