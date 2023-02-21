package co.edu.uniandes.dse.gym.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.gym.entities.ServicioEntity;
import co.edu.uniandes.dse.gym.entities.SedeEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(ServicioService.class)
public class ServicioServiceTest {
    @Autowired
    private ServicioService servicioService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();
    
    private List<ServicioEntity> servicioList = new ArrayList<>();
    private List<SedeEntity> sedeList = new ArrayList<>();
    
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from ServicioEntity");
        entityManager.getEntityManager().createQuery("delete from SedeEntity");
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            SedeEntity sedeEntity = factory.manufacturePojo(SedeEntity.class);
            entityManager.persist(sedeEntity);
            sedeList.add(sedeEntity);
        }

        for (int i = 0; i < 3; i++) {
            ServicioEntity servicioEntity = factory.manufacturePojo(ServicioEntity.class);
            servicioEntity.setSede(sedeList.get(0));
            entityManager.persist(servicioEntity);
            servicioList.add(servicioEntity);
        }
    }

    @Test
    void testCreateServicio() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
        newEntity.setSede(sedeList.get(0));
        ServicioEntity result = servicioService.createServicio(newEntity);
        assertNotNull(result);

        ServicioEntity entity = entityManager.find(ServicioEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getServicio(), entity.getServicio());
        assertEquals(newEntity.getDisponible(), entity.getDisponible());
    }

}