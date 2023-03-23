package co.edu.uniandes.dse.gym.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.uniandes.dse.gym.entities.ConvenioEntity;

@Repository
public interface ConvenioRepository extends JpaRepository<ConvenioEntity, Long> {
    List<ConvenioEntity> findByNombre(String nombre);
    
}
