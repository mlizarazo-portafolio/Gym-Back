package co.edu.uniandes.dse.gym.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
@Entity

public class SedeEntity extends BaseEntity {

    private String nombre;
    private String direccion;
    private String telefono;

    @OneToMany(mappedBy = "sede")
    private List<ReseniaEntity> resenias = new ArrayList<>();

    @ManyToMany(mappedBy = "sedes")
    private List<PlanEntrenamientoEntity> planes = new ArrayList<>();

}