package co.edu.uniandes.dse.gym.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
public class ActividadDetailDTO extends ActividadDTO{
    private RestriccionDTO restriccion;
    private List<SedeDTO> sedes = new ArrayList<>();   
    private List<AtletaDTO> atletas = new ArrayList<>();   
}
