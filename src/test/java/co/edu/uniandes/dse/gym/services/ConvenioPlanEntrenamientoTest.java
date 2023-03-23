package co.edu.uniandes.dse.gym.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

import co.edu.uniandes.dse.gym.entities.ConvenioEntity;
import co.edu.uniandes.dse.gym.entities.PlanEntrenamientoEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(ConvenioPlanEntrenamientoService.class)
public class ConvenioPlanEntrenamientoTest {

    @Autowired
    private ConvenioPlanEntrenamientoService convenioPlanEntrenamientoService;

    @Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

    private PlanEntrenamientoEntity plan = new PlanEntrenamientoEntity();
	private List<ConvenioEntity> convenioList = new ArrayList<>();

    @BeforeEach
	void setUp() {
		clearData();
		insertData();
	}
    private void clearData() {
		entityManager.getEntityManager().createQuery("delete from ConvenioEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from PlanEntrenamientoEntity").executeUpdate();
	}

    
	private void insertData() {

		plan = factory.manufacturePojo(PlanEntrenamientoEntity.class);
		entityManager.persist(plan);

		for (int i = 0; i < 3; i++) {
			ConvenioEntity entity = factory.manufacturePojo(ConvenioEntity.class);
			entityManager.persist(entity);
			entity.getPlan().add(plan);
			convenioList.add(entity);
			plan.getConvenios().add(entity);	
		}
	}

    @Test
	void testAddConvenio() throws EntityNotFoundException, IllegalOperationException {
		PlanEntrenamientoEntity newPlan = factory.manufacturePojo(PlanEntrenamientoEntity.class);
		entityManager.persist(newPlan);
		
		ConvenioEntity convenio = factory.manufacturePojo(ConvenioEntity.class);
		entityManager.persist(convenio);
		
		convenioPlanEntrenamientoService.addConvenio(newPlan.getId(), convenio.getId());

        ConvenioEntity lastConvenio = convenioPlanEntrenamientoService.getConvenio(newPlan.getId(), convenio.getId());

        assertEquals(convenio.getId(), lastConvenio.getId());
		assertEquals(convenio.getNombre(), lastConvenio.getNombre());
        assertEquals(convenio.getDescuento(), lastConvenio.getDescuento());
    }


    @Test
	void testAddInvalidConvenio() {
		assertThrows(EntityNotFoundException.class, ()->{
			PlanEntrenamientoEntity newPlan = factory.manufacturePojo(PlanEntrenamientoEntity.class);
			entityManager.persist(newPlan);
			convenioPlanEntrenamientoService.addConvenio(newPlan.getId(), 0L);
		});
    }

    @Test
	void testAddConvenioInvalidPlanEntrenamient() throws EntityNotFoundException, IllegalOperationException {
		assertThrows(EntityNotFoundException.class, ()->{
			ConvenioEntity convenio = factory.manufacturePojo(ConvenioEntity.class);
			entityManager.persist(convenio);
			convenioPlanEntrenamientoService.addConvenio(0L, convenio.getId());
		});
	}



    @Test
	void testRemoveConvenio() throws EntityNotFoundException {
		for (ConvenioEntity convenio : convenioList) {
			convenioPlanEntrenamientoService.removeConvenio(plan.getId(), convenio.getId());
		}
		assertTrue(convenioPlanEntrenamientoService.getConvenios(plan.getId()).isEmpty());
	}

	@Test
	void testRemoveInvalidConvenio(){
		assertThrows(EntityNotFoundException.class, ()->{
			convenioPlanEntrenamientoService.removeConvenio(plan.getId(), 0L);
		});
	}
	
	@Test
	void testRemoveConvenioInvalidPlanEntrenamiento(){
		assertThrows(EntityNotFoundException.class, ()->{
			convenioPlanEntrenamientoService.removeConvenio(0L, convenioList.get(0).getId());
		});
	}
}
