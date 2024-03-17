package co.edu.uniandes.dse.Vivienda.services;

import javax.transaction.Transactional;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import co.edu.uniandes.dse.Vivienda.entities.HabitanteEntity;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
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
            vivienda.getHabitantes_actuales().add(entity);
        }

    }

    @Test
    void addViviendaTest() throws EntityNotFoundException{
        HabitanteEntity newHabitante = factory.manufacturePojo(HabitanteEntity.class);
        newHabitante.setVivienda(vivienda);
        entityManager.persist(newHabitante);

        ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
        entityManager.persist(vivienda);

        habitanteViviendaService.addVivienda(vivienda.getId(), habitante.getId());

        assertTrue(habitante.getVivienda().equals(vivienda));


    }

    @Test
    void addViviendaInvalidTest() throws EntityNotFoundException{
        HabitanteEntity newHabitante = factory.manufacturePojo(HabitanteEntity.class);
        newHabitante.setVivienda(vivienda);
        entityManager.persist(newHabitante);

        ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
        entityManager.persist(vivienda);

        habitanteViviendaService.addVivienda(vivienda.getId(), habitante.getId());

        assertFalse(habitante.getVivienda().equals(0L));

    }

    @Test
    void addViviendaInvalidTest2(){
        assertThrows(EntityNotFoundException.class, () -> {
            ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
            entityManager.persist(vivienda);
            habitanteViviendaService.addVivienda(vivienda.getId(), 0L);
        });

    }

    @Test
    void getViviendaTest() throws EntityNotFoundException {
        ViviendaEntity vivienda = habitanteViviendaService.getVivienda(habitante.getId());
        assertTrue(vivienda.equals(habitante.getVivienda()));

    }

    @Test
    void removeViviendaTest() throws EntityNotFoundException{
        HabitanteEntity newHabitante = factory.manufacturePojo(HabitanteEntity.class);
        newHabitante.setVivienda(vivienda);
        entityManager.persist(newHabitante);

        habitanteViviendaService.removeVivienda(newHabitante.getId());
        assertFalse(newHabitante.getVivienda() == vivienda);
    }

    @Test
    void removeViviendaInvalidTest() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(EntityNotFoundException.class, () -> {
            HabitanteEntity habitante = factory.manufacturePojo(HabitanteEntity.class);
            entityManager.persist(habitante);
            habitante.setVivienda(null);
            habitanteViviendaService.removeVivienda(habitante.getId());
        });
    }

    @Test
    void updateViviendaTest() throws EntityNotFoundException{
        ViviendaEntity newVivienda = factory.manufacturePojo(ViviendaEntity.class);
        entityManager.persist(newVivienda);

        habitanteViviendaService.updateVivienda(habitante.getId(), newVivienda.getId());
        assertTrue(habitante.getVivienda().equals(newVivienda));
    }

    @Test
    void updateViviendaInvalidTest() throws EntityNotFoundException{
        ViviendaEntity newVivienda = factory.manufacturePojo(ViviendaEntity.class);
        entityManager.persist(newVivienda);

        habitanteViviendaService.updateVivienda(habitante.getId(), newVivienda.getId());
        assertFalse(habitante.getVivienda().equals(0L));
    }

    @Test
    void updateViviendaInvalidTest2(){
        assertThrows(EntityNotFoundException.class, () -> {
            ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
            entityManager.persist(vivienda);
            habitanteViviendaService.updateVivienda(0L, vivienda.getId());
        });

        

    }


    
}
