package co.edu.uniandes.dse.gym.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.entities.RestriccionEntity;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(RestriccionService.class)
class RestriccionServiceTest {

    @Autowired
    private RestriccionService restriccionService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<RestriccionEntity> restriccionList = new ArrayList<>();

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
    private void clearData() 
    {
            entityManager.getEntityManager().createQuery("delete from RestriccionEntity");
    
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     */
    private void insertData()
    {
        for(int i = 0; i<3; i++)
        {
            RestriccionEntity restriccionEntity = factory.manufacturePojo(RestriccionEntity.class);
            entityManager.persist(restriccionEntity);
            restriccionList.add(restriccionEntity);
        }
    }

    @Test
    void testCreateRestriccion()throws EntityNotFoundException, IllegalOperationException
    {
        RestriccionEntity newEntity = factory.manufacturePojo(RestriccionEntity.class);
        RestriccionEntity result = restriccionService.createRestriccion(newEntity);
        assertNotNull(result);
        
        RestriccionEntity entity = entityManager.find(RestriccionEntity.class, result.getId());

        assertEquals(newEntity.getActividad(), entity.getActividad());
        assertEquals(newEntity.getCondFisica(), entity.getCondFisica());
        assertEquals(newEntity.getEdad(), entity.getEdad());
        assertEquals(newEntity.getId(), entity.getId());
    }

    @Test
    void testCreateRestriccionWithNotValidEdad()
    {
        assertThrows(IllegalOperationException.class, () -> {
            RestriccionEntity newEntity = factory.manufacturePojo(RestriccionEntity.class);
            newEntity.setEdad(-20);
            restriccionService.createRestriccion(newEntity);
    });
    }

    @Test
    void testGetRestricciones()
    {
        List<RestriccionEntity> list = restriccionService.getRestricciones();
        assertEquals(restriccionList.size(), list.size());
        for(RestriccionEntity entity: list)
        {
            boolean found = false;
            for(RestriccionEntity storedEntity: restriccionList)
            {
                if(entity.getId().equals(storedEntity.getId()))
                {
                    found = true;
                }
            }

            assertTrue(found);

        }
    }

    @Test
    void testGetRestriccion() throws EntityNotFoundException
    {
        RestriccionEntity entity = restriccionList.get(0);
        RestriccionEntity resultEntity = restriccionService.getRestriccion(entity.getId());
        assertNotNull(resultEntity);

        assertEquals(entity.getActividad(), resultEntity.getActividad());
        assertEquals(entity.getCondFisica(), resultEntity.getCondFisica());
        assertEquals(entity.getEdad(),resultEntity.getEdad());
        assertEquals(entity.getId(), resultEntity.getId());

    }

    @Test
    void testGetInvalidRestriccion()
    {
        assertThrows(EntityNotFoundException.class,()->{
            restriccionService.getRestriccion(0L);
    });
    }

    @Test
    void testUpdateRestriccion() throws EntityNotFoundException
    {
        RestriccionEntity entity = restriccionList.get(0);
        RestriccionEntity pojoEntity = factory.manufacturePojo(RestriccionEntity.class);

        pojoEntity.setId(entity.getId());
        restriccionService.updateRestriccion(entity.getId(), pojoEntity);

        RestriccionEntity resp = entityManager.find(RestriccionEntity.class, entity.getId());

        assertEquals(pojoEntity.getActividad(), resp.getActividad());
        assertEquals(pojoEntity.getCondFisica(), resp.getCondFisica());
        assertEquals(pojoEntity.getEdad(), resp.getEdad());
        assertEquals(pojoEntity.getId(), resp.getId());

    }

    @Test
    void testUpdateRestriccionInvalid()
    {
        assertThrows(EntityNotFoundException.class, () -> {
            RestriccionEntity pojoEntity = factory.manufacturePojo(RestriccionEntity.class);
            pojoEntity.setId(0L);
            restriccionService.updateRestriccion(0L, pojoEntity);
    });
    }

    @Test
    void testDeleteRestriccion() throws EntityNotFoundException
    {
        RestriccionEntity entity = restriccionList.get(1);
        restriccionService.deleteRestriccion(entity.getId());
        RestriccionEntity deleted = entityManager.find(RestriccionEntity.class, entity.getId());
        assertNull(deleted);

    }

    @Test
    void testDeleteInvalidRestriccion()
    {
        assertThrows(EntityNotFoundException.class, ()->{
            restriccionService.deleteRestriccion(0L);
    });
    }



    
}
