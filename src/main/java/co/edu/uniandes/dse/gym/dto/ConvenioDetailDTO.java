package co.edu.uniandes.dse.gym.dto;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter

public class ConvenioDetailDTO extends ConvenioDTO{

  
    private String nombre;
    private Double descuento;

}