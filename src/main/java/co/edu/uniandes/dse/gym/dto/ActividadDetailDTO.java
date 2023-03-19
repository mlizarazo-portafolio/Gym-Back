package co.edu.uniandes.dse.gym.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
public class ActividadDetailDTO extends ActividadDTO{
    private List<SedeDTO> sedes = new ArrayList<>();   
}
