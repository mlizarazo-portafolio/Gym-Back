import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ConvenioDTO{

    private PlanEntrenamientoEntity plan;
    private String nombre;
    private Double descuento;

}