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

import co.edu.uniandes.dse.gym.entities.ServicioEntity;
import co.edu.uniandes.dse.gym.entities.SedeEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.services.ServicioSedeService;
import co.edu.uniandes.dse.gym.services.ServicioService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ ServicioService.class, ServicioSedeService.class })
public class ServicioSedeServiceTest {

    @Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ServicioSedeService servicioSedeService;

	@Autowired
	private ServicioService servicioService;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<SedeEntity> sedesList = new ArrayList<>();
	private List<ServicioEntity> serviciosList = new ArrayList<>();

    @BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

    private void clearData() {
		entityManager.getEntityManager().createQuery("delete from ServicioEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from SedeEntity").executeUpdate();
	}

    private void insertData() {
		for (int i = 0; i < 3; i++) {
			ServicioEntity servicios = factory.manufacturePojo(ServicioEntity.class);
			entityManager.persist(servicios);
			serviciosList.add(servicios);
		}
		for (int i = 0; i < 3; i++) {
			SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
			entityManager.persist(entity);
			sedesList.add(entity);
			if (i == 0) {
				serviciosList.get(i).setSede(entity);
			}
		}
	}

    @Test
	void testReplaceSede() throws EntityNotFoundException {
		ServicioEntity entity = serviciosList.get(0);
		servicioSedeService.replaceSede(entity.getId(), sedesList.get(1).getId());
		entity = servicioService.getServicioById(entity.getId());
		assertEquals(entity.getSede(), sedesList.get(1));
	}

    @Test
	void testReplaceSedeInvalidServicio() {
		assertThrows(EntityNotFoundException.class, ()->{
			servicioSedeService.replaceSede(0L, sedesList.get(1).getId());
		});
	}

    @Test
	void testReplaceInvalidSede() {
		assertThrows(EntityNotFoundException.class, ()->{
			ServicioEntity entity = serviciosList.get(0);
			servicioSedeService.replaceSede(entity.getId(), 0L);
		});
	}

    // @Test
	// void testRemoveSede() throws EntityNotFoundException {
	// 	servicioSedeService.removeSede(serviciosList.get(0).getId());
	// 	ServicioEntity response = servicioService.getServicioById(serviciosList.get(0).getId());
	// 	assertNull(response.getSede());
	// }

    // @Test
	// void testRemoveSedeInvalidServicio() throws EntityNotFoundException {
	// 	assertThrows(EntityNotFoundException.class, ()->{
	// 		servicioSedeService.removeSede(0L);
	// 	});
	// }
    
}