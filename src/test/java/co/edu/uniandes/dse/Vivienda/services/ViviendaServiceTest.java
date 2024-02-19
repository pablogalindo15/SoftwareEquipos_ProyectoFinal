package co.edu.uniandes.dse.Vivienda.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;


import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity.tipoVivienda;
import co.edu.uniandes.dse.Vivienda.exceptions.*;
import uk.co.jemos.podam.api.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(ViviendaService.class)
public class ViviendaServiceTest {
    // atributos
    @Autowired
    private ViviendaService viviendaService;

    @Autowired
    private TestEntityManager entityManager;

    private List<ViviendaEntity> viviendaList = new ArrayList<>();

    /*
     * PODAM es una librería que facilita la creación de instancias de 
     * objetos con datos ficticios. Así se evita el cablear los datos 
     * directamente en la prueba.
     */
    private PodamFactory factory = new PodamFactoryImpl();


    //setup

    
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from ViviendaEntity");
    }

    private void insertData(){
        for (int i = 0; i < 5; i++) { // Insertar 5 viviendas de ejemplo (?no estoy segura de que si haga esto)
            ViviendaEntity viviendaEntity = factory.manufacturePojo(ViviendaEntity.class);
            viviendaList.add(viviendaEntity);
            entityManager.persist(viviendaEntity);
        }        
    }
    
     
   

    /*
     * TESTS PARA CREAR NUEVA VIVIENDA
     */

     @Test
     public void testCreateViviendaEntity() throws EntityNotFoundException, IllegalOperationException{
        ViviendaEntity newViviendaEntity = factory.manufacturePojo(ViviendaEntity.class);
        
        ViviendaEntity result = viviendaService.createVivienda(newViviendaEntity);
        Boolean respOcupada = true;
        result.setOcupada(respOcupada);
        assertEquals(newViviendaEntity, result);
        assertNotNull(result.getNombre());
        assertNotEquals(0, result.getPrecio());
        assertNotNull(result.getDescripcion());
        assertNotNull(result.getFotos());
        assertNotEquals(0, result.getTamano());
        assertNotEquals(0, result.getEstrato());
        assertNotNull(result.getRestricciones());
        assertNotNull(result.getContacto());
        assertNotNull(result.getDireccion());
        assertEquals(respOcupada, result.getOcupada());
        assertNotEquals(0, result.getCoordX());
        assertNotEquals(0, result.getCoordY());
        assertTrue(result.getTipo() instanceof tipoVivienda);

     }

    @Test
    public void testCreateViviendaEntityNoName() throws EntityNotFoundException, IllegalOperationException{        
        ViviendaEntity newViviendaEntity = factory.manufacturePojo(ViviendaEntity.class);
        newViviendaEntity.setNombre(null);
        assertNull(newViviendaEntity.getNombre());

    }

    /*
     * TESTS PARA ENCONTRAR TODAS LAS VIVIENDAS
     */

     @Test
     public void testGetViviendas(){
        List<ViviendaEntity> list = (List<ViviendaEntity>) viviendaService.getViviendas();
        assertEquals(viviendaList.size(), list.size());
        for (ViviendaEntity entity : list){
            boolean found = false;
            for (ViviendaEntity storedEntity : viviendaList){
                if(entity.getId().equals(storedEntity.getId())){
                    found = true;
                }
            }
            assertTrue(found);
        }
     } 
     
     
    /*
     * TESTS PARA ENCONTRAR una VIVIENDAS
     */

     @Test
     public void testGetVivienda() throws EntityNotFoundException, IllegalOperationException{
        ViviendaEntity entity = viviendaList.get(0);
        ViviendaEntity resultEntity = viviendaService.getVivienda(entity.getId());
        assertEquals(entity, resultEntity);
     }

     @Test
    public void testGetInvalidVivienda() {
        assertThrows(EntityNotFoundException.class,()->{
                viviendaService.getVivienda(0L);
        });
    }
    
}
