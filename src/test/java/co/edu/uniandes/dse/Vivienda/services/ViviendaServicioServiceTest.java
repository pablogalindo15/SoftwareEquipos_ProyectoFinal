package co.edu.uniandes.dse.Vivienda.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
@Import(ViviendaServicioService.class)
public class ViviendaServicioServiceTest {

    @Autowired
    private ViviendaServicioService viviendaServicioService;

    @Autowired
    private TestEntityManager entityManager;
    private PodamFactory factory = new PodamFactoryImpl();
    private ViviendaEntity vivienda = new ViviendaEntity();

    private List<ServicioEntity> servicioList = new ArrayList<>();
    
    @BeforeEach
    public void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from ServicioEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from ViviendaEntity").executeUpdate();
    }

    private void insertData() {
        vivienda = factory.manufacturePojo(ViviendaEntity.class);
        entityManager.persist(vivienda);

        for (int i = 0; i < 3; i++) {
            ServicioEntity entity = factory.manufacturePojo(ServicioEntity.class);
            entityManager.persist(entity);
            entity.getViviendasServicio().add(vivienda);
            servicioList.add(entity);
            vivienda.getServiciosVivienda().add(entity);
        }
       
    }

    @Test
    void addServicioTest() throws EntityNotFoundException, IllegalOperationException {
        ViviendaEntity newVivienda = factory.manufacturePojo(ViviendaEntity.class);
        entityManager.persist(newVivienda);


        ServicioEntity servicio = factory.manufacturePojo(ServicioEntity.class);
        entityManager.persist(servicio);

        viviendaServicioService.addServicio(newVivienda.getId(), servicio.getId());

        ServicioEntity ultimoServicio = viviendaServicioService.getServicio(newVivienda.getId(), servicio.getId());
        assertNotEquals(newVivienda.getServiciosVivienda(), null);
        assertEquals(servicio.getId(), ultimoServicio.getId());
        assertEquals(servicio, ultimoServicio);

    }

    @Test
    void addServicioInvalidTest() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(EntityNotFoundException.class, () -> {
            ViviendaEntity newVivienda = factory.manufacturePojo(ViviendaEntity.class);
            entityManager.persist(newVivienda);
            viviendaServicioService.addServicio(newVivienda.getId(), 0L);
        });
    }

    @Test
    void getServiciosTest() throws EntityNotFoundException{
        List<ServicioEntity> servicioEntities = viviendaServicioService.getServicios(vivienda.getId());
        assertEquals(servicioList, servicioEntities);
    }

    @Test
	void getServicioInvalidTest(){
		assertThrows(EntityNotFoundException.class, ()->{
			viviendaServicioService.getServicios(0L);
		});
	}

    @Test
	void getServicioTest() throws EntityNotFoundException, IllegalOperationException {
		ServicioEntity servicioEntity = servicioList.get(0);
		ServicioEntity servicio = viviendaServicioService.getServicio(vivienda.getId(), servicioEntity.getId());
		assertNotNull(servicio);
        assertEquals(servicioEntity, servicio);
    }

    @Test
	void replaceServiciosTest() throws EntityNotFoundException {
		List<ServicioEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			ServicioEntity entity = factory.manufacturePojo(ServicioEntity.class);
			entityManager.persist(entity);
			vivienda.getServiciosVivienda().add(entity);
			nuevaLista.add(entity);
		}
		viviendaServicioService.replaceServicios(vivienda.getId(), nuevaLista);
		
		List<ServicioEntity> servicioEntities = viviendaServicioService.getServicios(vivienda.getId());
		for (ServicioEntity sNuevaLista : nuevaLista) {
			assertTrue(servicioEntities.contains(sNuevaLista));
		}
	}

    @Test
    void replaceServiciosInvalidTest(){
        assertThrows(EntityNotFoundException.class, ()->{
            List<ServicioEntity> nuevaLista = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                ServicioEntity entity = factory.manufacturePojo(ServicioEntity.class);
                entity.getViviendasServicio().add(vivienda);
                entityManager.persist(entity);
                nuevaLista.add(entity);
            }
            viviendaServicioService.replaceServicios(0L, nuevaLista);
        });
    }

    @Test
    void removeServicioTest() throws EntityNotFoundException{
        for (ServicioEntity servicio : servicioList) {
			viviendaServicioService.removeServicio(vivienda.getId(), servicio.getId());
		}
		assertTrue(viviendaServicioService.getServicios(vivienda.getId()).isEmpty());
    }

    @Test
    void removeServicioInvalidTest() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(EntityNotFoundException.class, () -> {
            viviendaServicioService.removeServicio(vivienda.getId(), 0L);
        });
    }

    @Test
    void removeServicioInvalidTest2() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(EntityNotFoundException.class, () -> {
            viviendaServicioService.removeServicio(0L, servicioList.get(0).getId());
        });
    }

    
     
}
