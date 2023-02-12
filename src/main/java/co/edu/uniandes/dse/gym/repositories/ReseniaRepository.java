package co.edu.uniandes.dse.gym.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.uniandes.dse.gym.entities.ReseniaEntity;

@Repository
public interface ReseniaRepository extends JpaRepository<ReseniaEntity, Long>{

    List<ReseniaEntity> findByUsuario(String usuario);
    
}
