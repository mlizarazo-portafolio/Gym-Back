package co.edu.uniandes.dse.gym.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class DeportologoDTO {
    private Long id;
    private String nombre;
    private String login;
    private String experiencia;
    private String foto;

    private SedeDTO sede;

}
