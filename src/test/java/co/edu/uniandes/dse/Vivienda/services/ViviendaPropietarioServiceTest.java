package co.edu.uniandes.dse.Vivienda.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

import co.edu.uniandes.dse.Vivienda.entities.PropietarioEntity;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(ViviendaPropietarioService.class)
public class ViviendaPropietarioServiceTest {
    @Autowired
    private ViviendaPropietarioService viviendaPropietarioService;

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
    void addPropietarioTest() throws EntityNotFoundException{
        PropietarioEntity newPropietario = factory.manufacturePojo(PropietarioEntity.class);
        entityManager.persist(newPropietario);

        ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
        entityManager.persist(vivienda);

        viviendaPropietarioService.addPropietarioToVivienda(vivienda.getId(), newPropietario.getId());
        assertNotEquals(vivienda.getPropietario(),null);
        assertEquals(vivienda.getPropietario(), newPropietario);
    }

    @Test
    void addPropietarioInvalidTest(){
        assertThrows(EntityNotFoundException.class, () -> {
            ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
            entityManager.persist(vivienda);
            viviendaPropietarioService.addPropietarioToVivienda(vivienda.getId(), 0L);
        });
    }

    @Test
    void removePropietarioTest() throws EntityNotFoundException{
        PropietarioEntity newPropietario = factory.manufacturePojo(PropietarioEntity.class);
        entityManager.persist(newPropietario);

        ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
        entityManager.persist(vivienda);

        viviendaPropietarioService.addPropietarioToVivienda(vivienda.getId(), newPropietario.getId());
        viviendaPropietarioService.removePropietarioFromVivienda(vivienda.getId(), newPropietario.getId());
        assertNotEquals(vivienda.getPropietario(),newPropietario);

    }

    @Test
    void removePropietarioInvalidTest(){
        assertThrows(EntityNotFoundException.class, () -> {
            ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
            entityManager.persist(vivienda);
            viviendaPropietarioService.removePropietarioFromVivienda(vivienda.getId(), 0L);
        });

    }

    @Test
    void getPropietarioTest() throws EntityNotFoundException{
        PropietarioEntity newPropietario = factory.manufacturePojo(PropietarioEntity.class);
        entityManager.persist(newPropietario);

        ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
        entityManager.persist(vivienda);

        viviendaPropietarioService.addPropietarioToVivienda(vivienda.getId(), newPropietario.getId());
        viviendaPropietarioService.getPropietario(vivienda.getId());
        assertEquals(vivienda.getPropietario(), newPropietario);
    }

    @Test
    void getPropietarioInvalidTest(){
        assertThrows(EntityNotFoundException.class, ()-> {
            viviendaPropietarioService.getPropietario(0L);});

    }

}
