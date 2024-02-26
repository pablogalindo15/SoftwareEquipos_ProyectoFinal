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
import co.edu.uniandes.dse.Vivienda.entities.ServicioEntity;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import({ ServicioViviendaService.class, ViviendaService.class })

public class ServicioViviendaServiceTest {
    @Autowired
    private ServicioViviendaService ServicioViviendaService;
    @Autowired
    private ViviendaService viviendaService;
    @Autowired
    private TestEntityManager entityManager;
    private PodamFactory factory = new PodamFactoryImpl();
    private ServicioEntity servicio = new ServicioEntity();
    private List<ViviendaEntity> viviendaList = new ArrayList<>();
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from ServicioEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from ViviendaEntity").executeUpdate();
    }
    private void insertData() {
        servicio = factory.manufacturePojo(ServicioEntity.class);
        entityManager.persist(servicio);
        for (int i = 0; i < 3; i++) {
            ViviendaEntity entity = factory.manufacturePojo(ViviendaEntity.class);
            entity.getServiciosVivienda().add(servicio);
            entityManager.persist(entity);
            viviendaList.add(entity);
            servicio.getViviendasServicio().add(entity);
        }
    }
    @Test
    void testGetVivienda() throws EntityNotFoundException, IllegalOperationException {
        ViviendaEntity viviendaEntity = viviendaList.get(0);
        ViviendaEntity vivienda = ServicioViviendaService.getVivienda(servicio.getId(), viviendaEntity.getId());
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
        assertEquals(viviendaEntity.getHabitantes_actuales(), vivienda.getHabitantes_actuales());
        assertEquals(viviendaEntity.getHistorial(), vivienda.getHistorial());
    }
    @Test
    void testAddVivienda() throws EntityNotFoundException, IllegalOperationException {
        ViviendaEntity newVivienda = factory.manufacturePojo(ViviendaEntity.class);
        viviendaService.createVivienda(newVivienda);
        ViviendaEntity viviendaEntity = ServicioViviendaService.addVivienda(servicio.getId(), newVivienda.getId());
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
        assertEquals(viviendaEntity.getHabitantes_actuales(), newVivienda.getHabitantes_actuales());
        assertEquals(viviendaEntity.getHistorial(), newVivienda.getHistorial());
        ViviendaEntity lastVivienda = ServicioViviendaService.getVivienda(servicio.getId(), newVivienda.getId());
        assertEquals(lastVivienda.getId(), newVivienda.getId());
        assertEquals(lastVivienda.getNombre(), newVivienda.getNombre());
        assertEquals(lastVivienda.getContacto(), newVivienda.getContacto());
        assertEquals(lastVivienda.getCoordX(), newVivienda.getCoordX());
        assertEquals(lastVivienda.getCoordY(), newVivienda.getCoordY());
        assertEquals(lastVivienda.getDescripcion(), newVivienda.getDescripcion());
        assertEquals(lastVivienda.getDireccion(), newVivienda.getDireccion());
        assertEquals(lastVivienda.getEstrato(), newVivienda.getEstrato());
        assertEquals(lastVivienda.getFotos(), newVivienda.getFotos());
        assertEquals(lastVivienda.getHabitantes_actuales(), newVivienda.getHabitantes_actuales());
        assertEquals(lastVivienda.getHistorial(), newVivienda.getHistorial());
    }
    
    @Test
    void testAddViviendaInvalidServicio() {
        assertThrows(EntityNotFoundException.class, () -> {
            ViviendaEntity newVivienda = factory.manufacturePojo(ViviendaEntity.class);
            viviendaService.createVivienda(newVivienda);
            ServicioViviendaService.addVivienda(0L, newVivienda.getId());
        });
    }
    @Test
    void testAddInvalidVivienda() {
        assertThrows(EntityNotFoundException.class, () -> {
            ServicioViviendaService.addVivienda(servicio.getId(), 0L);
        });
    }
    @Test
    void testGetViviendas() throws EntityNotFoundException {
        List<ViviendaEntity> ViviendaEntities = ServicioViviendaService.getViviendas(servicio.getId());

        assertEquals(viviendaList.size(), ViviendaEntities.size());

        for (int i = 0; i < viviendaList.size(); i++) {
            assertTrue(ViviendaEntities.contains(viviendaList.get(0)));
        }
    }
    @Test
    void testGetViviendasInvalidServicio() {
        assertThrows(EntityNotFoundException.class, () -> {
            ServicioViviendaService.getViviendas(0L);
        });
    }
    @Test
    void testGetViviendaInvalidServicio() {
        assertThrows(EntityNotFoundException.class, () -> {
            ViviendaEntity ViviendaEntity = viviendaList.get(0);
            ServicioViviendaService.getVivienda(0L, ViviendaEntity.getId());
        });
    }
    @Test
    void testGetInvalidVivienda() {
        assertThrows(EntityNotFoundException.class, () -> {
            ServicioViviendaService.getVivienda(servicio.getId(), 0L);
        });
    }
    @Test
    void testGetViviendaNotAssociatedServicio() {
        assertThrows(IllegalOperationException.class, () -> {
            ServicioEntity servicioEntity = factory.manufacturePojo(ServicioEntity.class);
            entityManager.persist(servicioEntity);

            ViviendaEntity viviendaEntity = factory.manufacturePojo(ViviendaEntity.class);
            entityManager.persist(viviendaEntity);

            ServicioViviendaService.getVivienda(servicioEntity.getId(), viviendaEntity.getId());
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
        ServicioViviendaService.replaceViviendas(servicio.getId(), nuevaLista);
        List<ViviendaEntity> ViviendaEntities = entityManager.find(ServicioEntity.class, servicio.getId()).getViviendasServicio();
        for (ViviendaEntity item : nuevaLista) {
            assertTrue(ViviendaEntities.contains(item));
        }
    }

    @Test
    void testReplaceViviendasInvalidServicio() {
        assertThrows(EntityNotFoundException.class, () -> {
            List<ViviendaEntity> nuevaLista = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                ViviendaEntity entity = factory.manufacturePojo(ViviendaEntity.class);
                viviendaService.createVivienda(entity);
                nuevaLista.add(entity);
            }
            ServicioViviendaService.replaceViviendas(0L, nuevaLista);
        });
    }
    @Test
    void testReplaceInvalidViviendas() {
        assertThrows(EntityNotFoundException.class, () -> {
            List<ViviendaEntity> nuevaLista = new ArrayList<>();
            ViviendaEntity entity = factory.manufacturePojo(ViviendaEntity.class);
            entity.setId(0L);
            nuevaLista.add(entity);
            ServicioViviendaService.replaceViviendas(servicio.getId(), nuevaLista);
        });
    }
    @Test
    void testRemoveVivienda() throws EntityNotFoundException {
        for (ViviendaEntity Vivienda : viviendaList) {
            ServicioViviendaService.removeVivienda(servicio.getId(), Vivienda.getId());
        }
        assertTrue(ServicioViviendaService.getViviendas(servicio.getId()).isEmpty());
    }
    @Test
    void testRemoveViviendaInvalidServicio() {
        assertThrows(EntityNotFoundException.class, () -> {
            for (ViviendaEntity Vivienda : viviendaList) {
                ServicioViviendaService.removeVivienda(0L, Vivienda.getId());
            }
        });
    }
    @Test
    void testRemoveInvalidVivienda() {
        assertThrows(EntityNotFoundException.class, () -> {
            ServicioViviendaService.removeVivienda(servicio.getId(), 0L);
        });
    }
}


    
    


