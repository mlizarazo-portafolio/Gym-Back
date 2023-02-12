package co.edu.uniandes.dse.gym.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.ArrayList;
// import java.util.ArrayList;
// import java.sql.Array; 

@Getter
@Setter
@Entity

public class SedeEntity extends BaseEntity {

    //private PlanEntrenamientoEntity planEntrenamiento;
    //@OneToMany(mappedBy = "sede")

    private String nombre;
    private String direccion;
    private String telefono;

    @OneToMany(mappedBy = "sede")
    private List<ReseniaEntity> resenias = new ArrayList<>();



}