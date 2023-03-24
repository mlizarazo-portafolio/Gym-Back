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

import co.edu.uniandes.dse.gym.entities.AtletaEntity;
import co.edu.uniandes.dse.gym.entities.DeportologoEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.services.DeportologoAtletaService;
import co.edu.uniandes.dse.gym.services.DeportologoService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ DeportologoService.class, DeportologoAtletaService.class })
class DeportologoAtletaServiceTest {

	@Autowired
	private DeportologoAtletaService deportologoAtletaService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<DeportologoEntity> deportologosList = new ArrayList<>();
	private List<AtletaEntity> atletasList = new ArrayList<>();


	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}


	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from AtletaEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from DeportologoEntity").executeUpdate();
	}

	private void insertData() {
		for (int i = 0; i < 3; i++) {
			AtletaEntity atleta = factory.manufacturePojo(AtletaEntity.class);
			entityManager.persist(atleta);
			atletasList.add(atleta);
		}

		for (int i = 0; i < 3; i++) {
			DeportologoEntity entity = factory.manufacturePojo(DeportologoEntity.class);
			entityManager.persist(entity);
			deportologosList.add(entity);
			if (i == 0) {
				atletasList.get(i).setDeportologo(entity);
				entity.getValoracionAtletas().add(atletasList.get(i));
			}
		}
	}


	@Test
	void testAddAtleta() throws EntityNotFoundException {
		DeportologoEntity entity = deportologosList.get(0);
		AtletaEntity atletaEntity = atletasList.get(1);
		AtletaEntity response = deportologoAtletaService.addAtleta(atletaEntity.getId(), entity.getId());

		assertNotNull(response);
		assertEquals(atletaEntity.getId(), response.getId());
	}

	@Test
	void testAddInvalidAtleta() {
		assertThrows(EntityNotFoundException.class, ()->{
			DeportologoEntity entity = deportologosList.get(0);
			deportologoAtletaService.addAtleta(0L, entity.getId());
		});
	}
	

	@Test
	void testAddAtletaInvalidDeportologo() {
		assertThrows(EntityNotFoundException.class, ()->{
			AtletaEntity atletaEntity = atletasList.get(1);
			deportologoAtletaService.addAtleta(atletaEntity.getId(), 0L);
		});
	}



	@Test
	void testGetAtletas() throws EntityNotFoundException {
		List<AtletaEntity> list = deportologoAtletaService.getAtletas(deportologosList.get(0).getId());
		assertEquals(1, list.size());
	}
	

	@Test
	void testGetAtletasInvalidDeportologo() {
		assertThrows(EntityNotFoundException.class,()->{
			deportologoAtletaService.getAtletas(0L);
		});
	}

	@Test
	void testGetAtleta() throws EntityNotFoundException, IllegalOperationException {
		DeportologoEntity entity = deportologosList.get(0);
		AtletaEntity atletaEntity = atletasList.get(0);
		AtletaEntity response = deportologoAtletaService.getAtleta(entity.getId(), atletaEntity.getId());

        assertEquals(atletaEntity.getId(), response.getId());
		assertEquals(atletaEntity.getAltura(), response.getAltura());
        assertEquals(atletaEntity.getDeportologo(), response.getDeportologo());
        assertEquals(atletaEntity.getDireccion(), response.getDireccion());
		assertEquals(atletaEntity.getNombre(), response.getNombre());
		assertEquals(atletaEntity.getLogin(), response.getLogin());
		assertEquals(atletaEntity.getPeso(), response.getPeso());
		assertEquals(atletaEntity.getPlan(), response.getPlan());
		assertEquals(atletaEntity.getActividadesInscritas(), response.getActividadesInscritas());
		assertEquals(atletaEntity.getSede(), response.getSede());
	}
	
	@Test
	void testGetAtletaInvalidDeportologo()  {
		assertThrows(EntityNotFoundException.class, ()->{
			AtletaEntity atletaEntity = atletasList.get(0);
			deportologoAtletaService.getAtleta(0L, atletaEntity.getId());
		});
	}
	
	@Test
	void testGetInvalidAtleta()  {
		assertThrows(EntityNotFoundException.class, ()->{
			DeportologoEntity entity = deportologosList.get(0);
			deportologoAtletaService.getAtleta(entity.getId(), 0L);
		});
	}

	@Test
	public void getAtletaNoAsociadoTest() {
		assertThrows(IllegalOperationException.class, () -> {
			DeportologoEntity entity = deportologosList.get(0);
			AtletaEntity atletaEntity = atletasList.get(1);
			deportologoAtletaService.getAtleta(entity.getId(), atletaEntity.getId());
		});
	}


	@Test
	void testReplaceAtletas() throws EntityNotFoundException {
		DeportologoEntity entity = deportologosList.get(0);
		List<AtletaEntity> list = atletasList.subList(1, 3);
		deportologoAtletaService.replaceAtletas(entity.getId(), list);

		for (AtletaEntity atleta : list) {
			AtletaEntity b = entityManager.find(AtletaEntity.class, atleta.getId());
			assertTrue(b.getDeportologo().equals(entity));
		}
	}

	@Test
	void testReplaceInvalidAtletas() {
		assertThrows(EntityNotFoundException.class, ()->{
			DeportologoEntity entity = deportologosList.get(0);
			
			List<AtletaEntity> atletas = new ArrayList<>();
			AtletaEntity newAtleta = factory.manufacturePojo(AtletaEntity.class);
			newAtleta.setId(0L);
			atletas.add(newAtleta);
			
			deportologoAtletaService.replaceAtletas(entity.getId(), atletas);
		});
	}
	

	@Test
	void testReplaceAtletasInvalidDeportologo() throws EntityNotFoundException {
		assertThrows(EntityNotFoundException.class, ()->{
			List<AtletaEntity> list = atletasList.subList(1, 3);
			deportologoAtletaService.replaceAtletas(0L, list);
		});
	}
}
