package co.edu.uniandes.dse.gym.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.gym.entities.ConvenioEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(ConvenioService.class)

 // onvenio
public class ConvenioServiceTest {

    @Autowired
    private ConvenioService convenioService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<ConvenioEntity> convenioList = new ArrayList<>();

    @BeforeEach
    void setUp() {
            clearData();
            insertData();
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from ConvenioEntity");
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     */
    
     private void insertData() {
        for (int i = 0; i < 3; i++) {
                ConvenioEntity convenioEntity = factory.manufacturePojo(ConvenioEntity.class);
                entityManager.persist(convenioEntity);
                convenioList.add(convenioEntity);
        }
    }

    
    @Test
    void testCreateConvenio() throws EntityNotFoundException, IllegalOperationException {
        ConvenioEntity newEntity = factory.manufacturePojo(ConvenioEntity.class);
        ConvenioEntity result = convenioService.createConvenio(newEntity);
        assertNotNull(result);

        ConvenioEntity entity = entityManager.find(ConvenioEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getDescuento(), entity.getDescuento());

    }

    @Test
    void testCreateConvenioInvalidNombre() {
        assertThrows(IllegalOperationException.class, () -> {
                ConvenioEntity newEntity = factory.manufacturePojo(ConvenioEntity.class);
                newEntity.setNombre(null);
                convenioService.createConvenio(newEntity);
        });
    }



    @Test
    void testGetConvenios() {
        List<ConvenioEntity> list = convenioService.getConvenios();
        assertEquals(convenioList.size(), list.size());
        for (ConvenioEntity entity : list) {
            boolean found = false;
            for (ConvenioEntity storedEntity : convenioList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
                    }
            assertTrue(found);
        }
    }

    @Test
    void testGetConvenioById() throws EntityNotFoundException {
        ConvenioEntity entity = convenioList.get(0);
        ConvenioEntity resultEntity = convenioService.getConvenioById(entity.getId());

        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getDescuento(), resultEntity.getDescuento());
      
    }

    @Test
    void testGetInvalidConvenioById() {
        assertThrows(EntityNotFoundException.class,()->{
                convenioService.getConvenioById(0L);
        });
    }

    @Test
    void testUpdateConvenio() throws EntityNotFoundException, IllegalOperationException {
        ConvenioEntity entity = convenioList.get(0);
        ConvenioEntity pojoEntity = factory.manufacturePojo(ConvenioEntity.class);
        pojoEntity.setId(entity.getId());
        convenioService.updateConvenio(entity.getId(), pojoEntity);

        ConvenioEntity resp = entityManager.find(ConvenioEntity.class, entity.getId());

        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
        assertEquals(pojoEntity.getDescuento(), resp.getDescuento());

    }

    @Test
    void testUpdateConvenioInvalidId() {
        assertThrows(EntityNotFoundException.class, () -> {
                ConvenioEntity pojoEntity = factory.manufacturePojo(ConvenioEntity.class);
                pojoEntity.setId(0L);
                convenioService.updateConvenio(0L, pojoEntity);
        });
    }

    @Test
    void testDeleteConvenio() throws EntityNotFoundException, IllegalOperationException {
        ConvenioEntity entity = convenioList.get(1);
        convenioService.deleteConvenio(entity.getId());
        ConvenioEntity deleted = entityManager.find(ConvenioEntity.class, entity.getId());
        assertNull(deleted);
    }

    @Test
    void testDeleteInvalidConvenio() {
        assertThrows(EntityNotFoundException.class, ()->{
                convenioService.deleteConvenio(0L);
        });
    }
    
    




    
}
