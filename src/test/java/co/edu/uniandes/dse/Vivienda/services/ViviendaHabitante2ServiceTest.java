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

import co.edu.uniandes.dse.Vivienda.entities.HabitanteEntity;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(ViviendaHabitante2Service.class)
/*
 * Segunda relacion con Habitantes, donde se modelan el historial de una vivienda
 */
public class ViviendaHabitante2ServiceTest {
    @Autowired
    private ViviendaHabitante2Service viviendaHabitanteService;

    @Autowired
    private TestEntityManager entityManager;
    private PodamFactory factory = new PodamFactoryImpl();
    private ViviendaEntity vivienda = new ViviendaEntity();

    private List<HabitanteEntity> habitanteList = new ArrayList<>();
    
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


        for (int i = 0; i < 3; i++) {
            HabitanteEntity entity = factory.manufacturePojo(HabitanteEntity.class);
            entityManager.persist(entity);
            entity.getViviendas().add(vivienda);
            habitanteList.add(entity);
            vivienda.getHistorial().add(entity);
        }
       
    }

    @Test
    void addHabitanteTest() throws EntityNotFoundException, IllegalOperationException {
        ViviendaEntity newVivienda = factory.manufacturePojo(ViviendaEntity.class);
        entityManager.persist(newVivienda);


        HabitanteEntity habitante = factory.manufacturePojo(HabitanteEntity.class);
        entityManager.persist(habitante);

        viviendaHabitanteService.addHabitante(newVivienda.getId(), habitante.getId());

        HabitanteEntity ultimoHabitante = viviendaHabitanteService.getHabitante(newVivienda.getId(), habitante.getId());
        assertNotEquals(newVivienda.getHistorial(), null);
        assertEquals(habitante.getId(), ultimoHabitante.getId());
        assertEquals(habitante, ultimoHabitante);

    }

    @Test
    void addHabitanteInvalidTest() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(EntityNotFoundException.class, () -> {
            ViviendaEntity newVivienda = factory.manufacturePojo(ViviendaEntity.class);
            entityManager.persist(newVivienda);
            viviendaHabitanteService.addHabitante(newVivienda.getId(), 0L);
        });
    }

    @Test
    void getHabitantesTest() throws EntityNotFoundException{
        List<HabitanteEntity> habitantesEntity = viviendaHabitanteService.getHabitantes(vivienda.getId());
        assertEquals(habitanteList, habitantesEntity);
    }

    @Test
	void getHabitantesInvalidTest(){
		assertThrows(EntityNotFoundException.class, ()->{
			viviendaHabitanteService.getHabitantes(0L);
		});
	}

    @Test
	void getHabitanteTest() throws EntityNotFoundException, IllegalOperationException {
		HabitanteEntity habitanteEntity = habitanteList.get(0);
		HabitanteEntity habitante = viviendaHabitanteService.getHabitante(vivienda.getId(), habitanteEntity.getId());
		assertNotNull(habitante);
        assertEquals(habitanteEntity, habitante);
    }

    @Test
	void replaceHabitantesTest() throws EntityNotFoundException {
		List<HabitanteEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			HabitanteEntity entity = factory.manufacturePojo(HabitanteEntity.class);
			entityManager.persist(entity);
			vivienda.getHistorial().add(entity);
			nuevaLista.add(entity);
		}
		viviendaHabitanteService.replaceHabitantes(vivienda.getId(), nuevaLista);
		
		List<HabitanteEntity> habitanteEntities = viviendaHabitanteService.getHabitantes(vivienda.getId());
		for (HabitanteEntity hNuevaLista : nuevaLista) {
			assertTrue(habitanteEntities.contains(hNuevaLista));
		}
	}

    @Test
    void replaceHabitantesInvalidTest(){
        assertThrows(EntityNotFoundException.class, ()->{
            List<HabitanteEntity> nuevaLista = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                HabitanteEntity entity = factory.manufacturePojo(HabitanteEntity.class);
                entity.getViviendas().add(vivienda);
                entityManager.persist(entity);
                nuevaLista.add(entity);
            }
            viviendaHabitanteService.replaceHabitantes(0L, nuevaLista);
        });
    }

    @Test
    void removeHabitanteTest() throws EntityNotFoundException{
        for (HabitanteEntity habitante : habitanteList) {
			viviendaHabitanteService.removeHabitante(vivienda.getId(), habitante.getId());
		}
		assertTrue(viviendaHabitanteService.getHabitantes(vivienda.getId()).isEmpty());
    }

    @Test
    void removeHabitanteInvalidTest() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(EntityNotFoundException.class, () -> {
            viviendaHabitanteService.removeHabitante(vivienda.getId(), 0L);
        });
    }

    @Test
    void removeHabitanteInvalidTest2() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(EntityNotFoundException.class, () -> {
            viviendaHabitanteService.removeHabitante(0L, habitanteList.get(0).getId());
        });
    }

}
