package co.edu.uniandes.dse.gym.dto;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ConvenioDTO{

    private Long id;
    private String nombre;
    private Double descuento;

    private ConvenioDTO plan;

}