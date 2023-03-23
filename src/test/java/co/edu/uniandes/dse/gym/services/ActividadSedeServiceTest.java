package co.edu.uniandes.dse.gym.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.gym.entities.SedeEntity;
import co.edu.uniandes.dse.gym.entities.ActividadEntity;
import co.edu.uniandes.dse.gym.entities.EntrenadorEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.services.ActividadSedeService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(ActividadSedeService.class)
public class ActividadSedeServiceTest {

    @Autowired
	private ActividadSedeService actividadSedeService;
	
	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private ActividadEntity actividad = new ActividadEntity();
	private EntrenadorEntity entrenador = new EntrenadorEntity();
	private List<SedeEntity> sedeList = new ArrayList<>();

	
	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

    private void clearData() {
		entityManager.getEntityManager().createQuery("delete from SedeEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from ActividadEntity").executeUpdate();
	}

    private void insertData() {
		entrenador = factory.manufacturePojo(EntrenadorEntity.class);
		entityManager.persist(entrenador);

		actividad = factory.manufacturePojo(ActividadEntity.class);
		actividad.setEntrenador(entrenador);
		entityManager.persist(actividad);

		for (int i = 0; i < 3; i++) {
			SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
			entityManager.persist(entity);
			entity.getActividades().add(actividad);
			sedeList.add(entity);
			actividad.getSedes().add(entity);	
		}
	}

    @Test
	void testAddSede() throws EntityNotFoundException, IllegalOperationException {
		ActividadEntity newActividad = factory.manufacturePojo(ActividadEntity.class);
		newActividad.setEntrenador(entrenador);
		entityManager.persist(newActividad);
		
		SedeEntity sede = factory.manufacturePojo(SedeEntity.class);
		entityManager.persist(sede);
		
		actividadSedeService.addSede(newActividad.getId(), sede.getId());
		
		SedeEntity lastSede = actividadSedeService.getSede(newActividad.getId(), sede.getId());
		assertEquals(sede.getId(), lastSede.getId());
		assertEquals(sede.getNombre(), lastSede.getNombre());
		assertEquals(sede.getDireccion(), lastSede.getDireccion());
		assertEquals(sede.getTelefono(), lastSede.getTelefono());
	}

    @Test
	void testAddInvalidSede() {
		assertThrows(EntityNotFoundException.class, ()->{
			ActividadEntity newActividad = factory.manufacturePojo(ActividadEntity.class);
			newActividad.setEntrenador(entrenador);
			entityManager.persist(newActividad);
			actividadSedeService.addSede(newActividad.getId(), 0L);
		});
	}

    @Test
	void testAddSedeInvalidActividad() throws EntityNotFoundException, IllegalOperationException {
		assertThrows(EntityNotFoundException.class, ()->{
			SedeEntity sede = factory.manufacturePojo(SedeEntity.class);
			entityManager.persist(sede);
			actividadSedeService.addSede(0L, sede.getId());
		});
	}

    @Test
	void testGetSedes() throws EntityNotFoundException {
		List<SedeEntity> sedeEntities = actividadSedeService.getSedes(actividad.getId());

		assertEquals(sedeList.size(), sedeEntities.size());

		for (int i = 0; i < sedeList.size(); i++) {
			assertTrue(sedeEntities.contains(sedeList.get(0)));
		}
	}

    @Test
	void testGetSedesInvalidActividad(){
		assertThrows(EntityNotFoundException.class, ()->{
			actividadSedeService.getSedes(0L);
		});
	}

    @Test
	void testGetSede() throws EntityNotFoundException, IllegalOperationException {
		SedeEntity sedeEntity = sedeList.get(0);
		SedeEntity sede = actividadSedeService.getSede(actividad.getId(), sedeEntity.getId());
		assertNotNull(sede);

		assertEquals(sedeEntity.getId(), sede.getId());
		assertEquals(sedeEntity.getNombre(), sede.getNombre());
		assertEquals(sedeEntity.getDireccion(), sede.getDireccion());
		assertEquals(sedeEntity.getTelefono(), sede.getTelefono());
	}

    @Test
	void testGetInvalidSede()  {
		assertThrows(EntityNotFoundException.class, ()->{
			actividadSedeService.getSede(actividad.getId(), 0L);
		});
	}

    @Test
	void testGetSedeInvalidActividad() {
		assertThrows(EntityNotFoundException.class, ()->{
			SedeEntity sedeEntity = sedeList.get(0);
			actividadSedeService.getSede(0L, sedeEntity.getId());
		});
	}

    @Test
	void testGetNotAssociatedSede() {
		assertThrows(IllegalOperationException.class, ()->{
			ActividadEntity newActividad = factory.manufacturePojo(ActividadEntity.class);
			newActividad.setEntrenador(entrenador);
			entityManager.persist(newActividad);
			SedeEntity sede = factory.manufacturePojo(SedeEntity.class);
			entityManager.persist(sede);
			actividadSedeService.getSede(newActividad.getId(), sede.getId());
		});
	}

    @Test
	void testReplaceSedes() throws EntityNotFoundException {
		List<SedeEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
			entityManager.persist(entity);
			actividad.getSedes().add(entity);
			nuevaLista.add(entity);
		}
		actividadSedeService.replaceSedes(actividad.getId(), nuevaLista);
		
		List<SedeEntity> sedeEntities = actividadSedeService.getSedes(actividad.getId());
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
		actividadSedeService.replaceSedes(actividad.getId(), nuevaLista);
		
		List<SedeEntity> sedeEntities = actividadSedeService.getSedes(actividad.getId());
		for (SedeEntity aNuevaLista : nuevaLista) {
			assertTrue(sedeEntities.contains(aNuevaLista));
		}
	}

    @Test
	void testReplaceSedesInvalidActividad(){
		assertThrows(EntityNotFoundException.class, ()->{
			List<SedeEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
				entity.getActividades().add(actividad);		
				entityManager.persist(entity);
				nuevaLista.add(entity);
			}
			actividadSedeService.replaceSedes(0L, nuevaLista);
		});
	}

    @Test
	void testReplaceInvalidSedes() {
		assertThrows(EntityNotFoundException.class, ()->{
			List<SedeEntity> nuevaLista = new ArrayList<>();
			SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
			entity.setId(0L);
			nuevaLista.add(entity);
			actividadSedeService.replaceSedes(actividad.getId(), nuevaLista);
		});
	}

    @Test
	void testReplaceSedesInvalidSede(){
		assertThrows(EntityNotFoundException.class, ()->{
			List<SedeEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
				entity.getActividades().add(actividad);		
				entityManager.persist(entity);
				nuevaLista.add(entity);
			}
			actividadSedeService.replaceSedes(0L, nuevaLista);
		});
	}

    @Test
	void testRemoveSede() throws EntityNotFoundException {
		for (SedeEntity sede : sedeList) {
			actividadSedeService.removeSede(actividad.getId(), sede.getId());
		}
		assertTrue(actividadSedeService.getSedes(actividad.getId()).isEmpty());
	}

    @Test
	void testRemoveInvalidSede(){
		assertThrows(EntityNotFoundException.class, ()->{
			actividadSedeService.removeSede(actividad.getId(), 0L);
		});
	}

    @Test
	void testRemoveSedeInvalidActividad(){
		assertThrows(EntityNotFoundException.class, ()->{
			actividadSedeService.removeSede(0L, sedeList.get(0).getId());
		});
	}
    
}
