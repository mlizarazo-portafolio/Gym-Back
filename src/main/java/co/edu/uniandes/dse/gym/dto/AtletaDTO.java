package co.edu.uniandes.dse.gym.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AtletaDTO {
    private Long id;
    private String nombre;
    private String login;
    private String tipoSangre;
    private String direccion;
    private Integer altura;
    private Integer peso;
    private Date fechaNacimiento;
    
    private DeportologoDTO deportologo;
    private SedeDTO sede;

}
