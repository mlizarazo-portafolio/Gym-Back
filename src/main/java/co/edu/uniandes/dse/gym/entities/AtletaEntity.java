package co.edu.uniandes.dse.gym.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import co.edu.uniandes.dse.gym.podam.DateStrategy;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@Entity

public class AtletaEntity extends BaseEntity {

    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fechaNacimiento;

    private String nombre;
    private String login;
    private String tipoSangre;
    private String direccion;
    private Integer altura;
    private Integer peso;

    @PodamExclude

    @ManyToOne
    private SedeEntity sede;
    @PodamExclude
    @ManyToOne
    private PlanEntrenamientoEntity plan;
    @PodamExclude
    @ManyToMany(mappedBy = "atletasInscritos")
    private List<ActividadEntity> actividadesInscritas = new ArrayList<>();
    @PodamExclude
    @ManyToOne
    private DeportologoEntity deportologo;

}