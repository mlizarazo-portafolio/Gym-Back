import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class PlanEntrenamientoDTO{

    private String objetivoBasico;
    private String nombre;
    private String descripcion;
    private String dirrecion;
    private String coordenada;
    private Integer duracion;
    private Integer costo;

}