package co.edu.uniandes.dse.gym.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.gym.entities.ReseniaEntity;
import co.edu.uniandes.dse.gym.entities.SedeEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.services.SedeReseniaService;
import co.edu.uniandes.dse.gym.services.SedeService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ SedeService.class, SedeReseniaService.class })
public class SedeReseniaServiceTest {

    @Autowired
	private SedeReseniaService sedeReseniaService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<SedeEntity> sedesList = new ArrayList<>();
	private List<ReseniaEntity> reseniasList = new ArrayList<>();

    @BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

    private void clearData() {
		entityManager.getEntityManager().createQuery("delete from ReseniaEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from SedeEntity").executeUpdate();
	}

    private void insertData() {
		for (int i = 0; i < 3; i++) {
			ReseniaEntity resenia = factory.manufacturePojo(ReseniaEntity.class);
			entityManager.persist(resenia);
			reseniasList.add(resenia);
		}

		for (int i = 0; i < 3; i++) {
			SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
			entityManager.persist(entity);
			sedesList.add(entity);
			if (i == 0) {
				reseniasList.get(i).setSede(entity);
				entity.getResenias().add(reseniasList.get(i));
			}
		}
	}

    @Test
	void testAddResenia() throws EntityNotFoundException {
		SedeEntity entity = sedesList.get(0);
		ReseniaEntity reseniaEntity = reseniasList.get(1);
		ReseniaEntity response = sedeReseniaService.addResenia(reseniaEntity.getId(), entity.getId());

		assertNotNull(response);
		assertEquals(reseniaEntity.getId(), response.getId());
	}
    
    @Test
	void testAddInvalidResenia() {
		assertThrows(EntityNotFoundException.class, ()->{
			SedeEntity entity = sedesList.get(0);
			sedeReseniaService.addResenia(0L, entity.getId());
		});
	}

    @Test
	void testAddReseniaInvalidSede() {
		assertThrows(EntityNotFoundException.class, ()->{
			ReseniaEntity reseniaEntity = reseniasList.get(1);
			sedeReseniaService.addResenia(reseniaEntity.getId(), 0L);
		});
	}

    @Test
	void testGetResenias() throws EntityNotFoundException {
		List<ReseniaEntity> list = sedeReseniaService.getResenias(sedesList.get(0).getId());
		assertEquals(1, list.size());
	}

    @Test
	void testGetReseniasInvalidSede() {
		assertThrows(EntityNotFoundException.class,()->{
			sedeReseniaService.getResenias(0L);
		});
	}

    @Test
	void testGetResenia() throws EntityNotFoundException, IllegalOperationException {
		SedeEntity entity = sedesList.get(0);
		ReseniaEntity reseniaEntity = reseniasList.get(0);
		ReseniaEntity response = sedeReseniaService.getResenia(entity.getId(), reseniaEntity.getId());

		assertEquals(reseniaEntity.getId(), response.getId());
		assertEquals(reseniaEntity.getUsuario(), response.getUsuario());
		assertEquals(reseniaEntity.getPuntuacion(), response.getPuntuacion());
		assertEquals(reseniaEntity.getComentario(), response.getComentario());
	}

    @Test
	void testGetReseniaInvalidSede()  {
		assertThrows(EntityNotFoundException.class, ()->{
			ReseniaEntity reseniaEntity = reseniasList.get(0);
			sedeReseniaService.getResenia(0L, reseniaEntity.getId());
		});
	}

    @Test
	void testGetInvalidResenia()  {
		assertThrows(EntityNotFoundException.class, ()->{
			SedeEntity entity = sedesList.get(0);
			sedeReseniaService.getResenia(entity.getId(), 0L);
		});
	}

    @Test
	public void getReseniaNoAsociadaTest() {
		assertThrows(IllegalOperationException.class, () -> {
			SedeEntity entity = sedesList.get(0);
			ReseniaEntity reseniaEntity = reseniasList.get(1);
			sedeReseniaService.getResenia(entity.getId(), reseniaEntity.getId());
		});
	}

    @Test
	void testReplaceResenias() throws EntityNotFoundException {
		SedeEntity entity = sedesList.get(0);
		List<ReseniaEntity> list = reseniasList.subList(1, 3);
		sedeReseniaService.replaceResenias(entity.getId(), list);

		for (ReseniaEntity resenia : list) {
			ReseniaEntity r = entityManager.find(ReseniaEntity.class, resenia.getId());
			assertTrue(r.getSede().equals(entity));
		}
	}

    @Test
	void testReplaceInvalidResenias() {
		assertThrows(EntityNotFoundException.class, ()->{
			SedeEntity entity = sedesList.get(0);
			
			List<ReseniaEntity> resenias = new ArrayList<>();
			ReseniaEntity newResenia = factory.manufacturePojo(ReseniaEntity.class);
			newResenia.setId(0L);
			resenias.add(newResenia);
			
			sedeReseniaService.replaceResenias(entity.getId(), resenias);
		});
	}

    @Test
	void testReplaceReseniasInvalidSede() throws EntityNotFoundException {
		assertThrows(EntityNotFoundException.class, ()->{
			List<ReseniaEntity> list = reseniasList.subList(1, 3);
			sedeReseniaService.replaceResenias(0L, list);
		});
	}
}
