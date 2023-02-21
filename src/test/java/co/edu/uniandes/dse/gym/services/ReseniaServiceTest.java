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

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import co.edu.uniandes.dse.gym.entities.ReseniaEntity;
import co.edu.uniandes.dse.gym.entities.SedeEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(ReseniaService.class)
public class ReseniaServiceTest {

    @Autowired
    private ReseniaService reseniaService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<ReseniaEntity> reseniaList = new ArrayList<>();
    private List<SedeEntity> sedeList = new ArrayList<>();

    @BeforeEach
    void setUp(){
        clearData();
        insertData();
    }

    private void clearData(){
        entityManager.getEntityManager().createQuery("delete from ReseniaEntity");
		entityManager.getEntityManager().createQuery("delete from SedeEntity");
    }

    private void insertData(){
        for (int i = 0; i < 3; i++) {
			SedeEntity sedeEntity = factory.manufacturePojo(SedeEntity.class);
			entityManager.persist(sedeEntity);
			sedeList.add(sedeEntity);
		}

		for (int i = 0; i < 3; i++) {
			ReseniaEntity reseniaEntity = factory.manufacturePojo(ReseniaEntity.class);
			reseniaEntity.setSede(sedeList.get(0));
			entityManager.persist(reseniaEntity);
			reseniaList.add(reseniaEntity);
		}

        reseniaList.get(0).setSede(sedeList.get(0));
        sedeList.get(0).getResenias().add(reseniaList.get(0));
    }

    @Test
    void testCreateResenia() throws EntityNotFoundException, IllegalOperationException{
        ReseniaEntity newEntity = factory.manufacturePojo(ReseniaEntity.class);
        newEntity.setSede(sedeList.get(0));
        newEntity.setUsuario("dgomezrey");
        newEntity.setPuntuacion(4);
        newEntity.setComentario("Excelente servicio");
        ReseniaEntity result = reseniaService.createResenia(newEntity);
        assertNotNull(result);

        ReseniaEntity entity = entityManager.find(ReseniaEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getUsuario(), entity.getUsuario());
        assertEquals(newEntity.getPuntuacion(), entity.getPuntuacion());
        assertEquals(newEntity.getComentario(), entity.getComentario());
    }
    
    @Test
    void testCreateReseniaWithNoValidUsuario(){
        assertThrows(IllegalOperationException.class, () ->{
            ReseniaEntity newEntity = factory.manufacturePojo(ReseniaEntity.class);
            newEntity.setUsuario("");
            reseniaService.createResenia(newEntity);
        });
    }

    @Test
    void testCreateReseniaWithNoValidUsuario2(){
        assertThrows(IllegalOperationException.class, () ->{
            ReseniaEntity newEntity = factory.manufacturePojo(ReseniaEntity.class);
            newEntity.setUsuario(null);
            reseniaService.createResenia(newEntity);
        });
    }

    @Test
    void testCreateReseniaWithNoValidPuntuacion(){
        assertThrows(IllegalOperationException.class, () ->{
            ReseniaEntity newEntity = factory.manufacturePojo(ReseniaEntity.class);
            newEntity.setPuntuacion(6);
            reseniaService.createResenia(newEntity);
        });
    }

    @Test
    void testCreateReseniaWithNoValidPuntuacion2(){
        assertThrows(IllegalOperationException.class, () ->{
            ReseniaEntity newEntity = factory.manufacturePojo(ReseniaEntity.class);
            newEntity.setPuntuacion(null);
            reseniaService.createResenia(newEntity);
        });
    }

    @Test
    void testCreateReseniaWithNoValidComentario(){
        assertThrows(IllegalOperationException.class, () ->{
            ReseniaEntity newEntity = factory.manufacturePojo(ReseniaEntity.class);
            newEntity.setComentario("");
            reseniaService.createResenia(newEntity);
        });
    }

    @Test
    void testCreateReseniaWithNoValidComentario2(){
        assertThrows(IllegalOperationException.class, () ->{
            ReseniaEntity newEntity = factory.manufacturePojo(ReseniaEntity.class);
            newEntity.setComentario(null);
            reseniaService.createResenia(newEntity);
        });
    }

    @Test
    void testCreateReseniaWithInvalidSede() {
        assertThrows(IllegalOperationException.class, () -> {
                ReseniaEntity newEntity = factory.manufacturePojo(ReseniaEntity.class);
                newEntity.setUsuario("dgomezrey2");
                SedeEntity sedeEntity = new SedeEntity();
                sedeEntity.setId(0L);
                newEntity.setSede(sedeEntity);
                reseniaService.createResenia(newEntity);
        });
    }

    @Test
    void testCreateReseniaWithNullSede() {
            assertThrows(IllegalOperationException.class, () -> {
                    ReseniaEntity newEntity = factory.manufacturePojo(ReseniaEntity.class);
                    newEntity.setUsuario("dgomezrey3");
                    newEntity.setSede(null);
                    reseniaService.createResenia(newEntity);
            });
    }
    
    @Test
    void testGetResenias(){
        List<ReseniaEntity> list = reseniaService.getResenias();
        assertEquals(reseniaList.size(), list.size());
        for (ReseniaEntity entity : list) {
            boolean found = false;
            for (ReseniaEntity storedEntity : reseniaList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }

    @Test
    void testGetReseniaById() throws EntityNotFoundException{
        ReseniaEntity entity = reseniaList.get(0);
        ReseniaEntity resultEntity = reseniaService.getReseniaById(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getUsuario(), resultEntity.getUsuario());
        assertEquals(entity.getPuntuacion(), resultEntity.getPuntuacion());
        assertEquals(entity.getComentario(), resultEntity.getComentario());
    }

    @Test
    void testGetInvalidResenia(){
        assertThrows(EntityNotFoundException.class, () ->{
            reseniaService.getReseniaById(0L);
        });        
    }

    @Test
    void testUpdateResenia() throws EntityNotFoundException, IllegalOperationException{
        ReseniaEntity entity = reseniaList.get(0);
        ReseniaEntity pojoEntity = factory.manufacturePojo(ReseniaEntity.class);
        pojoEntity.setId(entity.getId());
        pojoEntity.setPuntuacion(3);
        reseniaService.updateResenia(entity.getId(), pojoEntity);

        ReseniaEntity resp = entityManager.find(ReseniaEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getUsuario(), resp.getUsuario());
        assertEquals(pojoEntity.getPuntuacion(), resp.getPuntuacion());
        assertEquals(pojoEntity.getComentario(), resp.getComentario());
    }

    @Test
    void testUpdateInvalidResenia(){
        assertThrows(EntityNotFoundException.class, () ->{
            ReseniaEntity pojoEntity = factory.manufacturePojo(ReseniaEntity.class);
            pojoEntity.setId(0L);
            reseniaService.updateResenia(0L, pojoEntity);
        });        
    }



    

    @Test
    void testDeleteResenia() throws EntityNotFoundException, IllegalOperationException {
            ReseniaEntity entity = reseniaList.get(1);
            reseniaService.deleteResenia(entity.getId());
            ReseniaEntity deleted = entityManager.find(ReseniaEntity.class, entity.getId());
            assertNull(deleted);
    }

    @Test
    void testDeleteInvalidResenia(){
        assertThrows(EntityNotFoundException.class, () ->{
            reseniaService.deleteResenia(0L);
        });        
    }

    
}
