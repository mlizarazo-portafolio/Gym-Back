package co.edu.uniandes.dse.gym.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.gym.entities.ActividadEntity;

@Repository
public interface ActividadRepository extends JpaRepository<ActividadEntity, Long> {

    List<ActividadEntity> findByNombre(String nombre);
    
}