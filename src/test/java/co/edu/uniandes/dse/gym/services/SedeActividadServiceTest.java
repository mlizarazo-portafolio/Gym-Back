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
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.gym.entities.SedeEntity;
import co.edu.uniandes.dse.gym.entities.ActividadEntity;
import co.edu.uniandes.dse.gym.entities.EntrenadorEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.services.SedeActividadService;
import co.edu.uniandes.dse.gym.services.ActividadService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ SedeActividadService.class, ActividadService.class })
public class SedeActividadServiceTest {

    @Autowired
	private SedeActividadService sedeActividadService;

	@Autowired
	private ActividadService actividadService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private SedeEntity sede = new SedeEntity();
	private EntrenadorEntity entrenador = new EntrenadorEntity();
	private List<ActividadEntity> actividadList = new ArrayList<>();

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

		sede = factory.manufacturePojo(SedeEntity.class);
		entityManager.persist(sede);

		for (int i = 0; i < 3; i++) {
			ActividadEntity entity = factory.manufacturePojo(ActividadEntity.class);
			entity.setEntrenador(entrenador);
			entity.getSedes().add(sede);
			entityManager.persist(entity);
			actividadList.add(entity);
			sede.getActividades().add(entity);
		}
	}

    @Test
	void testAddActividad() throws EntityNotFoundException, IllegalOperationException {
		ActividadEntity newActividad = factory.manufacturePojo(ActividadEntity.class);
		newActividad.setEntrenador(entrenador);
		actividadService.createActividad(newActividad);

		ActividadEntity actividadEntity = sedeActividadService.addActividad(sede.getId(), newActividad.getId());
		assertNotNull(actividadEntity);

		assertEquals(actividadEntity.getId(), newActividad.getId());
		assertEquals(actividadEntity.getNombre(), newActividad.getNombre());
		assertEquals(actividadEntity.getMaxParticipantes(), newActividad.getMaxParticipantes());
		assertEquals(actividadEntity.getHorario(), newActividad.getHorario());
		assertEquals(actividadEntity.getTipo(), newActividad.getTipo());

		ActividadEntity lastActividad = sedeActividadService.getActividad(sede.getId(), newActividad.getId());

		assertEquals(lastActividad.getId(), newActividad.getId());
		assertEquals(lastActividad.getNombre(), newActividad.getNombre());
		assertEquals(lastActividad.getMaxParticipantes(), newActividad.getMaxParticipantes());
		assertEquals(lastActividad.getHorario(), newActividad.getHorario());
		assertEquals(lastActividad.getTipo(), newActividad.getTipo());

	}

    @Test
	void testAddActividadInvalidSede() {
		assertThrows(EntityNotFoundException.class, () -> {
			ActividadEntity newActividad = factory.manufacturePojo(ActividadEntity.class);
			newActividad.setEntrenador(entrenador);
			actividadService.createActividad(newActividad);
			sedeActividadService.addActividad(0L, newActividad.getId());
		});
	}

    @Test
	void testAddInvalidActividad() {
		assertThrows(EntityNotFoundException.class, () -> {
			sedeActividadService.addActividad(sede.getId(), 0L);
		});
	}

    @Test
	void testGetActividades() throws EntityNotFoundException {
		List<ActividadEntity> actividadEntities = sedeActividadService.getActividades(sede.getId());

		assertEquals(actividadList.size(), actividadEntities.size());

		for (int i = 0; i < actividadList.size(); i++) {
			assertTrue(actividadEntities.contains(actividadList.get(0)));
		}
	}

    @Test
	void testGetActividadesInvalidSede() {
		assertThrows(EntityNotFoundException.class, () -> {
			sedeActividadService.getActividades(0L);
		});
	}

    @Test
	void testGetActividad() throws EntityNotFoundException, IllegalOperationException {
		ActividadEntity actividadEntity = actividadList.get(0);
		ActividadEntity actividad = sedeActividadService.getActividad(sede.getId(), actividadEntity.getId());
		assertNotNull(actividad);

		assertEquals(actividadEntity.getId(), actividad.getId());
		assertEquals(actividadEntity.getNombre(), actividad.getNombre());
		assertEquals(actividadEntity.getMaxParticipantes(), actividad.getMaxParticipantes());
		assertEquals(actividadEntity.getHorario(), actividad.getHorario());
		assertEquals(actividadEntity.getTipo(), actividad.getTipo());
	}

    @Test
	void testGetActividadInvalidSede() {
		assertThrows(EntityNotFoundException.class, () -> {
			ActividadEntity actividadEntity = actividadList.get(0);
			sedeActividadService.getActividad(0L, actividadEntity.getId());
		});
	}

    @Test
	void testGetInvalidActividad() {
		assertThrows(EntityNotFoundException.class, () -> {
			sedeActividadService.getActividad(sede.getId(), 0L);
		});
	}

    @Test
	void testGetActividadNotAssociatedSede() {
		assertThrows(IllegalOperationException.class, () -> {
			SedeEntity sedeEntity = factory.manufacturePojo(SedeEntity.class);
			entityManager.persist(sedeEntity);

			ActividadEntity actividadEntity = factory.manufacturePojo(ActividadEntity.class);
			actividadEntity.setEntrenador(entrenador);
			entityManager.persist(actividadEntity);

			sedeActividadService.getActividad(sedeEntity.getId(), actividadEntity.getId());
		});
	}

    @Test
	void testReplaceActividades() throws EntityNotFoundException, IllegalOperationException {
		List<ActividadEntity> nuevaLista = new ArrayList<>();
		
		for (int i = 0; i < 3; i++) {
			ActividadEntity entity = factory.manufacturePojo(ActividadEntity.class);
			entity.setEntrenador(entrenador);
			entityManager.persist(entity);
			nuevaLista.add(entity);
		}
		
		sedeActividadService.addActividades(sede.getId(), nuevaLista);
		
		List<ActividadEntity> actividadEntities = entityManager.find(SedeEntity.class, sede.getId()).getActividades();
		for (ActividadEntity item : nuevaLista) {
			assertTrue(actividadEntities.contains(item));
		}
	}

    @Test
	void testReplaceActividadesInvalidSede() {
		assertThrows(EntityNotFoundException.class, () -> {
			List<ActividadEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				ActividadEntity entity = factory.manufacturePojo(ActividadEntity.class);
				entity.setEntrenador(entrenador);
				actividadService.createActividad(entity);
				nuevaLista.add(entity);
			}
			sedeActividadService.addActividades(0L, nuevaLista);
		});
	}

    @Test
	void testReplaceInvalidActividades() {
		assertThrows(EntityNotFoundException.class, () -> {
			List<ActividadEntity> nuevaLista = new ArrayList<>();
			ActividadEntity entity = factory.manufacturePojo(ActividadEntity.class);
			entity.setEntrenador(entrenador);
			entity.setId(0L);
			nuevaLista.add(entity);
			sedeActividadService.addActividades(sede.getId(), nuevaLista);
		});
	}

    @Test
	void testRemoveActividad() throws EntityNotFoundException {
		for (ActividadEntity actividad : actividadList) {
			sedeActividadService.removeActividad(sede.getId(), actividad.getId());
		}
		assertTrue(sedeActividadService.getActividades(sede.getId()).isEmpty());
	}

    @Test
	void testRemoveActividadInvalidSede() {
		assertThrows(EntityNotFoundException.class, () -> {
			for (ActividadEntity actividad : actividadList) {
				sedeActividadService.removeActividad(0L, actividad.getId());
			}
		});
	}

    @Test
	void testRemoveInvalidActividad() {
		assertThrows(EntityNotFoundException.class, () -> {
			sedeActividadService.removeActividad(sede.getId(), 0L);
		});
	}
    
}
