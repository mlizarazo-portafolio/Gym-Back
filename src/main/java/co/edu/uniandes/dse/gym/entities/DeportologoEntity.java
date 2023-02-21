package co.edu.uniandes.dse.gym.entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.transaction.Transactional;

import lombok.Getter;

import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

import java.util.List;
import java.util.ArrayList;

@Setter
@Getter
@Entity

public class DeportologoEntity extends BaseEntity {
    private String nombre;
    private String login;
    private String experiencia;
    private String foto;

    @PodamExclude

    @OneToOne
    private SedeEntity sede;
    @OneToMany(mappedBy = "deportologo")
    private List<AtletaEntity> valoracionAtletas = new ArrayList<>();

}