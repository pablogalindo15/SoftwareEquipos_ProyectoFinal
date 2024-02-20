package co.edu.uniandes.dse.Vivienda.services;

import javax.transaction.Transactional;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import lombok.Data;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import co.edu.uniandes.dse.Vivienda.entities.HabitanteEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.Vivienda.repositories.HabitanteRepository;
import lombok.extern.slf4j.Slf4j;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;




@DataJpaTest
@Transactional
@Import(HabitanteService.class)
class HabitanteServiceTest {
    @Autowired
    private HabitanteService habitanteService;

    @Autowired
    private TestEntityManager entityManager;

    private List<HabitanteEntity> habitanteList = new ArrayList<>();
    private PodamFactory factory = new PodamFactoryImpl();
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
        entityManager.getEntityManager().createQuery("delete from HabitanteEntity");
        }
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     */
    private void insertData() { 
        for (int i = 0; i < 3; i++) {
            HabitanteEntity habitante = factory.manufacturePojo(HabitanteEntity.class);
            entityManager.persist(habitante);
            habitanteList.add(habitante);
        }
    
      
        
    }
    @Test
    void createHabitanteTest() throws EntityNotFoundException, IllegalOperationException {
        HabitanteEntity newEntity = factory.manufacturePojo(HabitanteEntity.class);
        HabitanteEntity result = habitanteService.createHabitante(newEntity);
        assertNotNull(result);
        HabitanteEntity entity = entityManager.find(HabitanteEntity.class, result.getId());
        assertNotNull(entity);
        assertEquals(newEntity.getId(), entity.getId());
    }
    @Test
    void testCreateHabitanteWithNoValidName() {
        assertThrows(IllegalOperationException.class, () -> {
            HabitanteEntity newEntity = factory.manufacturePojo(HabitanteEntity.class);
            newEntity.setNombre("");
            habitanteService.createHabitante(newEntity);
        });
    }
    @Test
    void testCreateHabitanteWithNoValidName2() {
        assertThrows(IllegalOperationException.class, () -> {
            HabitanteEntity newEntity = factory.manufacturePojo(HabitanteEntity.class);
            newEntity.setNombre(null);
            habitanteService.createHabitante(newEntity);
        });
    }
    @Test
    void testCreateHabitanteWithStoredId() {
        assertThrows(IllegalOperationException.class, () -> {
            HabitanteEntity newEntity = factory.manufacturePojo(HabitanteEntity.class);
            newEntity.setId(habitanteList.get(0).getId());
            habitanteService.createHabitante(newEntity);
        });
    }
   

    
    @Test
    void testGetHabitante() throws EntityNotFoundException {
        HabitanteEntity entity = habitanteList.get(0);
        HabitanteEntity resultEntity = habitanteService.getHabitante(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
    }
    @Test
    void testGetInvalidHabitante() {
        assertThrows(EntityNotFoundException.class, () -> {
            habitanteService.getHabitante(0L);
        });
    }
    @Test
    void testUpdateHabitante() throws EntityNotFoundException, IllegalOperationException {
        HabitanteEntity entity = habitanteList.get(0);
        HabitanteEntity pojoEntity = factory.manufacturePojo(HabitanteEntity.class);
        pojoEntity.setId(entity.getId());
        habitanteService.updateHabitante(pojoEntity.getId(), pojoEntity);
        HabitanteEntity resp = entityManager.find(HabitanteEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
    }
    @Test
    void testUpdateHabitanteInvalid() {
        assertThrows(IllegalOperationException.class, () -> {
            HabitanteEntity pojoEntity = factory.manufacturePojo(HabitanteEntity.class);
            pojoEntity.setId(0L);
            habitanteService.updateHabitante(0L, pojoEntity);
        });
    }
    @Test
    void testUpdateHabitanteWithNoValidName() {
        assertThrows(IllegalOperationException.class, () -> {
            HabitanteEntity entity = habitanteList.get(0);
            HabitanteEntity pojoEntity = factory.manufacturePojo(HabitanteEntity.class);
            pojoEntity.setNombre("");
            pojoEntity.setId(entity.getId());
            habitanteService.updateHabitante(pojoEntity.getId(), pojoEntity);
        });
    }
    @Test
    void testUpdateHabitanteWithNoValidName2() {
        assertThrows(IllegalOperationException.class, () -> {
            HabitanteEntity entity = habitanteList.get(0);
            HabitanteEntity pojoEntity = factory.manufacturePojo(HabitanteEntity.class);
            pojoEntity.setNombre(null);
            pojoEntity.setId(entity.getId());
            habitanteService.updateHabitante(pojoEntity.getId(), pojoEntity);
        });
    }
    @Test
    void testDeleteHabitante() throws EntityNotFoundException {
        HabitanteEntity entity = habitanteList.get(1);
        habitanteService.deleteHabitante(entity.getId());
        HabitanteEntity deleted = entityManager.find(HabitanteEntity.class, entity.getId());
        assertNull(deleted);
    }
    @Test
    void testDeleteHabitanteInvalid() {
        assertThrows(EntityNotFoundException.class, () -> {
            habitanteService.deleteHabitante(0L);
        });
    }



}