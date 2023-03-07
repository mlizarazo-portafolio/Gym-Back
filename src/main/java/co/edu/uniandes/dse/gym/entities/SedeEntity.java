package co.edu.uniandes.dse.gym.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

import uk.co.jemos.podam.common.PodamExclude;

import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
@Entity

public class SedeEntity extends BaseEntity {

    private String nombre;
    private String direccion;
    private String telefono;

    @PodamExclude
    @OneToMany(mappedBy = "sede")
    private List<ReseniaEntity> resenias = new ArrayList<>();

    @PodamExclude
    @ManyToMany(mappedBy = "sedes")
    private List<PlanEntrenamientoEntity> planes = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "sede")
    private List<ServicioEntity> serviciosDisponibles = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "sede")
    private List<AtletaEntity> atletas = new ArrayList<>();
    
}