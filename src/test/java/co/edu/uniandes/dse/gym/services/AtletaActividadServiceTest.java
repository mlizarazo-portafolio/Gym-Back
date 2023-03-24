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

import co.edu.uniandes.dse.gym.entities.AtletaEntity;
import co.edu.uniandes.dse.gym.entities.ActividadEntity;
import co.edu.uniandes.dse.gym.entities.EntrenadorEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.services.AtletaActividadService;
import co.edu.uniandes.dse.gym.services.ActividadService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ AtletaActividadService.class, ActividadService.class })
public class AtletaActividadServiceTest {

    @Autowired
	private AtletaActividadService atletaActividadService;

	@Autowired
	private ActividadService actividadService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private AtletaEntity atleta = new AtletaEntity();
	private EntrenadorEntity entrenador = new EntrenadorEntity();
	private List<ActividadEntity> actividadList = new ArrayList<>();

    @BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

    private void clearData() {
		entityManager.getEntityManager().createQuery("delete from AtletaEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from ActividadEntity").executeUpdate();
	}

    private void insertData() {
		entrenador = factory.manufacturePojo(EntrenadorEntity.class);
		entityManager.persist(entrenador);

		atleta = factory.manufacturePojo(AtletaEntity.class);
		entityManager.persist(atleta);

		for (int i = 0; i < 3; i++) {
			ActividadEntity entity = factory.manufacturePojo(ActividadEntity.class);
			entity.setEntrenador(entrenador);
			entity.getAtletasInscritos().add(atleta);
			entityManager.persist(entity);
			actividadList.add(entity);
			atleta.getActividadesInscritas().add(entity);
		}
	}

    @Test
	void testAddActividad() throws EntityNotFoundException, IllegalOperationException {
		ActividadEntity newActividad = factory.manufacturePojo(ActividadEntity.class);
		newActividad.setEntrenador(entrenador);
		actividadService.createActividad(newActividad);

		ActividadEntity actividadEntity = atletaActividadService.addActividad(atleta.getId(), newActividad.getId());
		assertNotNull(actividadEntity);

		assertEquals(actividadEntity.getId(), newActividad.getId());
		assertEquals(actividadEntity.getNombre(), newActividad.getNombre());
		assertEquals(actividadEntity.getMaxParticipantes(), newActividad.getMaxParticipantes());
		assertEquals(actividadEntity.getHorario(), newActividad.getHorario());
		assertEquals(actividadEntity.getTipo(), newActividad.getTipo());

		ActividadEntity lastActividad = atletaActividadService.getActividad(atleta.getId(), newActividad.getId());

		assertEquals(lastActividad.getId(), newActividad.getId());
		assertEquals(lastActividad.getNombre(), newActividad.getNombre());
		assertEquals(lastActividad.getMaxParticipantes(), newActividad.getMaxParticipantes());
		assertEquals(lastActividad.getHorario(), newActividad.getHorario());
		assertEquals(lastActividad.getTipo(), newActividad.getTipo());

	}

    @Test
	void testAddActividadInvalidAtleta() {
		assertThrows(EntityNotFoundException.class, () -> {
			ActividadEntity newActividad = factory.manufacturePojo(ActividadEntity.class);
			newActividad.setEntrenador(entrenador);
			actividadService.createActividad(newActividad);
			atletaActividadService.addActividad(0L, newActividad.getId());
		});
	}

    @Test
	void testAddInvalidActividad() {
		assertThrows(EntityNotFoundException.class, () -> {
			atletaActividadService.addActividad(atleta.getId(), 0L);
		});
	}

    @Test
	void testGetActividades() throws EntityNotFoundException {
		List<ActividadEntity> actividadEntities = atletaActividadService.getActividades(atleta.getId());

		assertEquals(actividadList.size(), actividadEntities.size());

		for (int i = 0; i < actividadList.size(); i++) {
			assertTrue(actividadEntities.contains(actividadList.get(0)));
		}
	}

    @Test
	void testGetActividadesInvalidAtleta() {
		assertThrows(EntityNotFoundException.class, () -> {
			atletaActividadService.getActividades(0L);
		});
	}

    @Test
	void testGetActividad() throws EntityNotFoundException, IllegalOperationException {
		ActividadEntity actividadEntity = actividadList.get(0);
		ActividadEntity actividad = atletaActividadService.getActividad(atleta.getId(), actividadEntity.getId());
		assertNotNull(actividad);

		assertEquals(actividadEntity.getId(), actividad.getId());
		assertEquals(actividadEntity.getNombre(), actividad.getNombre());
		assertEquals(actividadEntity.getMaxParticipantes(), actividad.getMaxParticipantes());
		assertEquals(actividadEntity.getHorario(), actividad.getHorario());
		assertEquals(actividadEntity.getTipo(), actividad.getTipo());
	}

    @Test
	void testGetActividadInvalidAtleta() {
		assertThrows(EntityNotFoundException.class, () -> {
			ActividadEntity actividadEntity = actividadList.get(0);
			atletaActividadService.getActividad(0L, actividadEntity.getId());
		});
	}

    @Test
	void testGetInvalidActividad() {
		assertThrows(EntityNotFoundException.class, () -> {
			atletaActividadService.getActividad(atleta.getId(), 0L);
		});
	}

    @Test
	void testGetActividadNotAssociatedAtleta() {
		assertThrows(IllegalOperationException.class, () -> {
			AtletaEntity atletaEntity = factory.manufacturePojo(AtletaEntity.class);
			entityManager.persist(atletaEntity);

			ActividadEntity actividadEntity = factory.manufacturePojo(ActividadEntity.class);
			actividadEntity.setEntrenador(entrenador);
			entityManager.persist(actividadEntity);

			atletaActividadService.getActividad(atletaEntity.getId(), actividadEntity.getId());
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
		
		atletaActividadService.addActividades(atleta.getId(), nuevaLista);
		
		List<ActividadEntity> actividadEntities = entityManager.find(AtletaEntity.class, atleta.getId()).getActividadesInscritas();
		for (ActividadEntity item : nuevaLista) {
			assertTrue(actividadEntities.contains(item));
		}
	}

    @Test
	void testReplaceActividadesInvalidAtleta() {
		assertThrows(EntityNotFoundException.class, () -> {
			List<ActividadEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				ActividadEntity entity = factory.manufacturePojo(ActividadEntity.class);
				entity.setEntrenador(entrenador);
				actividadService.createActividad(entity);
				nuevaLista.add(entity);
			}
			atletaActividadService.addActividades(0L, nuevaLista);
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
			atletaActividadService.addActividades(atleta.getId(), nuevaLista);
		});
	}

    @Test
	void testRemoveActividad() throws EntityNotFoundException {
		for (ActividadEntity actividad : actividadList) {
			atletaActividadService.removeActividad(atleta.getId(), actividad.getId());
		}
		assertTrue(atletaActividadService.getActividades(atleta.getId()).isEmpty());
	}

    @Test
	void testRemoveActividadInvalidAtleta() {
		assertThrows(EntityNotFoundException.class, () -> {
			for (ActividadEntity actividad : actividadList) {
				atletaActividadService.removeActividad(0L, actividad.getId());
			}
		});
	}

    @Test
	void testRemoveInvalidActividad() {
		assertThrows(EntityNotFoundException.class, () -> {
			atletaActividadService.removeActividad(atleta.getId(), 0L);
		});
	}
    
}
