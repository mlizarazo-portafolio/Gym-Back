package co.edu.uniandes.dse.gym.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import co.edu.uniandes.dse.gym.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.gym.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.gym.entities.ActividadEntity;
import co.edu.uniandes.dse.gym.entities.EntrenadorEntity;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(ActividadService.class)
class ActividadServiceTest {
    
    @Autowired
    private ActividadService actividadService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<ActividadEntity> actividadList = new ArrayList<>();
    private List<EntrenadorEntity> entrenadorList = new ArrayList<>();
    
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
    private void clearData() 
    {
            entityManager.getEntityManager().createQuery("delete from ActividadEntity");
            entityManager.getEntityManager().createQuery("delete from EntrenadorEntity");
    }
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     */
    private void insertData()
    {
        for(int i = 0; i<3; i++)
        {
            EntrenadorEntity entrenadorEntity = factory.manufacturePojo(EntrenadorEntity.class);
            entityManager.persist(entrenadorEntity);
            entrenadorList.add(entrenadorEntity);
        }

        for(int i=0; i<3; i++)
        {
            ActividadEntity actividadEntity = factory.manufacturePojo(ActividadEntity.class);
            actividadEntity.setEntrenador(entrenadorList.get(0));
            entityManager.persist(actividadEntity);
            actividadList.add(actividadEntity);

        }

    }

    @Test
    void testCreateActividad() throws EntityNotFoundException, IllegalOperationException
    {
        ActividadEntity newEntity = factory.manufacturePojo(ActividadEntity.class);
        newEntity.setEntrenador(entrenadorList.get(0));
        ActividadEntity result = actividadService.createActividad(newEntity);
        ActividadEntity entity = entityManager.find(ActividadEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getEntrenador(), entity.getEntrenador());
        assertEquals(newEntity.getTipo(), entity.getTipo());
        assertEquals(newEntity.getAtletasInscritos(), entity.getAtletasInscritos());
        assertEquals(newEntity.getMaxParticipantes(), entity.getMaxParticipantes());
        assertEquals(newEntity.getRestriccion(), entity.getRestriccion());
        
    }

    @Test
    void testGetActividades()
    {
        List<ActividadEntity> list = actividadService.getActividades();
        assertEquals(actividadList.size(), list.size());
        for(ActividadEntity entity: list)
        {
            boolean found = false;
            for(ActividadEntity storedEntity: actividadList)
            {
                if(entity.getId().equals(storedEntity.getId()))
                {
                    found = true;
                }
                
            }
            assertTrue(found);
        }
    }

    @Test
    void testGetActividad() throws EntityNotFoundException
    {
        ActividadEntity entity = actividadList.get(0);
        ActividadEntity resultEntity = actividadService.getActividad(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getEntrenador(), resultEntity.getEntrenador());
        assertEquals(entity.getTipo(), resultEntity.getTipo());
        assertEquals(entity.getAtletasInscritos(), resultEntity.getAtletasInscritos());
        assertEquals(entity.getMaxParticipantes(), resultEntity.getMaxParticipantes());
        assertEquals(entity.getRestriccion(), resultEntity.getRestriccion());

    }

    @Test
    void testGetInvalidActividad()
    {
        assertThrows(EntityNotFoundException.class,()->{
            actividadService.getActividad(0L);
        });
    }

    @Test
    void testUpdateActividad()throws EntityNotFoundException
    {
        ActividadEntity entity = actividadList.get(0);
        ActividadEntity pojoEntity = factory.manufacturePojo(ActividadEntity.class);
        pojoEntity.setId(entity.getId());
        actividadService.updateActividad(entity.getId(), pojoEntity);

        ActividadEntity resp = entityManager.find(ActividadEntity.class, entity.getId());

        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
        assertEquals(pojoEntity.getEntrenador(), resp.getEntrenador());
        assertEquals(pojoEntity.getTipo(), resp.getTipo());
        assertEquals(pojoEntity.getAtletasInscritos(), resp.getAtletasInscritos());
        assertEquals(pojoEntity.getMaxParticipantes(), resp.getMaxParticipantes());
        assertEquals(pojoEntity.getRestriccion(), resp.getRestriccion());
    }

    @Test
    void testDeleteActividad() throws EntityNotFoundException
    {
        ActividadEntity entity = actividadList.get(1);
        actividadService.deleteActividad(entity.getId());
        ActividadEntity deleted = entityManager.find(ActividadEntity.class, entity.getId());
        assertNull(deleted);
    }

    

}
