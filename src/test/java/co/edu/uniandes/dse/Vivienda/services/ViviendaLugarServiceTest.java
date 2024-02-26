package co.edu.uniandes.dse.Vivienda.services;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.Vivienda.entities.LugarEntity;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(ViviendaLugarService.class)

public class ViviendaLugarServiceTest {
    @Autowired
    private ViviendaLugarService viviendaLugarService;
    
    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private ViviendaEntity vivienda = new ViviendaEntity();
    private List<LugarEntity> lugarList = new ArrayList<>();

    
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }
    
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from LugarEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from ViviendaEntity").executeUpdate();
    }

    private void insertData() {
        vivienda = factory.manufacturePojo(ViviendaEntity.class);
        entityManager.persist(vivienda);

        for (int i = 0; i < 3; i++) {
            LugarEntity entity = factory.manufacturePojo(LugarEntity.class);
            entityManager.persist(entity);
            entity.getViviendas_cercanas().add(vivienda);
            lugarList.add(entity);
            vivienda.getLugarDeInteres_cercano().add(entity);   
        }
    }

    @Test
    void testAddLugar() throws EntityNotFoundException, IllegalOperationException {
        ViviendaEntity newvivienda = factory.manufacturePojo(ViviendaEntity.class);
        entityManager.persist(newvivienda);
        
        LugarEntity lugar = factory.manufacturePojo(LugarEntity.class);
        entityManager.persist(lugar);
        
        viviendaLugarService.addLugar(newvivienda.getId(), lugar.getId());
        
        LugarEntity lastLugar = viviendaLugarService.getLugar(newvivienda.getId(), lugar.getId());
        assertEquals(lugar.getId(), lastLugar.getId());
        assertEquals(lugar.getNombre(), lastLugar.getNombre());
        assertEquals(lugar.getCoordenadaX(), lastLugar.getCoordenadaX());
        assertEquals(lugar.getCoordenadaY(), lastLugar.getCoordenadaY());
        assertEquals(lugar.getFotos(), lastLugar.getFotos());
        assertEquals(lugar.getPrecioMax(), lastLugar.getPrecioMax());
        assertEquals(lugar.getPrecioMin(), lastLugar.getPrecioMin());
        assertEquals(lugar.getTiempoLlegada(), lastLugar.getTiempoLlegada());
    }
    
    @Test
    void testAddInvalidLugar() {
        assertThrows(EntityNotFoundException.class, ()->{
            ViviendaEntity newVivienda = factory.manufacturePojo(ViviendaEntity.class);
            entityManager.persist(newVivienda);
            viviendaLugarService.addLugar(newVivienda.getId(), 0L);
        });
    }

    @Test
    void testAddlugarInvalidVivienda() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(EntityNotFoundException.class, ()->{
            LugarEntity lugar = factory.manufacturePojo(LugarEntity.class);
            entityManager.persist(lugar);
            viviendaLugarService.addLugar(0L, lugar.getId());
        });
    }

    @Test
    void testGetlugares() throws EntityNotFoundException {
        List<LugarEntity> lugarEntities = viviendaLugarService.getLugares(vivienda.getId());

        assertEquals(lugarList.size(), lugarEntities.size());

        for (int i = 0; i < lugarList.size(); i++) {
            assertTrue(lugarEntities.contains(lugarList.get(0)));
        }
    }

    @Test
    void testGetlugarsInvalidVivienda(){
        assertThrows(EntityNotFoundException.class, ()-> {
            viviendaLugarService.getLugares(0L);});
    }

    @Test
    void testGetlugar() throws EntityNotFoundException, IllegalOperationException {
        LugarEntity lugarEntity = lugarList.get(0);
        LugarEntity lugar = viviendaLugarService.getLugar(vivienda.getId(), lugarEntity.getId());
        assertNotNull(lugar);
        assertEquals(lugarEntity.getId(), lugar.getId());
        assertEquals(lugarEntity.getNombre(), lugar.getNombre());
        assertEquals(lugarEntity.getCoordenadaX(), lugar.getCoordenadaX());
        assertEquals(lugarEntity.getCoordenadaY(), lugar.getCoordenadaY());
        assertEquals(lugarEntity.getFotos(), lugar.getFotos());
        assertEquals(lugarEntity.getPrecioMax(), lugar.getPrecioMax());
        assertEquals(lugarEntity.getPrecioMin(), lugar.getPrecioMin());
        assertEquals(lugarEntity.getTiempoLlegada(), lugar.getTiempoLlegada());
    }
    
    @Test
    void testGetInvalidlugar()  {
        assertThrows(EntityNotFoundException.class, ()->{
            viviendaLugarService.getLugar(vivienda.getId(), 0L);
        });
    }
    
    @Test
    void testGetlugarInvalidvivienda() {
        assertThrows(EntityNotFoundException.class, ()->{
            LugarEntity LugarEntity = lugarList.get(0);
            viviendaLugarService.getLugar(0L, LugarEntity.getId());
        });
    }
    
    @Test
    void testGetNotAssociatedlugar() {
        assertThrows(IllegalOperationException.class, ()->{
            ViviendaEntity newVivienda = factory.manufacturePojo(ViviendaEntity.class);
            entityManager.persist(newVivienda);
            LugarEntity lugar = factory.manufacturePojo(LugarEntity.class);
            entityManager.persist(lugar);
            viviendaLugarService.getLugar(newVivienda.getId(), lugar.getId());
        });
    }

    @Test
    void testReplacelugares() throws EntityNotFoundException {
        List<LugarEntity> nuevaLista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            LugarEntity entity = factory.manufacturePojo(LugarEntity.class);
            entityManager.persist(entity);
            vivienda.getLugarDeInteres_cercano().add(entity);
            nuevaLista.add(entity);
        }
        viviendaLugarService.replaceLugares(vivienda.getId(), nuevaLista);
        
        List<LugarEntity> lugarEntities = viviendaLugarService.getLugares(vivienda.getId());
        for (LugarEntity aNuevaLista : nuevaLista) {
            assertTrue(lugarEntities.contains(aNuevaLista));
        }
    }

    @Test
    void testReplacelugares2() throws EntityNotFoundException {
        List<LugarEntity> nuevaLista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            LugarEntity entity = factory.manufacturePojo(LugarEntity.class);
            entityManager.persist(entity);
            nuevaLista.add(entity);
        }
        viviendaLugarService.replaceLugares(vivienda.getId(), nuevaLista);
        
        List<LugarEntity> lugarEntities = viviendaLugarService.getLugares(vivienda.getId());
        for (LugarEntity aNuevaLista : nuevaLista) {
            assertTrue(lugarEntities.contains(aNuevaLista));
        }
    }
    
    @Test
    void testReplacelugarsInvalidvivienda(){
        assertThrows(EntityNotFoundException.class, ()->{
            List<LugarEntity> nuevaLista = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                LugarEntity entity = factory.manufacturePojo(LugarEntity.class);
                entity.getViviendas_cercanas().add(vivienda);       
                entityManager.persist(entity);
                nuevaLista.add(entity);
            }
            viviendaLugarService.replaceLugares(0L, nuevaLista);
        });
    }
    
    @Test
    void testReplaceInvalidlugars() {
        assertThrows(EntityNotFoundException.class, ()->{
            List<LugarEntity> nuevaLista = new ArrayList<>();
            LugarEntity entity = factory.manufacturePojo(LugarEntity.class);
            entity.setId(0L);
            nuevaLista.add(entity);
            viviendaLugarService.replaceLugares(vivienda.getId(), nuevaLista);
        });
    }
    
    @Test
    void testReplacelugarsInvalidlugar(){
        assertThrows(EntityNotFoundException.class, ()->{
            List<LugarEntity> nuevaLista = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                LugarEntity entity = factory.manufacturePojo(LugarEntity.class);
                entity.getViviendas_cercanas().add(vivienda);       
                entityManager.persist(entity);
                nuevaLista.add(entity);
            }
            viviendaLugarService.replaceLugares(0L, nuevaLista);
        });
    }

    @Test
    void testRemovelugar() throws EntityNotFoundException {
        for (LugarEntity lugar : lugarList) {
            viviendaLugarService.removeLugar(vivienda.getId(), lugar.getId());
        }
        assertTrue(viviendaLugarService.getLugares(vivienda.getId()).isEmpty());
    }
    
    @Test
    void testRemoveInvalidlugar(){
        assertThrows(EntityNotFoundException.class, ()->{
            viviendaLugarService.removeLugar(vivienda.getId(), 0L);
        });
    }
    
    @Test
    void testRemovelugarInvalidvivienda(){
        assertThrows(EntityNotFoundException.class, ()->{
            viviendaLugarService.removeLugar(0L, lugarList.get(0).getId());
        });
    }
}

