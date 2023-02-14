package co.edu.uniandes.dse.gym.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Getter;

import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;

@Setter
@Getter
@Entity
public class DeportologoEntity extends BaseEntity {
    private String nombre;
    private String login;
    private String experiencia;
    private String foto;
    

    @OneToOne
    private SedeEntity sede;
    @OneToMany(mappedBy = "deportologo")
    private List<AtletaEntity> valoracionAtletas = new ArrayList<>();
}
