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
import co.edu.uniandes.dse.gym.services.AtletaDeportologoService;
import co.edu.uniandes.dse.gym.services.AtletaService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ AtletaService.class, AtletaDeportologoService.class })

class AtletaDeportologoServiceTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private AtletaDeportologoService atletaDeportologoService;

	@Autowired
	private AtletaService atletaService;

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
			AtletaEntity atletas = factory.manufacturePojo(AtletaEntity.class);
			entityManager.persist(atletas);
			atletasList.add(atletas);
		}
		for (int i = 0; i < 3; i++) {
			DeportologoEntity entity = factory.manufacturePojo(DeportologoEntity.class);
			entityManager.persist(entity);
			deportologosList.add(entity);
			if (i == 0) {
				atletasList.get(i).setDeportologo(entity);
			}
		}
	}

	@Test
	void testReplaceDeportologo() throws EntityNotFoundException {
		AtletaEntity entity = atletasList.get(0);
		atletaDeportologoService.replaceDeportologo(entity.getId(), deportologosList.get(1).getId());
		entity = atletaService.getAtleta(entity.getId());
		assertEquals(entity.getDeportologo(), deportologosList.get(1));
	}
	

	@Test
	void testReplaceDeportologoInvalidAtleta() {
		assertThrows(EntityNotFoundException.class, ()->{
			atletaDeportologoService.replaceDeportologo(0L, deportologosList.get(1).getId());
		});
	}
	

	@Test
	void testReplaceInvalidDeportologo() {
		assertThrows(EntityNotFoundException.class, ()->{
			AtletaEntity entity = atletasList.get(0);
			atletaDeportologoService.replaceDeportologo(entity.getId(), 0L);
		});
	}

	@Test
	void testRemoveDeportologo() throws EntityNotFoundException {
		atletaDeportologoService.removeDeportologo(atletasList.get(0).getId());
		AtletaEntity response = atletaService.getAtleta(atletasList.get(0).getId());
		assertNull(response.getDeportologo());
	}
	
	@Test
	void testRemoveDeportologoInvalidAtleta() throws EntityNotFoundException {
		assertThrows(EntityNotFoundException.class, ()->{
			atletaDeportologoService.removeDeportologo(0L);
		});
	}
	

}
