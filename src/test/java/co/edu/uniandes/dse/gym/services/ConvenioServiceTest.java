package co.edu.uniandes.dse.gym.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        entityManager.getEntityManager().createQuery("delete from PlanEntrenamientoEntity");
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
        //newEntity.setPlan(planList.get(0));
        //newEntity.setId("1-4028-9462-7");
        ConvenioEntity result = convenioService.crearConvenio(newEntity);
        assertNotNull(result);
        ConvenioEntity entity = entityManager.find(ConvenioEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getDescuento(), entity.getDescuento());
    }

    @Test
    void testGetConvenio() throws EntityNotFoundException {
        ConvenioEntity entity = convenioList.get(0);
        ConvenioEntity resultEntity = convenioService.getConvenio(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getDescuento(), resultEntity.getDescuento());

}


    
}
