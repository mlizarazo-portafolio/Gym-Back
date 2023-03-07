package co.edu.uniandes.dse.gym.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DeportologoDetailDTO extends DeportologoDTO {

    private List<AtletaDTO> valoracionAtletas = new ArrayList<>();

}
