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

import co.edu.uniandes.dse.gym.entities.PlanEntrenamientoEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(PlanEntrenamientoService.class)

public class PlanEntrenamientoServiceTest {

    @Autowired
    private PlanEntrenamientoService planEntrenamientoService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<PlanEntrenamientoEntity> planList = new ArrayList<>();
 
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
                PlanEntrenamientoEntity planEntrenamientoEntity = factory.manufacturePojo(PlanEntrenamientoEntity.class);
                entityManager.persist(planEntrenamientoEntity);
                planList.add(planEntrenamientoEntity);
        }
    }


    @Test
    void testCreatePlan() throws EntityNotFoundException, IllegalOperationException {
        PlanEntrenamientoEntity newEntity = factory.manufacturePojo(PlanEntrenamientoEntity.class);
        //newEntity.setPlan(planList.get(0));
        //newEntity.setId("1-4028-9462-7");
        PlanEntrenamientoEntity result = planEntrenamientoService.crearPlan(newEntity);
        assertNotNull(result);
        PlanEntrenamientoEntity entity = entityManager.find(PlanEntrenamientoEntity.class, result.getId());
        
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        assertEquals(newEntity.getObjetivoBasico(), entity.getObjetivoBasico());
        assertEquals(newEntity.getDirrecion(), entity.getDirrecion());
        assertEquals(newEntity.getCoordenada(), entity.getCoordenada());
        assertEquals(newEntity.getDuracion(), entity.getDuracion());
        assertEquals(newEntity.getCosto(), entity.getCosto());
    }

    @Test
    void testGetPlan() throws EntityNotFoundException {
        PlanEntrenamientoEntity entity = planList.get(0);
        PlanEntrenamientoEntity resultEntity = planEntrenamientoService.getPlan(entity.getId());
        assertNotNull(resultEntity);
        
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
        assertEquals(entity.getObjetivoBasico(), resultEntity.getObjetivoBasico());
        assertEquals(entity.getDirrecion(), resultEntity.getDirrecion());
        assertEquals(entity.getCoordenada(), resultEntity.getCoordenada());
        assertEquals(entity.getDuracion(), resultEntity.getDuracion());
        assertEquals(entity.getCosto(), resultEntity.getCosto());
    }

    @Test
    void testGetInvalidPlan() {
        assertThrows(EntityNotFoundException.class,()->{
                planEntrenamientoService.getPlan(0L);
        });
    }


    

}
