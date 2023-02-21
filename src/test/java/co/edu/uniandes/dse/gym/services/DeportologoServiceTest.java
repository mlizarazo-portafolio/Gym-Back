package co.edu.uniandes.dse.gym.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.gym.entities.DeportologoEntity;
import co.edu.uniandes.dse.gym.entities.SedeEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.services.DeportologoService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(DeportologoService.class)
class DeportologoServiceTest {

    @Autowired
    private DeportologoService deportologoService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<DeportologoEntity> deportologoList = new ArrayList<>();
    private List<SedeEntity> sedeList = new ArrayList<>();

    /**
     * Configuración inicial de la prueba.
     */
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from DeportologoEntity");
        entityManager.getEntityManager().createQuery("delete from SedeEntity");
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            SedeEntity sedeEntity = factory.manufacturePojo(SedeEntity.class);
            entityManager.persist(sedeEntity);
            sedeList.add(sedeEntity);
        }

        for (int i = 0; i < 3; i++) {
            DeportologoEntity deportologoEntity = factory.manufacturePojo(DeportologoEntity.class);
            deportologoEntity.setSede(sedeList.get(0));
            entityManager.persist(deportologoEntity);
            deportologoList.add(deportologoEntity);
        }

    }
    /*
     * @Test
     * void testCreateBook() throws EntityNotFoundException,
     * IllegalOperationException {
     * DeportologoEntity newEntity =
     * factory.manufacturePojo(DeportologoEntity.class);
     * newEntity.setSede(sedeList.get(0));
     * DeportologoEntity result = deportologoService.createDeportologo(newEntity);
     * assertNotNull(result);
     * DeportologoEntity entity = entityManager.find(DeportologoEntity.class,
     * result.getId());
     * assertEquals(newEntity.getId(), entity.getId());
     * assertEquals(newEntity.getNombre(), entity.getNombre());
     * assertEquals(newEntity.getFoto(), entity.getFoto());
     * assertEquals(newEntity.getExperiencia(), entity.getExperiencia());
     * 
     * }
     */

    @Test
    void testGetDeportologo() throws EntityNotFoundException {
        DeportologoEntity entity = deportologoList.get(0);
        DeportologoEntity resultEntity = deportologoService.getDeportologo(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getFoto(), resultEntity.getFoto());
        assertEquals(entity.getExperiencia(), resultEntity.getExperiencia());
    }

    @Test
    void testGetDeportologos() {
        List<DeportologoEntity> list = deportologoService.getDeportologos();
        assertEquals(deportologoList.size(), list.size());
        for (DeportologoEntity entity : list) {
            boolean found = false;
            for (DeportologoEntity storedEntity : deportologoList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }
    /*
     * @Test
     * void testUpdateDeportologo() throws EntityNotFoundException,
     * IllegalOperationException {
     * DeportologoEntity entity = deportologoList.get(0);
     * DeportologoEntity pojoEntity =
     * factory.manufacturePojo(DeportologoEntity.class);
     * pojoEntity.setId(entity.getId());
     * pojoEntity.setSede(sedeList.get(0));
     * deportologoService.updateDeportologo(pojoEntity.getId(), pojoEntity);
     * DeportologoEntity resp = entityManager.find(DeportologoEntity.class,
     * entity.getId());
     * assertEquals(pojoEntity.getId(), resp.getId());
     * assertEquals(pojoEntity.getNombre(), resp.getNombre());
     * assertEquals(pojoEntity.getFoto(), resp.getFoto());
     * assertEquals(pojoEntity.getExperiencia(), resp.getExperiencia());
     * }
     */

    @Test

    void testDeleteDeportologo() throws EntityNotFoundException {
        DeportologoEntity entity = deportologoList.get(0);
        deportologoService.deleteDeportologo(entity.getId());
        DeportologoEntity deleted = entityManager.find(DeportologoEntity.class, entity.getId());
        assertNull(deleted);
    }

}
