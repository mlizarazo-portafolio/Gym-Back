package co.edu.uniandes.dse.gym.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.uniandes.dse.gym.entities.SedeEntity;

@Repository
public interface SedeRepository extends JpaRepository<SedeEntity, Long>{

    List<SedeEntity> findByNombre(String nombre);
    
}