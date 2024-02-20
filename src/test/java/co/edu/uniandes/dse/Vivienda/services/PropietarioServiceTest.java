package co.edu.uniandes.dse.Vivienda.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import co.edu.uniandes.dse.Vivienda.entities.PropietarioEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;

import javax.transaction.Transactional;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(PropietarioService.class)

public class PropietarioServiceTest {
    @Autowired
    private PropietarioService propietarioService;

    //acceso a métodos para persistir y recuperar datos de la persistencia sin pasar por el servicio
    @Autowired
    private TestEntityManager entityManager;

    // Podam: una librería que facilita la creación de instancias de objetos con datos ficticios.
    private PodamFactory factory = new PodamFactoryImpl();

    // Define una lista de entidades que ayudaran a crear las pruebas
    private List<PropietarioEntity> PropietarioList = new ArrayList<>();

    @BeforeEach
    void setUp() {
            clearData();
            insertData();
    }
    
    // Limpia las tablas que están implicadas en la prueba.
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from PropietarioEntity");
    }
    
    //Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
    private void insertData() {
            for (int i = 0; i < 3; i++) {
                    PropietarioEntity propietarioEntity = factory.manufacturePojo(PropietarioEntity.class);
                    entityManager.persist(propietarioEntity);
                    PropietarioList.add(propietarioEntity);
            }
        }

    @Test
    void testCreatePropietario() throws EntityNotFoundException, IllegalOperationException {
        PropietarioEntity newEntity = factory.manufacturePojo(PropietarioEntity.class);
        newEntity.setNombre("Juan");
        newEntity.setCelular(319607220);
        newEntity.setCorreo("jf@gmail.com");
        newEntity.setFoto("https:\\hola");

        PropietarioEntity result = propietarioService.createPropietario(newEntity);
        assertNotNull(result);
        PropietarioEntity entity = entityManager.find(PropietarioEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getCorreo(), entity.getCorreo());
        assertEquals(newEntity.getFoto(), entity.getFoto());
      }

    @Test
    void testCreatePropietarioWithNoValidName() {
        assertThrows(IllegalOperationException.class, () -> {
                PropietarioEntity newEntity = factory.manufacturePojo(PropietarioEntity.class);
                newEntity.setNombre("");
                propietarioService.createPropietario(newEntity);
        });
        }

    @Test
    void testCreatePropietarioWithNoValidName2() {
            assertThrows(IllegalOperationException.class, () -> {
                        PropietarioEntity newEntity = factory.manufacturePojo(PropietarioEntity.class);
                        newEntity.setNombre(null);
                        propietarioService.createPropietario(newEntity);
                });
        }

    @Test
    void testCreatePropietarioWithNoValidPhone() {
        assertThrows(IllegalOperationException.class, () -> {
                PropietarioEntity newEntity = factory.manufacturePojo(PropietarioEntity.class);
                newEntity.setCelular(null);
                propietarioService.createPropietario(newEntity);
        });
        }

    @Test
    void testCreatePropietarioWithNoValidEmail() {
            assertThrows(IllegalOperationException.class, () -> {
                        PropietarioEntity newEntity = factory.manufacturePojo(PropietarioEntity.class);
                        newEntity.setCorreo(null);
                        propietarioService.createPropietario(newEntity);
                });
        }
    @Test
    void testCreatePropietarioWithNoValidEmail2() {
            assertThrows(IllegalOperationException.class, () -> {
                            PropietarioEntity newEntity = factory.manufacturePojo(PropietarioEntity.class);
                            newEntity.setCorreo("");
                            propietarioService.createPropietario(newEntity);
                    });
            }
    
    @Test
    void testCreatePropietarioWithStoredEmail() {
            assertThrows(IllegalOperationException.class, () -> {
                            PropietarioEntity newEntity = factory.manufacturePojo(PropietarioEntity.class);
                            newEntity.setCorreo(PropietarioList.get(0).getCorreo());
                            propietarioService.createPropietario(newEntity);
                    });
            }



    @Test
    void testGetPropietarios() {
        List<PropietarioEntity> list = propietarioService.getPropietarios();
        assertEquals(PropietarioList.size(), list.size());
        for (PropietarioEntity entity : list) {
                boolean found = false;
                for (PropietarioEntity storedEntity : PropietarioList) {
                        if (entity.getId().equals(storedEntity.getId())) {
                                found = true;
                        }
                }
                assertTrue(found);
        }
}



    @Test
    void testGetPropietario() throws EntityNotFoundException {
        PropietarioEntity entity = PropietarioList.get(0);
        PropietarioEntity resultEntity = propietarioService.getPropietario(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getCelular(), resultEntity.getCelular());
        assertEquals(entity.getCorreo(), resultEntity.getCorreo());
        assertEquals(entity.getFoto(), resultEntity.getFoto());
}


    @Test
    void testGetInvalidPropieatario() {
        assertThrows(EntityNotFoundException.class,()->{
                propietarioService.getPropietario(0L);
        });
}

    @Test
    void testUpdatePropietario() throws EntityNotFoundException, IllegalOperationException {
        PropietarioEntity entity = PropietarioList.get(0);
        PropietarioEntity pojoEntity = factory.manufacturePojo(PropietarioEntity.class);
        pojoEntity.setId(entity.getId());
        propietarioService.updatePropietario(entity.getId(), pojoEntity);

        PropietarioEntity resp = entityManager.find(PropietarioEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
        assertEquals(pojoEntity.getCelular(), resp.getCelular());
        assertEquals(pojoEntity.getCorreo(), resp.getCorreo());
        assertEquals(pojoEntity.getFoto(), resp.getFoto());
}
    @Test
    void testUpdatePropietarioInvalid() {
        assertThrows(EntityNotFoundException.class, () -> {
                PropietarioEntity pojoEntity = factory.manufacturePojo(PropietarioEntity.class);
                pojoEntity.setId(0L);
                propietarioService.updatePropietario(0L, pojoEntity);
        });
}


    @Test
    void testUpdatePropietarioWithNoValidName() {
        assertThrows(IllegalOperationException.class, () -> {
            PropietarioEntity entity = PropietarioList.get(0);
            PropietarioEntity pojoEntity = factory.manufacturePojo(PropietarioEntity.class);
                pojoEntity.setNombre("");
                pojoEntity.setId(entity.getId());
                propietarioService.updatePropietario(entity.getId(), pojoEntity);
        });
}

    @Test
    void testUpdatePropietarioWithNoValidName2() {
        assertThrows(IllegalOperationException.class, () -> {
            PropietarioEntity entity = PropietarioList.get(0);
            PropietarioEntity pojoEntity = factory.manufacturePojo(PropietarioEntity.class);
                pojoEntity.setNombre(null);
                pojoEntity.setId(entity.getId());
                propietarioService.updatePropietario(entity.getId(), pojoEntity);
        });
}

    @Test
    void testDeletePropietario() throws EntityNotFoundException, IllegalOperationException {
        PropietarioEntity entity = PropietarioList.get(1);
        propietarioService.deletePropietario(entity.getId());
        PropietarioEntity deleted = entityManager.find(PropietarioEntity.class, entity.getId());
        assertNull(deleted);
}


    @Test
    void testDeleteInvalidPropietario() {
        assertThrows(EntityNotFoundException.class, ()->{
                propietarioService.deletePropietario(0L);
        });
}

}
