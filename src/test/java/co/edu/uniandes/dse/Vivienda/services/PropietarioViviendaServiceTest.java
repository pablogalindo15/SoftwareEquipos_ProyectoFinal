package co.edu.uniandes.dse.Vivienda.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.Vivienda.entities.PropietarioEntity;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(PropietarioViviendaService.class)
public class PropietarioViviendaServiceTest {
    @Autowired
    private PropietarioViviendaService propietarioViviendaService;

    @Autowired
    private TestEntityManager entityManager;
    private PodamFactory factory = new PodamFactoryImpl();
    private PropietarioEntity propietario  = new PropietarioEntity();
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
        propietario = factory.manufacturePojo(PropietarioEntity.class);
        vivienda.setPropietario(propietario);

    }

    @Test
    void addViviendaTest() throws EntityNotFoundException{
        PropietarioEntity newPropietario = factory.manufacturePojo(PropietarioEntity.class);
        entityManager.persist(newPropietario);

        ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
        entityManager.persist(vivienda);

        propietarioViviendaService.addViviendaToPropietario(vivienda.getId(), newPropietario.getId());

        assertNotEquals(newPropietario.getViviendas(),null);
        assertTrue(newPropietario.getViviendas().contains(vivienda));
    }

    @Test
    void addViviendaInvalidTest(){
        assertThrows(EntityNotFoundException.class, () -> {
            ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
            entityManager.persist(vivienda);
            propietarioViviendaService.addViviendaToPropietario(vivienda.getId(), 0L);
        });
    }

    @Test
    void removeViviendaTest() throws EntityNotFoundException{
        PropietarioEntity newPropietario = factory.manufacturePojo(PropietarioEntity.class);
        entityManager.persist(newPropietario);

        ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
        entityManager.persist(vivienda);

        propietarioViviendaService.addViviendaToPropietario(vivienda.getId(), newPropietario.getId());
        propietarioViviendaService.removeViviendaFromPropietario(vivienda.getId(), newPropietario.getId());
        assertFalse(newPropietario.getViviendas().contains(vivienda));

    }

    @Test
    void removeViviendaInvalidTest(){
        assertThrows(EntityNotFoundException.class, () -> {
            ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
            entityManager.persist(vivienda);
            propietarioViviendaService.removeViviendaFromPropietario(vivienda.getId(), 0L);
        });
    }

    @Test
    void getViviendaTest() throws EntityNotFoundException{
        PropietarioEntity newPropietario = factory.manufacturePojo(PropietarioEntity.class);
        entityManager.persist(newPropietario);

        ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
        entityManager.persist(vivienda);

        propietarioViviendaService.addViviendaToPropietario(vivienda.getId(), newPropietario.getId());
        propietarioViviendaService.getViviendas(newPropietario.getId());
        int index = newPropietario.getViviendas().indexOf(vivienda);
        assertEquals(newPropietario.getViviendas().get(index), vivienda);
    }

    @Test
    void getViviendaInvalidTest(){
        assertThrows(EntityNotFoundException.class, ()-> {
            propietarioViviendaService.getViviendas(0L);});

    }

}
