package co.edu.uniandes.dse.gym.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import co.edu.uniandes.dse.gym.entities.SedeEntity;
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(AtletaService.class)
class AtletaServiceTest {

    @Autowired
    private AtletaService atletaService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<AtletaEntity> atletaList = new ArrayList<>();
    private List<DeportologoEntity> deportologoList = new ArrayList<>();
    private List<SedeEntity> sedeList = new ArrayList<>();

    /**
     * Configuración inicial de la prueba.
     */
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from AtletaEntity");
        entityManager.getEntityManager().createQuery("delete from DeportologoEntity");
        entityManager.getEntityManager().createQuery("delete from SedeEntity");
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            DeportologoEntity deportologoEntity = factory.manufacturePojo(DeportologoEntity.class);
            entityManager.persist(deportologoEntity);
            deportologoList.add(deportologoEntity);
        }
        for (int i = 0; i < 3; i++) {
            SedeEntity sedeEntity = factory.manufacturePojo(SedeEntity.class);
            entityManager.persist(sedeEntity);
            sedeList.add(sedeEntity);
        }

        for (int i = 0; i < 3; i++) {
            AtletaEntity atletaEntity = factory.manufacturePojo(AtletaEntity.class);
            atletaEntity.setDeportologo(deportologoList.get(0));
            atletaEntity.setSede(sedeList.get(0));
            entityManager.persist(atletaEntity);
            atletaList.add(atletaEntity);

        }

    }

    @Test
    void testCreateAtleta() throws EntityNotFoundException, IllegalOperationException {
        AtletaEntity newEntity = factory.manufacturePojo(AtletaEntity.class);
        newEntity.setDeportologo(deportologoList.get(0));
        newEntity.setSede(sedeList.get(0));
        AtletaEntity result = atletaService.createAtleta(newEntity);
        AtletaEntity entity = entityManager.find(AtletaEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getDeportologo(), entity.getDeportologo());
        assertEquals(newEntity.getSede(), entity.getSede());
        assertEquals(newEntity.getTipoSangre(), entity.getTipoSangre());
        assertEquals(newEntity.getFechaNacimiento(), entity.getFechaNacimiento());
        assertEquals(newEntity.getAltura(), entity.getAltura());
        assertEquals(newEntity.getPeso(), entity.getPeso());
    }

    @Test
    void testGetAtleta() throws EntityNotFoundException {
        AtletaEntity entity = atletaList.get(0);
        AtletaEntity resultEntity = atletaService.getAtleta(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getDeportologo(), resultEntity.getDeportologo());
        assertEquals(entity.getSede(), resultEntity.getSede());
        assertEquals(entity.getTipoSangre(), resultEntity.getTipoSangre());
        assertEquals(entity.getFechaNacimiento(), resultEntity.getFechaNacimiento());
        assertEquals(entity.getAltura(), resultEntity.getAltura());
        assertEquals(entity.getPeso(), resultEntity.getPeso());
    }

    @Test
    void testGetAtletas() {
        List<AtletaEntity> list = atletaService.getAtletas();
        assertEquals(atletaList.size(), list.size());
        for (AtletaEntity entity : list) {
            boolean found = false;
            for (AtletaEntity storedEntity : atletaList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }

    @Test
    void testUpdateAtleta() throws EntityNotFoundException, IllegalOperationException {
        AtletaEntity entity = atletaList.get(0);
        AtletaEntity pojoEntity = factory.manufacturePojo(AtletaEntity.class);
        pojoEntity.setId(entity.getId());
        pojoEntity.setDeportologo(deportologoList.get(0));
        pojoEntity.setSede(sedeList.get(0));
        atletaService.updateAtleta(pojoEntity);
        AtletaEntity resp = entityManager.find(AtletaEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
        assertEquals(pojoEntity.getDeportologo(), resp.getDeportologo());
        assertEquals(pojoEntity.getSede(), resp.getSede());
        assertEquals(pojoEntity.getTipoSangre(), resp.getTipoSangre());
        assertEquals(pojoEntity.getFechaNacimiento(), resp.getFechaNacimiento());
        assertEquals(pojoEntity.getAltura(), resp.getAltura());
        assertEquals(pojoEntity.getPeso(), resp.getPeso());
    }

    @Test
    void testDeleteAtleta() throws EntityNotFoundException {
        AtletaEntity entity = atletaList.get(0);
        atletaService.deleteAtleta(entity.getId());
        AtletaEntity deleted = entityManager.find(AtletaEntity.class, entity.getId());
        assertNull(deleted);
    }

    @Test
    void testCreateAtletaWithSameName() {
        AtletaEntity newEntity = factory.manufacturePojo(AtletaEntity.class);
        newEntity.setDeportologo(deportologoList.get(0));
        newEntity.setSede(sedeList.get(0));
        newEntity.setNombre(atletaList.get(0).getNombre());
        assertThrows(IllegalOperationException.class, () -> {
            atletaService.createAtleta(newEntity);
        });
    }

}
