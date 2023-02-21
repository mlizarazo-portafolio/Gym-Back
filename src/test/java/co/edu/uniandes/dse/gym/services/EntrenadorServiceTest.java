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

import co.edu.uniandes.dse.gym.entities.EntrenadorEntity;
import co.edu.uniandes.dse.gym.entities.ActividadEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(EntrenadorService.class)

public class EntrenadorServiceTest {

    @Autowired
    private EntrenadorService entrenadorService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<EntrenadorEntity> entrenadorList = new ArrayList<>();

    private List<ActividadEntity> actividadList = new ArrayList<>();


    @BeforeEach
    void setUp(){
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from EntrenadorEntity");
        entityManager.getEntityManager().createQuery("delete from ActividadEntity");
	}

    private void insertData() {
        for (int i = 0; i < 3; i++) {
			ActividadEntity actividadEntity = factory.manufacturePojo(ActividadEntity.class);
			entityManager.persist(actividadEntity);
			actividadList.add(actividadEntity);
		}
        
		for (int i = 0; i < 3; i++) {
			EntrenadorEntity entrenadorEntity = factory.manufacturePojo(EntrenadorEntity.class);
			entityManager.persist(entrenadorEntity);
			entrenadorList.add(entrenadorEntity);
		}

        

        entrenadorList.get(0).setActividad(actividadList.get(0));
        actividadList.get(0).setEntrenador(entrenadorList.get(0));
		
	}

