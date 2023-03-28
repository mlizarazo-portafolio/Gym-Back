package co.edu.uniandes.dse.gym.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
        PlanEntrenamientoEntity result = planEntrenamientoService.crearPlan(newEntity);
        assertNotNull(result);
        PlanEntrenamientoEntity entity = entityManager.find(PlanEntrenamientoEntity.class, result.getId());
        
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getObjetivoBasico(), entity.getObjetivoBasico());
        assertEquals(newEntity.getDireccion(), entity.getDireccion());
        assertEquals(newEntity.getDuracion(), entity.getDuracion());
        assertEquals(newEntity.getCosto(), entity.getCosto());
    }

    @Test
    void testGetPlan() throws EntityNotFoundException {
        PlanEntrenamientoEntity entity = planList.get(0);
        PlanEntrenamientoEntity resultEntity = planEntrenamientoService.getPlanById(entity.getId());
        assertNotNull(resultEntity);

        
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getObjetivoBasico(), resultEntity.getObjetivoBasico());
        assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
        assertEquals(entity.getDireccion(), resultEntity.getDireccion());
        assertEquals(entity.getDuracion(), resultEntity.getDuracion());
        assertEquals(entity.getCosto(), resultEntity.getCosto());
    }


    @Test
    void testGetPlanById() throws EntityNotFoundException {
        PlanEntrenamientoEntity entity = planList.get(0);
        PlanEntrenamientoEntity resultEntity = planEntrenamientoService.getPlanById(entity.getId());
        assertNotNull(resultEntity);

        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getObjetivoBasico(), resultEntity.getObjetivoBasico());
        assertEquals(entity.getDireccion(), resultEntity.getDireccion());
        assertEquals(entity.getDuracion(), resultEntity.getDuracion());
        assertEquals(entity.getCosto(), resultEntity.getCosto());
    }

    @Test
    void testGetInvalidPlanById() {
        assertThrows(EntityNotFoundException.class,()->{
                planEntrenamientoService.getPlanById(0L);
        });
    }


    @Test
    void testUpdatePlan() throws EntityNotFoundException, IllegalOperationException {
        PlanEntrenamientoEntity entity = planList.get(0);
        PlanEntrenamientoEntity pojoEntity = factory.manufacturePojo(PlanEntrenamientoEntity.class);
        pojoEntity.setId(entity.getId());
        planEntrenamientoService.updatePlan(entity.getId(), pojoEntity);

        PlanEntrenamientoEntity resp = entityManager.find(PlanEntrenamientoEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
        assertEquals(pojoEntity.getObjetivoBasico(), resp.getObjetivoBasico());
        assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        assertEquals(pojoEntity.getDireccion(), resp.getDireccion());
        assertEquals(pojoEntity.getDuracion(), resp.getDuracion());
        assertEquals(pojoEntity.getCosto(), resp.getCosto());
    }

    @Test
    void testUpdatePlanInvalidId() {
        assertThrows(EntityNotFoundException.class, () -> {
                PlanEntrenamientoEntity pojoEntity = factory.manufacturePojo(PlanEntrenamientoEntity.class);
                pojoEntity.setId(0L);
                planEntrenamientoService.updatePlan(0L, pojoEntity);
        });
    }

    @Test
    void testDeletePlan() throws EntityNotFoundException, IllegalOperationException {
        PlanEntrenamientoEntity entity = planList.get(1);
        planEntrenamientoService.deletePlan(entity.getId());
        PlanEntrenamientoEntity deleted = entityManager.find(PlanEntrenamientoEntity.class, entity.getId());
        assertNull(deleted);
    }

    @Test
    void testDeleteInvalidSede() {
        assertThrows(EntityNotFoundException.class, ()->{
                planEntrenamientoService.deletePlan(0L);
        });
    }

}
