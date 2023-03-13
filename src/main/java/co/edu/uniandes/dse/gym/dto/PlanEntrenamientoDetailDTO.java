package co.edu.uniandes.dse.gym.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter


public class PlanEntrenamientoDetailDTO extends PlanEntrenamientoDTO{

    private List<SedeDTO> sedes = new ArrayList<>();

    private List<ConvenioDTO> convenios = new ArrayList<>();

    private List<AtletaDTO> atletasInscritos = new ArrayList<>();
}