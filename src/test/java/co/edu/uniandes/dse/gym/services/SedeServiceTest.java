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

import co.edu.uniandes.dse.gym.entities.SedeEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(SedeService.class)
public class SedeServiceTest {
    @Autowired
    private SedeService sedeService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();
    
    private List<SedeEntity> sedeList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from SedeEntity");
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            SedeEntity sedeEntity = factory.manufacturePojo(SedeEntity.class);
            entityManager.persist(sedeEntity);
            sedeList.add(sedeEntity);
        }
    }

    @Test
    void testCreateSede() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);
        SedeEntity result = sedeService.createSede(newEntity);
        assertNotNull(result);

        SedeEntity entity = entityManager.find(SedeEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getDireccion(), entity.getDireccion());
        assertEquals(newEntity.getTelefono(), entity.getTelefono());
    }

    @Test
    void testGetSedes() {
        List<SedeEntity> list = sedeService.getSedes();
        assertEquals(sedeList.size(), list.size());
        for (SedeEntity entity : list) {
            boolean found = false;
            for (SedeEntity storedEntity : sedeList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
                    }
            assertTrue(found);
        }
    }

    @Test
    void testGetSedeById() throws EntityNotFoundException {
        SedeEntity entity = sedeList.get(0);
        SedeEntity resultEntity = sedeService.getSedeById(entity.getId());

        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getDireccion(), resultEntity.getDireccion());
        assertEquals(entity.getTelefono(), resultEntity.getTelefono());
    }

    @Test
    void testUpdateSede() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity entity = sedeList.get(0);
        SedeEntity pojoEntity = factory.manufacturePojo(SedeEntity.class);
        pojoEntity.setId(entity.getId());
        sedeService.updateSede(entity.getId(), pojoEntity);

        SedeEntity resp = entityManager.find(SedeEntity.class, entity.getId());

        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
        assertEquals(pojoEntity.getDireccion(), resp.getDireccion());
        assertEquals(pojoEntity.getTelefono(), resp.getTelefono());
    }

    @Test
    void testDeleteSede() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity entity = sedeList.get(1);
        sedeService.deleteSede(entity.getId());
        SedeEntity deleted = entityManager.find(SedeEntity.class, entity.getId());
        assertNull(deleted);
    }

}