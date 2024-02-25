package co.edu.uniandes.dse.Vivienda.services;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

import co.edu.uniandes.dse.Vivienda.entities.HabitanteEntity;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(ViviendaHabitanteService.class)
public class ViviendaHabitanteServiceTest {
    @Autowired
    private ViviendaHabitanteService viviendaHabitanteService;

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
    void addHabitanteTest() throws EntityNotFoundException, IllegalOperationException {
        HabitanteEntity newHabitante = factory.manufacturePojo(HabitanteEntity.class);
        newHabitante.setVivienda(vivienda);
        entityManager.persist(newHabitante);

        ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
        entityManager.persist(vivienda);

        viviendaHabitanteService.addHabitante(vivienda.getId(), newHabitante.getId());
        
        assertNotEquals(vivienda.getHabitantes_actuales(), null);

    }

    @Test
    void addHabitanteInvalidTest() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(EntityNotFoundException.class, () -> {
            ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
            entityManager.persist(vivienda);
            viviendaHabitanteService.addHabitante(vivienda.getId(), 0L);
        });
    }

    @Test
    void removeHabitanteInvalidTest() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(EntityNotFoundException.class, () -> {
            ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
            entityManager.persist(vivienda);
            viviendaHabitanteService.removeHabitante(vivienda.getId(), 0L);
        });
    }

    @Test
    void replaceHabitanteTest() throws EntityNotFoundException, IllegalOperationException {
        HabitanteEntity newHabitante = factory.manufacturePojo(HabitanteEntity.class);
        newHabitante.setVivienda(vivienda);
        entityManager.persist(newHabitante);

        ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
        entityManager.persist(vivienda);

        viviendaHabitanteService.addHabitante(vivienda.getId(), newHabitante.getId());

        viviendaHabitanteService.replaceHabitante(vivienda.getId(), habitante.getId());

        assertTrue(vivienda.getHabitantes_actuales().contains(newHabitante));
        assertTrue(vivienda.getHabitantes_actuales().contains(habitante));
    }

    @Test
    void replaceHabitanteInvalidTest() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(EntityNotFoundException.class, () -> {
            ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
            entityManager.persist(vivienda);
            viviendaHabitanteService.replaceHabitante(vivienda.getId(), 0L);
        });
    }

}
