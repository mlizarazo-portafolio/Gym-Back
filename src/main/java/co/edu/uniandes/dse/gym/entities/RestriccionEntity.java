package co.edu.uniandes.dse.gym.entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

@Getter
@Setter
@Entity
public class RestriccionEntity extends BaseEntity
{
    private Integer edad;
    private Integer condFisica;

    @PodamExclude
    @OneToOne
    private ActividadEntity actividad;
}