    @Test
    void testCreateEntrenador() throws EntityNotFoundException, IllegalOperationException{
        EntrenadorEntity newEntity = factory.manufacturePojo(EntrenadorEntity.class);
        newEntity.setNombre("Daniel Gomez Rey");
        newEntity.setFoto(":/imagen.jpg");
        newEntity.setTrayectoria("18 años viendo educacion fisica en el colegio");

		EntrenadorEntity result = entrenadorService.createEntrenador(newEntity);
		assertNotNull(result);

		EntrenadorEntity entity = entityManager.find(EntrenadorEntity.class, result.getId());
		assertEquals(newEntity.getId(), entity.getId());
		assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getFoto(), entity.getFoto());
        assertEquals(newEntity.getTrayectoria(), entity.getTrayectoria());
    }

    @Test
    void testCreateEntrenadorWithNoValidNombre(){
        assertThrows(IllegalOperationException.class, () ->{
            EntrenadorEntity newEntity = factory.manufacturePojo(EntrenadorEntity.class);
            newEntity.setNombre("");
            entrenadorService.createEntrenador(newEntity);
        });
    }

    @Test
    void testCreateEntrenadorWithNoValidNombre2(){
        assertThrows(IllegalOperationException.class, () ->{
            EntrenadorEntity newEntity = factory.manufacturePojo(EntrenadorEntity.class);
            newEntity.setNombre(null);
            entrenadorService.createEntrenador(newEntity);
        });
    }

    @Test
    void testCreateEntrenadorWithStoredName(){
        assertThrows(IllegalOperationException.class, () ->{
            EntrenadorEntity newEntity = factory.manufacturePojo(EntrenadorEntity.class);
            newEntity.setNombre(entrenadorList.get(0).getNombre());
            entrenadorService.createEntrenador(newEntity);
        });
    }

    @Test
    void testCreateEntrenadorWithNoValidFoto(){
        assertThrows(IllegalOperationException.class, () ->{
            EntrenadorEntity newEntity = factory.manufacturePojo(EntrenadorEntity.class);
            newEntity.setFoto("");
            entrenadorService.createEntrenador(newEntity);
        });
    }

    @Test
    void testCreateEntrenadorWithNoValidFoto2(){
        assertThrows(IllegalOperationException.class, () ->{
            EntrenadorEntity newEntity = factory.manufacturePojo(EntrenadorEntity.class);
            newEntity.setFoto(null);
            entrenadorService.createEntrenador(newEntity);
        });
    }

    @Test
    void testCreateEntrenadorWithNoValidTrayectoria(){
        assertThrows(IllegalOperationException.class, () ->{
            EntrenadorEntity newEntity = factory.manufacturePojo(EntrenadorEntity.class);
            newEntity.setTrayectoria("");
            entrenadorService.createEntrenador(newEntity);
        });
    }

    @Test
    void testCreateEntrenadorWithNoValidTrayectoria2(){
        assertThrows(IllegalOperationException.class, () ->{
            EntrenadorEntity newEntity = factory.manufacturePojo(EntrenadorEntity.class);
            newEntity.setTrayectoria(null);
            entrenadorService.createEntrenador(newEntity);
        });
    }

    @Test
    void testGetEntrenadores(){
        List<EntrenadorEntity> list = entrenadorService.getEntrenadores();
        assertEquals(entrenadorList.size(), list.size());
        for (EntrenadorEntity entity : list) {
            boolean found = false;
            for (EntrenadorEntity storedEntity : entrenadorList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }

    @Test
    void testGetEntrenadorById() throws EntityNotFoundException{
        EntrenadorEntity entity = entrenadorList.get(0);
        EntrenadorEntity resultEntity = entrenadorService.getEntrenadorById(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getFoto(), resultEntity.getFoto());
        assertEquals(entity.getTrayectoria(), resultEntity.getTrayectoria());
    }

    @Test
    void testGetInvalidEntrenador(){
        assertThrows(EntityNotFoundException.class, () ->{
            entrenadorService.getEntrenadorById(0L);
        });        
    }

    @Test
    void testUpdateEntrenador() throws EntityNotFoundException, IllegalOperationException{
        EntrenadorEntity entity = entrenadorList.get(0);
        EntrenadorEntity pojoEntity = factory.manufacturePojo(EntrenadorEntity.class);
        pojoEntity.setId(entity.getId());
        entrenadorService.updateEntrenador(entity.getId(), pojoEntity);

        EntrenadorEntity resp = entityManager.find(EntrenadorEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
        assertEquals(pojoEntity.getFoto(), resp.getFoto());
        assertEquals(pojoEntity.getTrayectoria(), resp.getTrayectoria());
    }

    @Test
    void testUpdateInvalidEntrenador(){
        assertThrows(EntityNotFoundException.class, () ->{
            EntrenadorEntity pojoEntity = factory.manufacturePojo(EntrenadorEntity.class);
            pojoEntity.setId(0L);
            entrenadorService.updateEntrenador(0L, pojoEntity);
        });        
    }

    @Test
    void testUpdateEntrenadorWithNoValidNombre() {
        assertThrows(IllegalOperationException.class, () -> {
                EntrenadorEntity entity = entrenadorList.get(0);
                EntrenadorEntity pojoEntity = factory.manufacturePojo(EntrenadorEntity.class);
                pojoEntity.setNombre("");
                pojoEntity.setId(entity.getId());
                entrenadorService.updateEntrenador(entity.getId(), pojoEntity);
        });
    }

    @Test
    void testUpdateEntrenadorWithNoValidNombre2() {
        assertThrows(IllegalOperationException.class, () -> {
                EntrenadorEntity entity = entrenadorList.get(0);
                EntrenadorEntity pojoEntity = factory.manufacturePojo(EntrenadorEntity.class);
                pojoEntity.setNombre(null);
                pojoEntity.setId(entity.getId());
                entrenadorService.updateEntrenador(entity.getId(), pojoEntity);
        });
    }

    @Test
    void testUpdateEntrenadorWithStoredNombre() {
        assertThrows(IllegalOperationException.class, () -> {
                EntrenadorEntity entity = entrenadorList.get(0);
                EntrenadorEntity pojoEntity = factory.manufacturePojo(EntrenadorEntity.class);
                pojoEntity.setNombre(entity.getNombre());
                pojoEntity.setId(entity.getId());
                entrenadorService.updateEntrenador(entity.getId(), pojoEntity);
        });
    } 
    
    @Test
    void testUpdateEntrenadorWithNoValidFoto() {
        assertThrows(IllegalOperationException.class, () -> {
                EntrenadorEntity entity = entrenadorList.get(0);
                EntrenadorEntity pojoEntity = factory.manufacturePojo(EntrenadorEntity.class);
                pojoEntity.setFoto("");
                pojoEntity.setId(entity.getId());
                entrenadorService.updateEntrenador(entity.getId(), pojoEntity);
        });
    }

    @Test
    void testUpdateEntrenadorWithNoValidFoto2() {
        assertThrows(IllegalOperationException.class, () -> {
                EntrenadorEntity entity = entrenadorList.get(0);
                EntrenadorEntity pojoEntity = factory.manufacturePojo(EntrenadorEntity.class);
                pojoEntity.setFoto(null);
                pojoEntity.setId(entity.getId());
                entrenadorService.updateEntrenador(entity.getId(), pojoEntity);
        });
    }

    @Test
    void testUpdateEntrenadorWithNoValidTrayectoria() {
        assertThrows(IllegalOperationException.class, () -> {
                EntrenadorEntity entity = entrenadorList.get(0);
                EntrenadorEntity pojoEntity = factory.manufacturePojo(EntrenadorEntity.class);
                pojoEntity.setTrayectoria("");
                pojoEntity.setId(entity.getId());
                entrenadorService.updateEntrenador(entity.getId(), pojoEntity);
        });
    }

    @Test
    void testUpdateEntrenadorWithNoValidTrayectoria2() {
        assertThrows(IllegalOperationException.class, () -> {
                EntrenadorEntity entity = entrenadorList.get(0);
                EntrenadorEntity pojoEntity = factory.manufacturePojo(EntrenadorEntity.class);
                pojoEntity.setTrayectoria(null);
                pojoEntity.setId(entity.getId());
                entrenadorService.updateEntrenador(entity.getId(), pojoEntity);
        });
    }

    @Test
    void testDeleteEntrenador() throws EntityNotFoundException, IllegalOperationException {
            EntrenadorEntity entity = entrenadorList.get(1);
            entrenadorService.deleteEntrenador(entity.getId());
            EntrenadorEntity deleted = entityManager.find(EntrenadorEntity.class, entity.getId());
            assertNull(deleted);
    }

    @Test
    void testDeleteInvalidEntrenador(){
        assertThrows(EntityNotFoundException.class, () ->{
            entrenadorService.deleteEntrenador(0L);
        });        
    }

}
