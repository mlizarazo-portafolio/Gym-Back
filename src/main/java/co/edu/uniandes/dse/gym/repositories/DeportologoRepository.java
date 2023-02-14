package co.edu.uniandes.dse.gym.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.gym.entities.DeportologoEntity;


@Repository
public interface DeportologoRepository extends JpaRepository<DeportologoEntity, Long> {

    List<DeportologoEntity> findByNombre(String nombre);
    
}