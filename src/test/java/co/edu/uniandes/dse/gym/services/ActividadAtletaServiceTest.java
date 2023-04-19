package co.edu.uniandes.dse.gym.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import co.edu.uniandes.dse.gym.entities.AtletaEntity;
import co.edu.uniandes.dse.gym.entities.ActividadEntity;
import co.edu.uniandes.dse.gym.entities.EntrenadorEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(ActividadAtletaService.class)
public class ActividadAtletaServiceTest {

    @Autowired
	private ActividadAtletaService actividadAtletaService;
	
	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private ActividadEntity actividad = new ActividadEntity();
	private EntrenadorEntity entrenador = new EntrenadorEntity();
	private List<AtletaEntity> atletaList = new ArrayList<>();

	
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

		actividad = factory.manufacturePojo(ActividadEntity.class);
		actividad.setEntrenador(entrenador);
		entityManager.persist(actividad);

		for (int i = 0; i < 3; i++) {
			AtletaEntity entity = factory.manufacturePojo(AtletaEntity.class);
			entityManager.persist(entity);
			entity.getActividadesInscritas().add(actividad);
			atletaList.add(entity);
			actividad.getAtletas().add(entity);	
		}
	}

    @Test
	void testAddAtleta() throws EntityNotFoundException, IllegalOperationException {
		ActividadEntity newActividad = factory.manufacturePojo(ActividadEntity.class);
		newActividad.setEntrenador(entrenador);
		entityManager.persist(newActividad);
		
		AtletaEntity atleta = factory.manufacturePojo(AtletaEntity.class);
		entityManager.persist(atleta);
		
		actividadAtletaService.addAtleta(newActividad.getId(), atleta.getId());
		
		AtletaEntity lastAtleta = actividadAtletaService.getAtleta(newActividad.getId(), atleta.getId());
		assertEquals(atleta.getId(), lastAtleta.getId());
		assertEquals(atleta.getAltura(), lastAtleta.getAltura());
        assertEquals(atleta.getDeportologo(), lastAtleta.getDeportologo());
        assertEquals(atleta.getDireccion(), lastAtleta.getDireccion());
		assertEquals(atleta.getNombre(), lastAtleta.getNombre());
		assertEquals(atleta.getLogin(), lastAtleta.getLogin());
		assertEquals(atleta.getPeso(), lastAtleta.getPeso());
		assertEquals(atleta.getPlan(), lastAtleta.getPlan());
		assertEquals(atleta.getActividadesInscritas(), lastAtleta.getActividadesInscritas());
		assertEquals(atleta.getSede(), lastAtleta.getSede());
	}

    @Test
	void testAddInvalidAtleta() {
		assertThrows(EntityNotFoundException.class, ()->{
			ActividadEntity newActividad = factory.manufacturePojo(ActividadEntity.class);
			newActividad.setEntrenador(entrenador);
			entityManager.persist(newActividad);
			actividadAtletaService.addAtleta(newActividad.getId(), 0L);
		});
	}

    @Test
	void testAddAtletaInvalidActividad() throws EntityNotFoundException, IllegalOperationException {
		assertThrows(EntityNotFoundException.class, ()->{
			AtletaEntity atleta = factory.manufacturePojo(AtletaEntity.class);
			entityManager.persist(atleta);
			actividadAtletaService.addAtleta(0L, atleta.getId());
		});
	}

    @Test
	void testGetAtletas() throws EntityNotFoundException {
		List<AtletaEntity> atletaEntities = actividadAtletaService.getAtletas(actividad.getId());

		assertEquals(atletaList.size(), atletaEntities.size());

		for (int i = 0; i < atletaList.size(); i++) {
			assertTrue(atletaEntities.contains(atletaList.get(0)));
		}
	}

    @Test
	void testGetAtletasInvalidActividad(){
		assertThrows(EntityNotFoundException.class, ()->{
			actividadAtletaService.getAtletas(0L);
		});
	}

    @Test
	void testGetAtleta() throws EntityNotFoundException, IllegalOperationException {
		AtletaEntity atletaEntity = atletaList.get(0);
		AtletaEntity atleta = actividadAtletaService.getAtleta(actividad.getId(), atletaEntity.getId());
		assertNotNull(atleta);

		assertEquals(atletaEntity.getId(), atleta.getId());
		assertEquals(atletaEntity.getId(), atleta.getId());
		assertEquals(atletaEntity.getAltura(), atleta.getAltura());
        assertEquals(atletaEntity.getDeportologo(), atleta.getDeportologo());
        assertEquals(atletaEntity.getDireccion(), atleta.getDireccion());
		assertEquals(atletaEntity.getNombre(), atleta.getNombre());
		assertEquals(atletaEntity.getLogin(), atleta.getLogin());
		assertEquals(atletaEntity.getPeso(), atleta.getPeso());
		assertEquals(atletaEntity.getPlan(), atleta.getPlan());
		assertEquals(atletaEntity.getActividadesInscritas(), atleta.getActividadesInscritas());
		assertEquals(atletaEntity.getSede(), atleta.getSede());
	}

    @Test
	void testGetInvalidAtleta()  {
		assertThrows(EntityNotFoundException.class, ()->{
			actividadAtletaService.getAtleta(actividad.getId(), 0L);
		});
	}

    @Test
	void testGetAtletaInvalidActividad() {
		assertThrows(EntityNotFoundException.class, ()->{
			AtletaEntity atletaEntity = atletaList.get(0);
			actividadAtletaService.getAtleta(0L, atletaEntity.getId());
		});
	}

    @Test
	void testGetNotAssociatedAtleta() {
		assertThrows(IllegalOperationException.class, ()->{
			ActividadEntity newActividad = factory.manufacturePojo(ActividadEntity.class);
			newActividad.setEntrenador(entrenador);
			entityManager.persist(newActividad);
			AtletaEntity atleta = factory.manufacturePojo(AtletaEntity.class);
			entityManager.persist(atleta);
			actividadAtletaService.getAtleta(newActividad.getId(), atleta.getId());
		});
	}

    @Test
	void testReplaceAtletas() throws EntityNotFoundException {
		List<AtletaEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			AtletaEntity entity = factory.manufacturePojo(AtletaEntity.class);
			entityManager.persist(entity);
			actividad.getAtletas().add(entity);
			nuevaLista.add(entity);
		}
		actividadAtletaService.replaceAtletas(actividad.getId(), nuevaLista);
		
		List<AtletaEntity> atletaEntities = actividadAtletaService.getAtletas(actividad.getId());
		for (AtletaEntity aNuevaLista : nuevaLista) {
			assertTrue (atletaEntities.contains(aNuevaLista));
		}
	}

    @Test
	void testReplaceAtletas2() throws EntityNotFoundException {
		List<AtletaEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			AtletaEntity entity = factory.manufacturePojo(AtletaEntity.class);
			entityManager.persist(entity);
			nuevaLista.add(entity);
		}
		actividadAtletaService.replaceAtletas(actividad.getId(), nuevaLista);
		
		List<AtletaEntity> atletaEntities = actividadAtletaService.getAtletas(actividad.getId());
		for (AtletaEntity aNuevaLista : nuevaLista) {
			assertTrue (atletaEntities.contains(aNuevaLista));
		}
	}

    @Test
	void testReplaceAtletasInvalidActividad(){
		assertThrows(EntityNotFoundException.class, ()->{
			List<AtletaEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				AtletaEntity entity = factory.manufacturePojo(AtletaEntity.class);
				entity.getActividadesInscritas().add(actividad);		
				entityManager.persist(entity);
				nuevaLista.add(entity);
			}
			actividadAtletaService.replaceAtletas(0L, nuevaLista);
		});
	}

    @Test
	void testReplaceInvalidAtletas() {
		assertThrows(EntityNotFoundException.class, ()->{
			List<AtletaEntity> nuevaLista = new ArrayList<>();
			AtletaEntity entity = factory.manufacturePojo(AtletaEntity.class);
			entity.setId(0L);
			nuevaLista.add(entity);
			actividadAtletaService.replaceAtletas(actividad.getId(), nuevaLista);
		});
	}

    @Test
	void testReplaceAtletasInvalidAtleta(){
		assertThrows(EntityNotFoundException.class, ()->{
			List<AtletaEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				AtletaEntity entity = factory.manufacturePojo(AtletaEntity.class);
				entity.getActividadesInscritas().add(actividad);		
				entityManager.persist(entity);
				nuevaLista.add(entity);
			}
			actividadAtletaService.replaceAtletas(0L, nuevaLista);
		});
	}

    @Test
	void testRemoveAtleta() throws EntityNotFoundException {
		for (AtletaEntity atleta : atletaList) {
			actividadAtletaService.removeAtleta(actividad.getId(), atleta.getId());
		}
		assertTrue(actividadAtletaService.getAtletas(actividad.getId()).isEmpty());
	}

    @Test
	void testRemoveInvalidAtleta(){
		assertThrows(EntityNotFoundException.class, ()->{
			actividadAtletaService.removeAtleta(actividad.getId(), 0L);
		});
	}

    @Test
	void testRemoveAtletaInvalidActividad(){
		assertThrows(EntityNotFoundException.class, ()->{
			actividadAtletaService.removeAtleta(0L, atletaList.get(0).getId());
		});
	}
    
}