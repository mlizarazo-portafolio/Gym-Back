package co.edu.uniandes.dse.gym.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AtletaDTO {
    private String nombre;
    private String login;
    private String tipoSangre;
    private String direccion;
    private Integer altura;
    private Integer peso;

    private DeportologoDTO deportologo;
    private SedeDTO sede;

}
