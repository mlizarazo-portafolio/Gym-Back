package co.edu.uniandes.dse.gym.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.gym.entities.RestriccionEntity;

@Repository
public interface RestriccionRepository extends JpaRepository<RestriccionEntity, Long> {

    List<RestriccionEntity> findBycondFisica(String condFisica);
    
}
