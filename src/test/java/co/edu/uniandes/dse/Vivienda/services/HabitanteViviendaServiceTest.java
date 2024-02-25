package co.edu.uniandes.dse.Vivienda.services;

import javax.transaction.Transactional;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import co.edu.uniandes.dse.Vivienda.services.HabitanteViviendaService;
import org.junit.jupiter.api.extension.ExtendWith;
import co.edu.uniandes.dse.Vivienda.entities.HabitanteEntity;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.Vivienda.repositories.ViviendaRepository;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import static org.junit.jupiter.api.Assertions.assertThrows;



@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(HabitanteViviendaService.class)
class HabitanteViviendaServiceTest {

    @Autowired
    private HabitanteViviendaService habitanteViviendaService;

    @Autowired
    private TestEntityManager entityManager;
    private PodamFactory factory = new PodamFactoryImpl();
    private HabitanteEntity habitante =  new HabitanteEntity();
    private ViviendaEntity vivienda = new ViviendaEntity();


    @BeforeEach
    public void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from HabitanteEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from ViviendaEntity").executeUpdate();
    }

    private void insertData() {
        vivienda = factory.manufacturePojo(ViviendaEntity.class);
        entityManager.persist(vivienda);

        habitante = factory.manufacturePojo(HabitanteEntity.class);
        habitante.setVivienda(vivienda);
        entityManager.persist(habitante);

        for (int i = 0; i < 3; i++) {
            HabitanteEntity entity = factory.manufacturePojo(HabitanteEntity.class);
            entityManager.persist(entity);
            entity.setVivienda(vivienda);
            vivienda.getHabitantes_acutales().add(entity);
        }


        

       
    }


    @Test
    void addHabitanteTest() throws EntityNotFoundException, IllegalOperationException {
        HabitanteEntity newHabitante = factory.manufacturePojo(HabitanteEntity.class);
        newHabitante.setVivienda(vivienda);
        entityManager.persist(newHabitante);

        ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
        entityManager.persist(vivienda);

        habitanteViviendaService.addHabitante(vivienda.getId(), newHabitante.getId());

        ViviendaEntity viviendaEntity = habitanteViviendaService.getVivienda(newHabitante.getId());
        assertTrue(viviendaEntity.getHabitantes_acutales().contains(newHabitante));


    }

    @Test
    void addHabitanteInvalidTest() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(EntityNotFoundException.class, () -> {
            ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
            entityManager.persist(vivienda);
            habitanteViviendaService.addHabitante(vivienda.getId(), 0L);
        });
    }

    @Test
    void removeHabitanteInvalidTest() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(EntityNotFoundException.class, () -> {
            ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
            entityManager.persist(vivienda);
            habitanteViviendaService.removeHabitante(vivienda.getId(), 0L);
        });
    }

    @Test
    void getViviendaTest() throws EntityNotFoundException {
        ViviendaEntity viviendaEntity = habitanteViviendaService.getVivienda(habitante.getId());
        assertFalse(viviendaEntity.getHabitantes_acutales().contains(habitante));

    }

    @Test
    void getHabitanteTest() throws EntityNotFoundException {
        HabitanteEntity habitanteEntity = habitanteViviendaService.getHabitante(vivienda.getId());
        assertTrue(vivienda.getHabitantes_acutales().contains(habitanteEntity));
    }

    @Test
    void replaceHabitanteTest() throws EntityNotFoundException, IllegalOperationException {
        HabitanteEntity newHabitante = factory.manufacturePojo(HabitanteEntity.class);
        newHabitante.setVivienda(vivienda);
        entityManager.persist(newHabitante);

        ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
        entityManager.persist(vivienda);

        habitanteViviendaService.addHabitante(vivienda.getId(), newHabitante.getId());

        habitanteViviendaService.replaceHabitante(vivienda.getId(), habitante.getId());

        ViviendaEntity viviendaEntity = habitanteViviendaService.getVivienda(habitante.getId());
        assertTrue(viviendaEntity.getHabitantes_acutales().contains(newHabitante));
        assertTrue(viviendaEntity.getHabitantes_acutales().contains(habitante));
    }

    @Test
    void replaceHabitanteInvalidTest() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(EntityNotFoundException.class, () -> {
            ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
            entityManager.persist(vivienda);
            habitanteViviendaService.replaceHabitante(vivienda.getId(), 0L);
        });
    }



    
}
