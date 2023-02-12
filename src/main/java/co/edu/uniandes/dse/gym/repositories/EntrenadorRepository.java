package co.edu.uniandes.dse.gym.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.uniandes.dse.gym.entities.EntrenadorEntity;

@Repository
public interface EntrenadorRepository extends JpaRepository<EntrenadorEntity, Long> {

    List<EntrenadorEntity> findByNombre(String nombre);
    
}
