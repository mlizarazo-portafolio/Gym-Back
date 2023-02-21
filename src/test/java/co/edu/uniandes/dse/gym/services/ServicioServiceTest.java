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
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void testCreateServicioInvalidServicio() {
        assertThrows(IllegalOperationException.class, () -> {
                ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
                newEntity.setSede(sedeList.get(0));
                newEntity.setServicio(null);
                servicioService.createServicio(newEntity);
        });
    }

    @Test
    void testCreateServicioInvalidDisponible() {
        assertThrows(IllegalOperationException.class, () -> {
                ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
                newEntity.setSede(sedeList.get(0));
                newEntity.setDisponible(null);
                servicioService.createServicio(newEntity);
        });
    }

    @Test
    void testCreateServicioInvalidSede() {
        assertThrows(IllegalOperationException.class, () -> {
                ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
                SedeEntity sedeEntity = new SedeEntity();
                sedeEntity.setId(0L);
                servicioService.createServicio(newEntity);
        });
    }

    @Test
    void testCreateServicioNullSede() {
        assertThrows(IllegalOperationException.class, () -> {
                ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
                newEntity.setSede(null);
                servicioService.createServicio(newEntity);
        });
    }

    // @Test
    // void testCreateServicioEmptySede() {
    //     assertThrows(IllegalOperationException.class, () -> {
    //             ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
    //             SedeEntity sedeEntity = new SedeEntity();
    //             newEntity.setSede(sedeEntity);
    //             servicioService.createServicio(newEntity);
    //     });
    // }

    @Test
    void testGetServicios() {
        List<ServicioEntity> list = servicioService.getServicios();
        assertEquals(servicioList.size(), list.size());
        for (ServicioEntity entity : list) {
            boolean found = false;
            for (ServicioEntity storedEntity : servicioList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
                    }
            assertTrue(found);
        }
    }

    @Test
    void testGetServicioById() throws EntityNotFoundException {
        ServicioEntity entity = servicioList.get(0);
        ServicioEntity resultEntity = servicioService.getServicioById(entity.getId());

        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getServicio(), resultEntity.getServicio());
        assertEquals(entity.getDisponible(), resultEntity.getDisponible());
    }

    @Test
    void testGetInvalidServicioById() {
        assertThrows(EntityNotFoundException.class,()->{
                servicioService.getServicioById(0L);
        });
    }

    @Test
    void testUpdateServicio() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity entity = servicioList.get(0);
        ServicioEntity pojoEntity = factory.manufacturePojo(ServicioEntity.class);
        pojoEntity.setId(entity.getId());
        servicioService.updateServicio(entity.getId(), pojoEntity);

        ServicioEntity resp = entityManager.find(ServicioEntity.class, entity.getId());

        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getServicio(), resp.getServicio());
        assertEquals(pojoEntity.getDisponible(), resp.getDisponible());
    }

    @Test
    void testUpdateServicioInvalidId() {
        assertThrows(EntityNotFoundException.class, () -> {
                ServicioEntity pojoEntity = factory.manufacturePojo(ServicioEntity.class);
                pojoEntity.setId(0L);
                servicioService.updateServicio(0L, pojoEntity);
        });
    }

    @Test
    void testDeleteServicio() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity entity = servicioList.get(1);
        servicioService.deleteServicio(entity.getId());
        ServicioEntity deleted = entityManager.find(ServicioEntity.class, entity.getId());
        assertNull(deleted);
    }

    @Test
    void testDeleteInvalidServicio() {
        assertThrows(EntityNotFoundException.class, ()->{
                servicioService.deleteServicio(0L);
        });
    }

}