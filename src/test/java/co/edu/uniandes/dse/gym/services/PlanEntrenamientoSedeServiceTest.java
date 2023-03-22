package co.edu.uniandes.dse.gym.services;

import static org.junit.jupiter.api.Assertions.*;

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

import co.edu.uniandes.dse.gym.entities.SedeEntity;
import co.edu.uniandes.dse.gym.entities.PlanEntrenamientoEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.services.PlanEntrenamientoSedeService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(PlanEntrenamientoSedeService.class)
class PlanEntrenamientoSedeServiceTest {
	
	@Autowired
	private PlanEntrenamientoSedeService planEntrenamientoSedeService;
	
	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private PlanEntrenamientoEntity planEntrenamiento = new PlanEntrenamientoEntity();
	private List<SedeEntity> sedeList = new ArrayList<>();

	
	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}
	
	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from SedeEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from PlanEntrenamientoEntity").executeUpdate();
	}

	private void insertData() {

		planEntrenamiento = factory.manufacturePojo(PlanEntrenamientoEntity.class);
		entityManager.persist(planEntrenamiento);

		for (int i = 0; i < 3; i++) {
			SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
			entityManager.persist(entity);
			entity.getPlanes().add(planEntrenamiento);
			sedeList.add(entity);
			planEntrenamiento.getSedes().add(entity);	
		}
	}

	@Test
	void testAddSede() throws EntityNotFoundException, IllegalOperationException {
		PlanEntrenamientoEntity newPlanEntrenamiento = factory.manufacturePojo(PlanEntrenamientoEntity.class);
		entityManager.persist(newPlanEntrenamiento);
		
		SedeEntity sede = factory.manufacturePojo(SedeEntity.class);
		entityManager.persist(sede);
		
		planEntrenamientoSedeService.addSede(newPlanEntrenamiento.getId(), sede.getId());
		
		SedeEntity lastSede = planEntrenamientoSedeService.getSede(newPlanEntrenamiento.getId(), sede.getId());
		assertEquals(sede.getId(), lastSede.getId());
		assertEquals(sede.getNombre(), lastSede.getNombre());
		assertEquals(sede.getDireccion(), lastSede.getDireccion());
        assertEquals(sede.getTelefono(), lastSede.getTelefono());
	}
	
	@Test
	void testAddInvalidSede() {
		assertThrows(EntityNotFoundException.class, ()->{
			PlanEntrenamientoEntity newPlanEntrenamiento = factory.manufacturePojo(PlanEntrenamientoEntity.class);
			entityManager.persist(newPlanEntrenamiento);
			planEntrenamientoSedeService.addSede(newPlanEntrenamiento.getId(), 0L);
		});
	}

	@Test
	void testAddSedeInvalidPlanEntrenamiento() throws EntityNotFoundException, IllegalOperationException {
		assertThrows(EntityNotFoundException.class, ()->{
			SedeEntity sede = factory.manufacturePojo(SedeEntity.class);
			entityManager.persist(sede);
			planEntrenamientoSedeService.addSede(0L, sede.getId());
		});
	}

	@Test
	void testGetSedes() throws EntityNotFoundException {
		List<SedeEntity> sedeEntities = planEntrenamientoSedeService.getSedes(planEntrenamiento.getId());

		assertEquals(sedeList.size(), sedeEntities.size());

		for (int i = 0; i < sedeList.size(); i++) {
			assertTrue(sedeEntities.contains(sedeList.get(0)));
		}
	}

	void testGetSedesInvalidPlanEntrenamiento(){
		assertThrows(EntityNotFoundException.class, ()->{
			planEntrenamientoSedeService.getSedes(0L);
		});
	}

	@Test
	void testGetSede() throws EntityNotFoundException, IllegalOperationException {
		SedeEntity sedeEntity = sedeList.get(0);
		SedeEntity sede = planEntrenamientoSedeService.getSede(planEntrenamiento.getId(), sedeEntity.getId());
		assertNotNull(sede);

		assertEquals(sede.getId(), sedeEntity.getId());
		assertEquals(sede.getNombre(), sedeEntity.getNombre());
		assertEquals(sede.getDireccion(), sedeEntity.getDireccion());
        assertEquals(sede.getTelefono(), sedeEntity.getTelefono());
	}

	@Test
	void testGetInvalidSede()  {
		assertThrows(EntityNotFoundException.class, ()->{
			planEntrenamientoSedeService.getSede(planEntrenamiento.getId(), 0L);
		});
	}

	@Test
	void testGetSedeInvalidPlanEntrenamiento() {
		assertThrows(EntityNotFoundException.class, ()->{
			SedeEntity sedeEntity = sedeList.get(0);
			planEntrenamientoSedeService.getSede(0L, sedeEntity.getId());
		});
	}
	
	@Test
	void testGetNotAssociatedSede() {
		assertThrows(IllegalOperationException.class, ()->{
			PlanEntrenamientoEntity newPlanEntrenamiento = factory.manufacturePojo(PlanEntrenamientoEntity.class);
			entityManager.persist(newPlanEntrenamiento);
			SedeEntity sede = factory.manufacturePojo(SedeEntity.class);
			entityManager.persist(sede);
			planEntrenamientoSedeService.getSede(newPlanEntrenamiento.getId(), sede.getId());
		});
	}

	@Test
	void testReplaceSedes() throws EntityNotFoundException {
		List<SedeEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
			entityManager.persist(entity);
			planEntrenamiento.getSedes().add(entity);
			nuevaLista.add(entity);
		}
		planEntrenamientoSedeService.replaceSedes(planEntrenamiento.getId(), nuevaLista);

		List<SedeEntity> sedeEntities = planEntrenamientoSedeService.getSedes(planEntrenamiento.getId());
		for (SedeEntity aNuevaLista : nuevaLista) {
			assertTrue(sedeEntities.contains(aNuevaLista));
		}
	}
	
	@Test
	void testReplaceSedes2() throws EntityNotFoundException {
		List<SedeEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
			entityManager.persist(entity);
			nuevaLista.add(entity);
		}
		planEntrenamientoSedeService.replaceSedes(planEntrenamiento.getId(), nuevaLista);
		
		List<SedeEntity> sedeEntities = planEntrenamientoSedeService.getSedes(planEntrenamiento.getId());
		for (SedeEntity aNuevaLista : nuevaLista) {
			assertTrue(sedeEntities.contains(aNuevaLista));
		}
	}
	
	@Test
	void testReplaceSedesInvalidPlanEntrenamiento(){
		assertThrows(EntityNotFoundException.class, ()->{
			List<SedeEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
				entity.getPlanes().add(planEntrenamiento);		
				entityManager.persist(entity);
				nuevaLista.add(entity);
			}
			planEntrenamientoSedeService.replaceSedes(0L, nuevaLista);
		});
	}

	@Test
	void testReplaceInvalidSedes() {
		assertThrows(EntityNotFoundException.class, ()->{
			List<SedeEntity> nuevaLista = new ArrayList<>();
			SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
			entity.setId(0L);
			nuevaLista.add(entity);
			planEntrenamientoSedeService.replaceSedes(planEntrenamiento.getId(), nuevaLista);
		});
	}
	
	@Test
	void testReplaceSedesInvalidSede(){
		assertThrows(EntityNotFoundException.class, ()->{
			List<SedeEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
				entity.getPlanes().add(planEntrenamiento);		
				entityManager.persist(entity);
				nuevaLista.add(entity);
			}
			planEntrenamientoSedeService.replaceSedes(0L, nuevaLista);
		});
	}

	@Test
	void testRemoveSede() throws EntityNotFoundException {
		for (SedeEntity sede : sedeList) {
			planEntrenamientoSedeService.removeSede(planEntrenamiento.getId(), sede.getId());
		}
		assertTrue(planEntrenamientoSedeService.getSedes(planEntrenamiento.getId()).isEmpty());
	}

	@Test
	void testRemoveInvalidSede(){
		assertThrows(EntityNotFoundException.class, ()->{
			planEntrenamientoSedeService.removeSede(planEntrenamiento.getId(), 0L);
		});
	}
	
	@Test
	void testRemoveSedeInvalidPlanEntrenamiento(){
		assertThrows(EntityNotFoundException.class, ()->{
			planEntrenamientoSedeService.removeSede(0L, sedeList.get(0).getId());
		});
	}

}