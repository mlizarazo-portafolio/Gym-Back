package co.edu.uniandes.dse.gym.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.gym.entities.AtletaEntity;

@Repository
public interface AtletaRepository extends JpaRepository<AtletaEntity, Long> {

    List<AtletaEntity> findByNombre(String nombre);
    
}