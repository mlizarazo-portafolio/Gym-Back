package co.edu.uniandes.dse.gym.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
public class SedeDetailDTO extends SedeDTO{
    private List<ReseniaDTO> resenias = new ArrayList<>();
    private List<PlanEntrenamientoDTO> planes = new ArrayList<>();
    private List<ServicioDTO> serviciosDisponibles = new ArrayList<>();
    private List<AtletaDTO> atletas = new ArrayList<>();
}