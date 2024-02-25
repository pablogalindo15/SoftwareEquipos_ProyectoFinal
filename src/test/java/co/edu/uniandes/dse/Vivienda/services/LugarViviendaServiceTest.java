package co.edu.uniandes.dse.Vivienda.services;
import static org.junit.jupiter.api.Assertions.*;

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
@Import({ LugarViviendaService.class, ViviendaService.class })
public class LugarViviendaServiceTest {
    
    @Autowired
    private LugarViviendaService lugarViviendaService;

    @Autowired
    private ViviendaService viviendaService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private LugarEntity lugar = new LugarEntity();
    private List<ViviendaEntity> viviendaList = new ArrayList<>();

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
        lugar = factory.manufacturePojo(LugarEntity.class);
        entityManager.persist(lugar);

        for (int i = 0; i < 3; i++) {
            ViviendaEntity entity = factory.manufacturePojo(ViviendaEntity.class);
            entity.getLugarDeInteres_cercano().add(lugar);
            entityManager.persist(entity);
            viviendaList.add(entity);
            lugar.getViviendas_cercanas().add(entity);
        }
    }
    
    @Test
    void testAddVivienda() throws EntityNotFoundException, IllegalOperationException {
        ViviendaEntity newVivienda = factory.manufacturePojo(ViviendaEntity.class);
        viviendaService.createVivienda(newVivienda);
        ViviendaEntity viviendaEntity = lugarViviendaService.addVivienda(lugar.getId(), newVivienda.getId());
        assertNotNull(viviendaEntity);

        assertEquals(viviendaEntity.getId(), newVivienda.getId());
        assertEquals(viviendaEntity.getNombre(), newVivienda.getNombre());
        assertEquals(viviendaEntity.getContacto(), newVivienda.getContacto());
        assertEquals(viviendaEntity.getCoordX(), newVivienda.getCoordX());
        assertEquals(viviendaEntity.getCoordY(), newVivienda.getCoordY());
        assertEquals(viviendaEntity.getDescripcion(), newVivienda.getDescripcion());
        assertEquals(viviendaEntity.getDireccion(), newVivienda.getDireccion());
        assertEquals(viviendaEntity.getEstrato(), newVivienda.getEstrato());
        assertEquals(viviendaEntity.getFotos(), newVivienda.getFotos());
        assertEquals(viviendaEntity.getHabitantes_acutales(), newVivienda.getHabitantes_acutales());
        assertEquals(viviendaEntity.getHistorial(), newVivienda.getHistorial());
        
        ViviendaEntity lastVivienda = lugarViviendaService.getVivienda(lugar.getId(), newVivienda.getId());

        assertEquals(lastVivienda.getId(), newVivienda.getId());
        assertEquals(lastVivienda.getNombre(), newVivienda.getNombre());
        assertEquals(lastVivienda.getContacto(), newVivienda.getContacto());
        assertEquals(lastVivienda.getCoordX(), newVivienda.getCoordX());
        assertEquals(lastVivienda.getCoordY(), newVivienda.getCoordY());
        assertEquals(lastVivienda.getDescripcion(), newVivienda.getDescripcion());
        assertEquals(lastVivienda.getDireccion(), newVivienda.getDireccion());
        assertEquals(lastVivienda.getEstrato(), newVivienda.getEstrato());
        assertEquals(lastVivienda.getFotos(), newVivienda.getFotos());
        assertEquals(lastVivienda.getHabitantes_acutales(), newVivienda.getHabitantes_acutales());
        assertEquals(lastVivienda.getHistorial(), newVivienda.getHistorial());

    }
    
    @Test
    void testAddViviendaInvalidlugar() {
        assertThrows(EntityNotFoundException.class, () -> {
            ViviendaEntity newVivienda = factory.manufacturePojo(ViviendaEntity.class);
            viviendaService.createVivienda(newVivienda);
            lugarViviendaService.addVivienda(0L, newVivienda.getId());
        });
    }

    @Test
    void testAddInvalidVivienda() {
        assertThrows(EntityNotFoundException.class, () -> {
            lugarViviendaService.addVivienda(lugar.getId(), 0L);
        });
    }

    @Test
    void testGetViviendas() throws EntityNotFoundException {
        List<ViviendaEntity> ViviendaEntities = lugarViviendaService.getViviendas(lugar.getId());

        assertEquals(viviendaList.size(), ViviendaEntities.size());

        for (int i = 0; i < viviendaList.size(); i++) {
            assertTrue(ViviendaEntities.contains(viviendaList.get(0)));
        }
    }

    @Test
    void testGetViviendasInvalidlugar() {
        assertThrows(EntityNotFoundException.class, () -> {
            lugarViviendaService.getViviendas(0L);
        });
    }

    @Test
    void testGetVivienda() throws EntityNotFoundException, IllegalOperationException {
        ViviendaEntity viviendaEntity = viviendaList.get(0);
        ViviendaEntity vivienda = lugarViviendaService.getVivienda(lugar.getId(), viviendaEntity.getId());
        assertNotNull(vivienda);
        assertEquals(viviendaEntity.getId(), vivienda.getId());
        assertEquals(viviendaEntity.getNombre(), vivienda.getNombre());
        assertEquals(viviendaEntity.getContacto(), vivienda.getContacto());
        assertEquals(viviendaEntity.getCoordX(), vivienda.getCoordX());
        assertEquals(viviendaEntity.getCoordY(), vivienda.getCoordY());
        assertEquals(viviendaEntity.getDescripcion(), vivienda.getDescripcion());
        assertEquals(viviendaEntity.getDireccion(), vivienda.getDireccion());
        assertEquals(viviendaEntity.getEstrato(), vivienda.getEstrato());
        assertEquals(viviendaEntity.getFotos(), vivienda.getFotos());
        assertEquals(viviendaEntity.getHabitantes_acutales(), vivienda.getHabitantes_acutales());
        assertEquals(viviendaEntity.getHistorial(), vivienda.getHistorial());
    }

    @Test
    void testGetViviendaInvalidlugar() {
        assertThrows(EntityNotFoundException.class, () -> {
            ViviendaEntity ViviendaEntity = viviendaList.get(0);
            lugarViviendaService.getVivienda(0L, ViviendaEntity.getId());
        });
    }

    @Test
    void testGetInvalidVivienda() {
        assertThrows(EntityNotFoundException.class, () -> {
            lugarViviendaService.getVivienda(lugar.getId(), 0L);
        });
    }


    @Test
    void testGetViviendaNotAssociatedlugar() {
        assertThrows(IllegalOperationException.class, () -> {
            LugarEntity lugarEntity = factory.manufacturePojo(LugarEntity.class);
            entityManager.persist(lugarEntity);

            ViviendaEntity viviendaEntity = factory.manufacturePojo(ViviendaEntity.class);
            entityManager.persist(viviendaEntity);

            lugarViviendaService.getVivienda(lugarEntity.getId(), viviendaEntity.getId());
        });
    }

    @Test
    void testReplaceViviendas() throws EntityNotFoundException, IllegalOperationException {
        List<ViviendaEntity> nuevaLista = new ArrayList<>();
        
        for (int i = 0; i < 3; i++) {
            ViviendaEntity entity = factory.manufacturePojo(ViviendaEntity.class);
            entityManager.persist(entity);
            nuevaLista.add(entity);
        }
        
        lugarViviendaService.addViviendas(lugar.getId(), nuevaLista);
        
        List<ViviendaEntity> ViviendaEntities = entityManager.find(LugarEntity.class, lugar.getId()).getViviendas_cercanas();
        for (ViviendaEntity item : nuevaLista) {
            assertTrue(ViviendaEntities.contains(item));
        }
    }
    
    @Test
    void testReplaceViviendasInvalidlugar() {
        assertThrows(EntityNotFoundException.class, () -> {
            List<ViviendaEntity> nuevaLista = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                ViviendaEntity entity = factory.manufacturePojo(ViviendaEntity.class);
                viviendaService.createVivienda(entity);
                nuevaLista.add(entity);
            }
            lugarViviendaService.addViviendas(0L, nuevaLista);
        });
    }

    @Test
    void testReplaceInvalidViviendas() {
        assertThrows(EntityNotFoundException.class, () -> {
            List<ViviendaEntity> nuevaLista = new ArrayList<>();
            ViviendaEntity entity = factory.manufacturePojo(ViviendaEntity.class);
            entity.setId(0L);
            nuevaLista.add(entity);
            lugarViviendaService.addViviendas(lugar.getId(), nuevaLista);
        });
    }

    @Test
    void testRemoveVivienda() throws EntityNotFoundException {
        for (ViviendaEntity Vivienda : viviendaList) {
            lugarViviendaService.removeVivienda(lugar.getId(), Vivienda.getId());
        }
        assertTrue(lugarViviendaService.getViviendas(lugar.getId()).isEmpty());
    }

    @Test
    void testRemoveViviendaInvalidlugar() {
        assertThrows(EntityNotFoundException.class, () -> {
            for (ViviendaEntity Vivienda : viviendaList) {
                lugarViviendaService.removeVivienda(0L, Vivienda.getId());
            }
        });
    }

    @Test
    void testRemoveInvalidVivienda() {
        assertThrows(EntityNotFoundException.class, () -> {
            lugarViviendaService.removeVivienda(lugar.getId(), 0L);
        });
    }
}

