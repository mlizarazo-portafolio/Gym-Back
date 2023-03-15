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
import co.edu.uniandes.dse.gym.services.SedeServicioService;
import co.edu.uniandes.dse.gym.services.SedeService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ SedeService.class, SedeServicioService.class })
class SedeServicioServiceTest {

	@Autowired
	private SedeServicioService sedeServicioService;

	@Autowired
	private TestEntityManager entityManager;

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
			ServicioEntity servicio = factory.manufacturePojo(ServicioEntity.class);
			entityManager.persist(servicio);
			serviciosList.add(servicio);
		}

		for (int i = 0; i < 3; i++) {
			SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
			entityManager.persist(entity);
			sedesList.add(entity);
			if (i == 0) {
				serviciosList.get(i).setSede(entity);
				entity.getServiciosDisponibles().add(serviciosList.get(i));
			}
		}
	}

	@Test
	void testAddServicio() throws EntityNotFoundException {
		SedeEntity entity = sedesList.get(0);
		ServicioEntity servicioEntity = serviciosList.get(1);
		ServicioEntity response = sedeServicioService.addServicio(servicioEntity.getId(), entity.getId());

		assertNotNull(response);
		assertEquals(servicioEntity.getId(), response.getId());
	}
	
	@Test
	void testAddInvalidServicio() {
		assertThrows(EntityNotFoundException.class, ()->{
			SedeEntity entity = sedesList.get(0);
			sedeServicioService.addServicio(0L, entity.getId());
		});
	}
	
	@Test
	void testAddServicioInvalidSede() {
		assertThrows(EntityNotFoundException.class, ()->{
			ServicioEntity servicioEntity = serviciosList.get(1);
			sedeServicioService.addServicio(servicioEntity.getId(), 0L);
		});
	}

	@Test
	void testGetServicios() throws EntityNotFoundException {
		List<ServicioEntity> list = sedeServicioService.getServicios(sedesList.get(0).getId());
		assertEquals(1, list.size());
	}
	
	@Test
	void testGetServiciosInvalidSede() {
		assertThrows(EntityNotFoundException.class,()->{
			sedeServicioService.getServicios(0L);
		});
	}

	@Test
	void testGetServicio() throws EntityNotFoundException, IllegalOperationException {
		SedeEntity entity = sedesList.get(0);
		ServicioEntity servicioEntity = serviciosList.get(0);
		ServicioEntity response = sedeServicioService.getServicio(entity.getId(), servicioEntity.getId());

		assertEquals(servicioEntity.getId(), response.getId());
		assertEquals(servicioEntity.getServicio(), response.getServicio());
		assertEquals(servicioEntity.getDisponible(), response.getDisponible());
	}
	
	@Test
	void testGetServicioInvalidSede()  {
		assertThrows(EntityNotFoundException.class, ()->{
			ServicioEntity servicioEntity = serviciosList.get(0);
			sedeServicioService.getServicio(0L, servicioEntity.getId());
		});
	}
	
	@Test
	void testGetInvalidServicio()  {
		assertThrows(EntityNotFoundException.class, ()->{
			SedeEntity entity = sedesList.get(0);
			sedeServicioService.getServicio(entity.getId(), 0L);
		});
	}

	@Test
	public void getServicioNoAsociadoTest() {
		assertThrows(IllegalOperationException.class, () -> {
			SedeEntity entity = sedesList.get(0);
			ServicioEntity servicioEntity = serviciosList.get(1);
			sedeServicioService.getServicio(entity.getId(), servicioEntity.getId());
		});
	}

	@Test
	void testReplaceServicios() throws EntityNotFoundException {
		SedeEntity entity = sedesList.get(0);
		List<ServicioEntity> list = serviciosList.subList(1, 3);
		sedeServicioService.replaceServicios(entity.getId(), list);

		for (ServicioEntity servicio : list) {
			ServicioEntity b = entityManager.find(ServicioEntity.class, servicio.getId());
			assertTrue(b.getSede().equals(entity));
		}
	}
	
	@Test
	void testReplaceInvalidServicios() {
		assertThrows(EntityNotFoundException.class, ()->{
			SedeEntity entity = sedesList.get(0);
			
			List<ServicioEntity> servicios = new ArrayList<>();
			ServicioEntity newServicio = factory.manufacturePojo(ServicioEntity.class);
			newServicio.setId(0L);
			servicios.add(newServicio);
			
			sedeServicioService.replaceServicios(entity.getId(), servicios);
		});
	}

	@Test
	void testReplaceServiciosInvalidSede() throws EntityNotFoundException {
		assertThrows(EntityNotFoundException.class, ()->{
			List<ServicioEntity> list = serviciosList.subList(1, 3);
			sedeServicioService.replaceServicios(0L, list);
		});    
    }

}