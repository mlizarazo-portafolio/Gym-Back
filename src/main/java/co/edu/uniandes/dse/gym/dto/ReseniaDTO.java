package co.edu.uniandes.dse.gym.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReseniaDTO {
    private Long id;
    private String usuario;
    private Integer puntuacion;
    private String comentario;

    private SedeDTO sede;
     
}
