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

import co.edu.uniandes.dse.gym.entities.PlanEntrenamientoEntity;
import co.edu.uniandes.dse.gym.entities.SedeEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.services.SedePlanEntrenamientoService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(SedePlanEntrenamientoService.class)
class SedePlanEntrenamientoServiceTest {
	
	@Autowired
	private SedePlanEntrenamientoService sedePlanEntrenamientoService;
	
	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private SedeEntity sede = new SedeEntity();
	private List<PlanEntrenamientoEntity> planEntrenamientoList = new ArrayList<>();

	
	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}
	
	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from PlanEntrenamientoEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from SedeEntity").executeUpdate();
	}

	private void insertData() {

		sede = factory.manufacturePojo(SedeEntity.class);
		entityManager.persist(sede);

		for (int i = 0; i < 3; i++) {
			PlanEntrenamientoEntity entity = factory.manufacturePojo(PlanEntrenamientoEntity.class);
			entityManager.persist(entity);
			entity.getSedes().add(sede);
			planEntrenamientoList.add(entity);
			sede.getPlanes().add(entity);	
		}
	}

	@Test
	void testAddPlanEntrenamiento() throws EntityNotFoundException, IllegalOperationException {
		SedeEntity newSede = factory.manufacturePojo(SedeEntity.class);
		entityManager.persist(newSede);
		
		PlanEntrenamientoEntity planEntrenamiento = factory.manufacturePojo(PlanEntrenamientoEntity.class);
		entityManager.persist(planEntrenamiento);
		
		sedePlanEntrenamientoService.addPlanEntrenamiento(newSede.getId(), planEntrenamiento.getId());
		
		PlanEntrenamientoEntity lastPlanEntrenamiento = sedePlanEntrenamientoService.getPlanEntrenamiento(newSede.getId(), planEntrenamiento.getId());
		assertEquals(planEntrenamiento.getId(), lastPlanEntrenamiento.getId());
		assertEquals(planEntrenamiento.getObjetivoBasico(), lastPlanEntrenamiento.getObjetivoBasico());
		assertEquals(planEntrenamiento.getNombre(), lastPlanEntrenamiento.getNombre());
		assertEquals(planEntrenamiento.getDescripcion(), lastPlanEntrenamiento.getDescripcion());
		assertEquals(planEntrenamiento.getDirrecion(), lastPlanEntrenamiento.getDirrecion());
        assertEquals(planEntrenamiento.getDuracion(), lastPlanEntrenamiento.getDuracion());
        assertEquals(planEntrenamiento.getCosto(), lastPlanEntrenamiento.getCosto());
	}
	
	@Test
	void testAddInvalidPlanEntrenamiento() {
		assertThrows(EntityNotFoundException.class, ()->{
			SedeEntity newSede = factory.manufacturePojo(SedeEntity.class);
			entityManager.persist(newSede);
			sedePlanEntrenamientoService.addPlanEntrenamiento(newSede.getId(), 0L);
		});
	}

	@Test
	void testAddPlanEntrenamientoInvalidSede() throws EntityNotFoundException, IllegalOperationException {
		assertThrows(EntityNotFoundException.class, ()->{
			PlanEntrenamientoEntity planEntrenamiento = factory.manufacturePojo(PlanEntrenamientoEntity.class);
			entityManager.persist(planEntrenamiento);
			sedePlanEntrenamientoService.addPlanEntrenamiento(0L, planEntrenamiento.getId());
		});
	}

	@Test
	void testGetPlanEntrenamientos() throws EntityNotFoundException {
		List<PlanEntrenamientoEntity> planEntrenamientoEntities = sedePlanEntrenamientoService.getPlanEntrenamientos(sede.getId());

		assertEquals(planEntrenamientoList.size(), planEntrenamientoEntities.size());

		for (int i = 0; i < planEntrenamientoList.size(); i++) {
			assertTrue(planEntrenamientoEntities.contains(planEntrenamientoList.get(0)));
		}
	}

	void testGetPlanEntrenamientosInvalidSede(){
		assertThrows(EntityNotFoundException.class, ()->{
			sedePlanEntrenamientoService.getPlanEntrenamientos(0L);
		});
	}

	@Test
	void testGetPlanEntrenamiento() throws EntityNotFoundException, IllegalOperationException {
		PlanEntrenamientoEntity planEntrenamientoEntity = planEntrenamientoList.get(0);
		PlanEntrenamientoEntity planEntrenamiento = sedePlanEntrenamientoService.getPlanEntrenamiento(sede.getId(), planEntrenamientoEntity.getId());
		assertNotNull(planEntrenamiento);

		assertEquals(planEntrenamiento.getId(), planEntrenamiento.getId());
		assertEquals(planEntrenamiento.getObjetivoBasico(), planEntrenamiento.getObjetivoBasico());
		assertEquals(planEntrenamiento.getNombre(), planEntrenamiento.getNombre());
		assertEquals(planEntrenamiento.getDescripcion(), planEntrenamiento.getDescripcion());
		assertEquals(planEntrenamiento.getDirrecion(), planEntrenamiento.getDirrecion());
        assertEquals(planEntrenamiento.getDuracion(), planEntrenamiento.getDuracion());
        assertEquals(planEntrenamiento.getCosto(), planEntrenamiento.getCosto());
	}

	@Test
	void testGetInvalidPlanEntrenamiento()  {
		assertThrows(EntityNotFoundException.class, ()->{
			sedePlanEntrenamientoService.getPlanEntrenamiento(sede.getId(), 0L);
		});
	}

	@Test
	void testGetPlanEntrenamientoInvalidSede() {
		assertThrows(EntityNotFoundException.class, ()->{
			PlanEntrenamientoEntity planEntrenamientoEntity = planEntrenamientoList.get(0);
			sedePlanEntrenamientoService.getPlanEntrenamiento(0L, planEntrenamientoEntity.getId());
		});
	}
	
	@Test
	void testGetNotAssociatedPlanEntrenamiento() {
		assertThrows(IllegalOperationException.class, ()->{
			SedeEntity newSede = factory.manufacturePojo(SedeEntity.class);
			entityManager.persist(newSede);
			PlanEntrenamientoEntity planEntrenamiento = factory.manufacturePojo(PlanEntrenamientoEntity.class);
			entityManager.persist(planEntrenamiento);
			sedePlanEntrenamientoService.getPlanEntrenamiento(newSede.getId(), planEntrenamiento.getId());
		});
	}

	@Test
	void testReplacePlanEntrenamientos() throws EntityNotFoundException {
		List<PlanEntrenamientoEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			PlanEntrenamientoEntity entity = factory.manufacturePojo(PlanEntrenamientoEntity.class);
			entityManager.persist(entity);
			sede.getPlanes().add(entity);
			nuevaLista.add(entity);
		}
		sedePlanEntrenamientoService.replacePlanEntrenamientos(sede.getId(), nuevaLista);
		
		List<PlanEntrenamientoEntity> planEntrenamientoEntities = sedePlanEntrenamientoService.getPlanEntrenamientos(sede.getId());
		for (PlanEntrenamientoEntity aNuevaLista : nuevaLista) {
			assertTrue(planEntrenamientoEntities.contains(aNuevaLista));
		}
	}
	
	@Test
	void testReplacePlanEntrenamientos2() throws EntityNotFoundException {
		List<PlanEntrenamientoEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			PlanEntrenamientoEntity entity = factory.manufacturePojo(PlanEntrenamientoEntity.class);
			entityManager.persist(entity);
			nuevaLista.add(entity);
		}
		sedePlanEntrenamientoService.replacePlanEntrenamientos(sede.getId(), nuevaLista);
		
		List<PlanEntrenamientoEntity> planEntrenamientoEntities = sedePlanEntrenamientoService.getPlanEntrenamientos(sede.getId());
		for (PlanEntrenamientoEntity aNuevaLista : nuevaLista) {
			assertTrue(planEntrenamientoEntities.contains(aNuevaLista));
		}
	}
	
	@Test
	void testReplacePlanEntrenamientosInvalidSede(){
		assertThrows(EntityNotFoundException.class, ()->{
			List<PlanEntrenamientoEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				PlanEntrenamientoEntity entity = factory.manufacturePojo(PlanEntrenamientoEntity.class);
				entity.getSedes().add(sede);		
				entityManager.persist(entity);
				nuevaLista.add(entity);
			}
			sedePlanEntrenamientoService.replacePlanEntrenamientos(0L, nuevaLista);
		});
	}

	@Test
	void testReplaceInvalidPlanEntrenamientos() {
		assertThrows(EntityNotFoundException.class, ()->{
			List<PlanEntrenamientoEntity> nuevaLista = new ArrayList<>();
			PlanEntrenamientoEntity entity = factory.manufacturePojo(PlanEntrenamientoEntity.class);
			entity.setId(0L);
			nuevaLista.add(entity);
			sedePlanEntrenamientoService.replacePlanEntrenamientos(sede.getId(), nuevaLista);
		});
	}
	
	@Test
	void testReplacePlanEntrenamientosInvalidPlanEntrenamiento(){
		assertThrows(EntityNotFoundException.class, ()->{
			List<PlanEntrenamientoEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				PlanEntrenamientoEntity entity = factory.manufacturePojo(PlanEntrenamientoEntity.class);
				entity.getSedes().add(sede);		
				entityManager.persist(entity);
				nuevaLista.add(entity);
			}
			sedePlanEntrenamientoService.replacePlanEntrenamientos(0L, nuevaLista);
		});
	}

	@Test
	void testRemovePlanEntrenamiento() throws EntityNotFoundException {
		for (PlanEntrenamientoEntity planEntrenamiento : planEntrenamientoList) {
			sedePlanEntrenamientoService.removePlanEntrenamiento(sede.getId(), planEntrenamiento.getId());
		}
		assertTrue(sedePlanEntrenamientoService.getPlanEntrenamientos(sede.getId()).isEmpty());
	}

	@Test
	void testRemoveInvalidPlanEntrenamiento(){
		assertThrows(EntityNotFoundException.class, ()->{
			sedePlanEntrenamientoService.removePlanEntrenamiento(sede.getId(), 0L);
		});
	}
	
	@Test
	void testRemovePlanEntrenamientoInvalidSede(){
		assertThrows(EntityNotFoundException.class, ()->{
			sedePlanEntrenamientoService.removePlanEntrenamiento(0L, planEntrenamientoList.get(0).getId());
		});
	}

}