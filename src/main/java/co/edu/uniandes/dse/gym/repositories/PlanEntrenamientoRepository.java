package co.edu.uniandes.dse.gym.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.uniandes.dse.gym.entities.PlanEntrenamientoEntity;

@Repository
public interface PlanEntrenamientoRepository extends JpaRepository<PlanEntrenamientoEntity, Long> {
    List<PlanEntrenamientoEntity> findByNombre(String nombre);
    
}
