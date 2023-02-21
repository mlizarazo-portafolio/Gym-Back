package co.edu.uniandes.dse.gym.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.gym.entities.ServicioEntity;
import co.edu.uniandes.dse.gym.entities.SedeEntity;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(ServicioService.class)
public class ServicioServiceTest {
    @Autowired
    private ServicioService ServicioService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();
    
    private List<ServicioEntity> servicioList = new ArrayList<>();
    private List<SedeEntity> sedeList = new ArrayList<>();
    
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from ServicioEntity");
        entityManager.getEntityManager().createQuery("delete from SedeEntity");
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            SedeEntity sedeEntity = factory.manufacturePojo(SedeEntity.class);
            entityManager.persist(sedeEntity);
            sedeList.add(sedeEntity);
        }

        for (int i = 0; i < 3; i++) {
            ServicioEntity servicioEntity = factory.manufacturePojo(ServicioEntity.class);
            servicioEntity.setSede(sedeList.get(0));
            entityManager.persist(servicioEntity);
            servicioList.add(servicioEntity);
        }
    }

}