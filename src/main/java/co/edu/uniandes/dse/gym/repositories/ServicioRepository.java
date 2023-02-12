package co.edu.uniandes.dse.gym.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.uniandes.dse.gym.entities.ServicioEntity;

@Repository
public interface ServicioRepository extends JpaRepository<ServicioEntity, Long>{

    List<ServicioEntity> findByNombre(String nombre);
    
}