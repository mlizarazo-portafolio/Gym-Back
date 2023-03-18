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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.gym.entities.ReseniaEntity;
import co.edu.uniandes.dse.gym.entities.SedeEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.services.ReseniaSedeService;
import co.edu.uniandes.dse.gym.services.ReseniaService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ ReseniaService.class, ReseniaSedeService.class })

public class ReseniaSedeServiceTest {

    @Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ReseniaSedeService reseniaSedeService;

	@Autowired
	private ReseniaService reseniaService;

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
			ReseniaEntity resenias = factory.manufacturePojo(ReseniaEntity.class);
			entityManager.persist(resenias);
			reseniasList.add(resenias);
		}
		for (int i = 0; i < 3; i++) {
			SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
			entityManager.persist(entity);
			sedesList.add(entity);
			if (i == 0) {
				reseniasList.get(i).setSede(entity);
			}
		}
	}

    @Test
	void testReplaceSede() throws EntityNotFoundException {
		ReseniaEntity entity = reseniasList.get(0);
		reseniaSedeService.replaceSede(entity.getId(), sedesList.get(1).getId());
		entity = reseniaService.getReseniaById(entity.getId());
		assertEquals(entity.getSede(), sedesList.get(1));
	}

    @Test
	void testReplaceSedeInvalidResenia() {
		assertThrows(EntityNotFoundException.class, ()->{
			reseniaSedeService.replaceSede(0L, sedesList.get(1).getId());
		});
	}

    @Test
	void testReplaceInvalidSede() {
		assertThrows(EntityNotFoundException.class, ()->{
			ReseniaEntity entity = reseniasList.get(0);
			reseniaSedeService.replaceSede(entity.getId(), 0L);
		});
	}

    @Test
	void testRemoveSede() throws EntityNotFoundException {
		reseniaSedeService.removeSede(reseniasList.get(0).getId());
		ReseniaEntity response = reseniaService.getReseniaById(reseniasList.get(0).getId());
		assertNull(response.getSede());
	}

    @Test
	void testRemoveSedeInvalidResenia() throws EntityNotFoundException {
		assertThrows(EntityNotFoundException.class, ()->{
			reseniaSedeService.removeSede(0L);
		});
	}
    
}
