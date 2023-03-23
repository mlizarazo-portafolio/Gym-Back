package co.edu.uniandes.dse.gym.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActividadDTO {
    private Long id;
    private String nombre;
    private Integer maxParticipantes;
    private String horario;
    private String tipo;
    private EntrenadorDTO entrenador;
    
}
