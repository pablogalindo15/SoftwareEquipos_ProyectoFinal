package co.edu.uniandes.dse.Vivienda.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.util.ArrayList;
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

import co.edu.uniandes.dse.Vivienda.entities.LugarEntity;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(LugarService.class)
class LugarServiceTest {
    @Autowired
    private LugarService lugarService;

    @Autowired
    private TestEntityManager entityManager;

    //private LugarEntity LugarPrueba= new LugarEntity();
    private List<LugarEntity> lugarList = new ArrayList<>();
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
        entityManager.getEntityManager().createQuery("delete from LugarEntity");
        }

    /**
    * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
    */
    private void insertData() { 
        for (int i = 0; i < 3; i++) {
            LugarEntity lugarEntity = factory.manufacturePojo(LugarEntity.class);
            entityManager.persist(lugarEntity);
            lugarList.add(lugarEntity);
                }
        
        ViviendaEntity viviendaEntity = factory.manufacturePojo(ViviendaEntity.class);
            entityManager.persist(viviendaEntity);
            viviendaEntity.getLugarDeInteres_cercano().add(lugarList.get(0));
            lugarList.get(0).getViviendas_cercanas().add(viviendaEntity);}
    
        @Test
        void testCreateLugar()throws EntityNotFoundException, IllegalOperationException{
                LugarEntity newEntity = factory.manufacturePojo(LugarEntity.class);
                newEntity.setNombre("lugar1");
                LugarEntity result = lugarService.createLugar(newEntity);
                assertNotNull(result);
                LugarEntity entity = entityManager.find(LugarEntity.class, result.getId());
                assertEquals(newEntity, result);
                assertEquals(newEntity.getNombre(), entity.getNombre());
                assertEquals(newEntity.getCoordenadaX(), entity.getCoordenadaX());
                assertEquals(newEntity.getCoordenadaY(), entity.getCoordenadaY());
                assertEquals(newEntity.getFotos(), entity.getFotos());
                assertEquals(newEntity.getPrecioMax(), entity.getPrecioMax());
                assertEquals(newEntity.getPrecioMin(), entity.getPrecioMin());
                assertEquals(newEntity.getTiempoLlegada(), entity.getTiempoLlegada());
                
            }

    @Test   
    void testCreateLugarWithNoValidNombre() {
        assertThrows(IllegalOperationException.class, () -> {
                LugarEntity newEntity = factory.manufacturePojo(LugarEntity.class);
                newEntity.setNombre("");
                lugarService.createLugar(newEntity);
        });
    }   
    @Test
    void testCreateLugarWithNoValidNombre2() {
        assertThrows(IllegalOperationException.class, () -> {
                LugarEntity newEntity = factory.manufacturePojo(LugarEntity.class);
                newEntity.setNombre(null);
                lugarService.createLugar(newEntity);
        });
    }

  
    @Test
    void testGetLugares() {   
       List<LugarEntity> list = (List<LugarEntity>)lugarService.getLugares();
        assertEquals(lugarList.size(), list.size());
        for (LugarEntity entity : list) {
                boolean found = false;
                for (LugarEntity storedEntity : lugarList) {
                        if (entity.getId().equals(storedEntity.getId())) {
                                found = true;
                        }
                }
                assertTrue(found);
        }
    }

    
    @Test
    void testGetLugar() throws EntityNotFoundException {
        LugarEntity entity = lugarList.get(0);
        LugarEntity resultEntity = lugarService.getLugar(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getCoordenadaX(), resultEntity.getCoordenadaX());
        assertEquals(entity.getCoordenadaY(), resultEntity.getCoordenadaY());
        assertEquals(entity.getFotos(), resultEntity.getFotos());
        assertEquals(entity.getPrecioMax(), resultEntity.getPrecioMax());
        assertEquals(entity.getPrecioMin(), resultEntity.getPrecioMin());
        assertEquals(entity.getTiempoLlegada(), resultEntity.getTiempoLlegada());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
    }
    @Test
    void testGetInvalidLugar() {
            assertThrows(EntityNotFoundException.class,()->{
                    lugarService.getLugar(0L);
            });
    }
    @Test
    void testUpdateLugar() throws EntityNotFoundException, IllegalOperationException {
        LugarEntity entity = lugarList.get(0);
        LugarEntity pojoEntity = factory.manufacturePojo(LugarEntity.class);
        pojoEntity.setId(entity.getId());
        lugarService.updateLugar(entity.getId(), pojoEntity);

        LugarEntity resp = entityManager.find(LugarEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getCoordenadaX(), resp.getCoordenadaX());
        assertEquals(pojoEntity.getCoordenadaY(), resp.getCoordenadaY());
        assertEquals(pojoEntity.getFotos(), resp.getFotos());
        assertEquals(pojoEntity.getPrecioMax(), resp.getPrecioMax());
        assertEquals(pojoEntity.getPrecioMin(),resp.getPrecioMin());
        assertEquals(pojoEntity.getTiempoLlegada(), resp.getTiempoLlegada());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
    }
    @Test
    void testUpdateLugarInvalid() {
        assertThrows(EntityNotFoundException.class, () -> {
                LugarEntity pojoEntity = factory.manufacturePojo(LugarEntity.class);
                pojoEntity.setId(0L);
                lugarService.updateLugar(0L, pojoEntity);
        });
    }
    @Test
    void testUpdateLugarWithNoValidNombre() {
        assertThrows(IllegalOperationException.class, () -> {
                LugarEntity entity = lugarList.get(0);
                LugarEntity pojoEntity = factory.manufacturePojo(LugarEntity.class);
                pojoEntity.setNombre("");
                pojoEntity.setId(entity.getId());
                lugarService.updateLugar(entity.getId(), pojoEntity);
        });
    }
    @Test
    void testUpdateLugarWithNoValidNombre2() {
        assertThrows(IllegalOperationException.class, () -> {
                LugarEntity entity = lugarList.get(0);
                LugarEntity pojoEntity = factory.manufacturePojo(LugarEntity.class);
                pojoEntity.setNombre(null);
                pojoEntity.setId(entity.getId());
                lugarService.updateLugar(entity.getId(), pojoEntity);
        });
    }
    @Test
    void testDeleteLugar() throws EntityNotFoundException, IllegalOperationException {
        LugarEntity entity = lugarList.get(1);
        lugarService.deleteLugar(entity.getId());
        LugarEntity deleted = entityManager.find(LugarEntity.class, entity.getId());
        assertNull(deleted);
    }
    @Test
    void testDeleteInvalidLugar() {
        assertThrows(EntityNotFoundException.class, ()->{
                lugarService.deleteLugar(0L);
        });
    }
   @Test
   void testDeleteLugarWithEntitySinFind() { 
       assertThrows(IllegalOperationException.class, () -> {
            LugarEntity entity = lugarList.get(0);
            lugarService.deleteLugar(entity.getId());
         });
    }

}
