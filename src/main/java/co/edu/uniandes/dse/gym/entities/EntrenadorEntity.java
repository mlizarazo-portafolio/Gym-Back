package co.edu.uniandes.dse.gym.entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

@Setter
@Getter
@Entity
public class EntrenadorEntity extends BaseEntity{

    private String nombre;
    private String foto;
    private String trayectoria;

    @PodamExclude
    @OneToOne(mappedBy = "entrenador")
    private ActividadEntity actividad;
    
}
