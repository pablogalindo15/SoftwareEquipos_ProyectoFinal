package co.edu.uniandes.dse.Vivienda.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;


import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.context.annotation.Import;


import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity.posiblesEstratos;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity.tipoVivienda;
import co.edu.uniandes.dse.Vivienda.exceptions.*;
import uk.co.jemos.podam.api.*;

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
        for (int i = 0; i < 5; i++) { // Insertar 5 viviendas de ejemplo 
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
        assertTrue(result.getEstrato() instanceof posiblesEstratos);
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

    @Test
    public void testCreateViviendaEntityNoPrecio(){
        assertThrows(IllegalOperationException.class, () -> {
            ViviendaEntity newEntity = factory.manufacturePojo(ViviendaEntity.class);
            newEntity.setPrecio((float) 0);
            viviendaService.createVivienda(newEntity);
        });

    }

    @Test
    public void testCreateViviendaEntityNoDescripcion(){
        assertThrows(IllegalOperationException.class, () -> {
            ViviendaEntity newEntity = factory.manufacturePojo(ViviendaEntity.class);
            newEntity.setDescripcion("");
            viviendaService.createVivienda(newEntity);
        });

    }

    @Test
    public void testCreateViviendaEntityNoFotos(){
        assertThrows(IllegalOperationException.class, () -> {
            ViviendaEntity newEntity = factory.manufacturePojo(ViviendaEntity.class);
            newEntity.setFotos("");
            viviendaService.createVivienda(newEntity);
        });

    }

    @Test
    public void testCreateViviendaEntityNoTamano(){
        assertThrows(IllegalOperationException.class, () -> {
            ViviendaEntity newEntity = factory.manufacturePojo(ViviendaEntity.class);
            newEntity.setTamano(0);
            viviendaService.createVivienda(newEntity);
        });

    }

    @Test
    public void testCreateViviendaEntityNoEstrato(){
        assertThrows(IllegalOperationException.class, () -> {
            ViviendaEntity newEntity = factory.manufacturePojo(ViviendaEntity.class);
            newEntity.setEstrato(null);;
            viviendaService.createVivienda(newEntity);
        });

    }

    @Test
    public void testCreateViviendaEntityNoRestricciones(){
        assertThrows(IllegalOperationException.class, () -> {
            ViviendaEntity newEntity = factory.manufacturePojo(ViviendaEntity.class);
            newEntity.setRestricciones(null);
            viviendaService.createVivienda(newEntity);
        });

    }

    @Test
    public void testCreateViviendaEntityNoContacto(){
        assertThrows(IllegalOperationException.class, () -> {
            ViviendaEntity newEntity = factory.manufacturePojo(ViviendaEntity.class);
            newEntity.setContacto(null);
            viviendaService.createVivienda(newEntity);
        });

    }

    @Test
    public void testCreateViviendaEntityNoDireccion(){
        assertThrows(IllegalOperationException.class, () -> {
            ViviendaEntity newEntity = factory.manufacturePojo(ViviendaEntity.class);
            newEntity.setDireccion("");
            viviendaService.createVivienda(newEntity);
        });

    }

    @Test
    public void testCreateViviendaEntityNoCoordX(){
        assertThrows(IllegalOperationException.class, () -> {
            ViviendaEntity newEntity = factory.manufacturePojo(ViviendaEntity.class);
            newEntity.setCoordX((double) 0);
            viviendaService.createVivienda(newEntity);
        });

    }

    @Test
    public void testCreateViviendaEntityNoCoordY(){
        assertThrows(IllegalOperationException.class, () -> {
            ViviendaEntity newEntity = factory.manufacturePojo(ViviendaEntity.class);
            newEntity.setCoordY((double) 0);
            viviendaService.createVivienda(newEntity);
        });

    }

    @Test
    public void testCreateViviendaEntityNoTipo(){
        assertThrows(IllegalOperationException.class, () -> {
            ViviendaEntity newEntity = factory.manufacturePojo(ViviendaEntity.class);
            newEntity.setEstrato(null);;
            viviendaService.createVivienda(newEntity);
        });

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

     @Test
     public void testGetInvalidViviendas(){
        List<ViviendaEntity> list = (List<ViviendaEntity>) viviendaService.getViviendas();
        assertEquals(viviendaList.size(), list.size());
        for (ViviendaEntity entity : list){
            entity.setId((long) -1);
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

    /*
     * TESTS PARA ACTUALIZAR UNA VIVIENDA
     */

     @Test
     public void testUpdateVivienda() throws EntityNotFoundException, IllegalOperationException{
        ViviendaEntity entity = viviendaList.get(0);
        ViviendaEntity pojoEntity = factory.manufacturePojo(ViviendaEntity.class);
        pojoEntity.setId(entity.getId());
        viviendaService.updateVivienda(entity.getId(), pojoEntity);

        ViviendaEntity resp = entityManager.find(ViviendaEntity.class, entity.getId());
        assertEquals(pojoEntity, resp);
     }

     @Test
    public void testUpdateViviendaInvalid() {
        assertThrows(EntityNotFoundException.class, () -> {
                ViviendaEntity pojoEntity = factory.manufacturePojo(ViviendaEntity.class);
                pojoEntity.setId(0L);
                viviendaService.updateVivienda(0L, pojoEntity);
        });
    }
  
    @Test
    void testUpdateViviendaWithInvalidName() {
        assertThrows(IllegalOperationException.class, () -> {
                ViviendaEntity entity = viviendaList.get(0);
                ViviendaEntity pojoEntity = factory.manufacturePojo(ViviendaEntity.class);
                pojoEntity.setNombre("");
                pojoEntity.setId(entity.getId());
                viviendaService.updateVivienda(entity.getId(), pojoEntity);
        });
    }
    ///////////////////////
    @Test
    public void testUpdateViviendaEntityNoPrecio(){
        assertThrows(IllegalOperationException.class, () -> {
            ViviendaEntity entity = viviendaList.get(0);
            ViviendaEntity pojoEntity = factory.manufacturePojo(ViviendaEntity.class);
            pojoEntity.setPrecio((float) 0);
            pojoEntity.setId(entity.getId());
            viviendaService.updateVivienda(entity.getId(), pojoEntity);
        });

    }

    @Test
    public void testUpdateViviendaEntityNoDescripcion(){
        assertThrows(IllegalOperationException.class, () -> {
            ViviendaEntity entity = viviendaList.get(0);
            ViviendaEntity pojoEntity = factory.manufacturePojo(ViviendaEntity.class);
            pojoEntity.setId(entity.getId());
            pojoEntity.setDescripcion("");
            viviendaService.updateVivienda(entity.getId(), pojoEntity);
        });

    }

    @Test
    public void testUpdateViviendaEntityNoFotos(){
        assertThrows(IllegalOperationException.class, () -> {            
            ViviendaEntity entity = viviendaList.get(0);
            ViviendaEntity pojoEntity = factory.manufacturePojo(ViviendaEntity.class);
            pojoEntity.setId(entity.getId());
            pojoEntity.setFotos("");
            viviendaService.updateVivienda(entity.getId(), pojoEntity);
        });

    }

    @Test
    public void testUpdateViviendaEntityNoTamano(){
        assertThrows(IllegalOperationException.class, () -> {
            ViviendaEntity entity = viviendaList.get(0);
            ViviendaEntity pojoEntity = factory.manufacturePojo(ViviendaEntity.class);
            pojoEntity.setId(entity.getId());
            pojoEntity.setTamano(0);
            viviendaService.updateVivienda(entity.getId(), pojoEntity);
        });

    }

    @Test
    public void testUpdateViviendaEntityNoEstrato(){
        assertThrows(IllegalOperationException.class, () -> {
            ViviendaEntity entity = viviendaList.get(0);
            ViviendaEntity pojoEntity = factory.manufacturePojo(ViviendaEntity.class);
            pojoEntity.setId(entity.getId());
            pojoEntity.setEstrato(null);;
            viviendaService.updateVivienda(entity.getId(), pojoEntity);
        });

    }

    @Test
    public void testUpdateViviendaEntityNoRestricciones(){
        assertThrows(IllegalOperationException.class, () -> {
            ViviendaEntity entity = viviendaList.get(0);
            ViviendaEntity pojoEntity = factory.manufacturePojo(ViviendaEntity.class);
            pojoEntity.setId(entity.getId());
            pojoEntity.setRestricciones(null);
            viviendaService.updateVivienda(entity.getId(), pojoEntity);
        });

    }

    @Test
    public void testUpdateViviendaEntityNoContacto(){
        assertThrows(IllegalOperationException.class, () -> {
            ViviendaEntity entity = viviendaList.get(0);
            ViviendaEntity pojoEntity = factory.manufacturePojo(ViviendaEntity.class);
            pojoEntity.setId(entity.getId());
            pojoEntity.setContacto(null);
            viviendaService.updateVivienda(entity.getId(), pojoEntity);
        });

    }

    @Test
    public void testUpdateViviendaEntityNoDireccion(){
        assertThrows(IllegalOperationException.class, () -> {
            ViviendaEntity entity = viviendaList.get(0);
            ViviendaEntity pojoEntity = factory.manufacturePojo(ViviendaEntity.class);
            pojoEntity.setId(entity.getId());
            pojoEntity.setDireccion("");
            viviendaService.updateVivienda(entity.getId(), pojoEntity);
        });

    }

    @Test
    public void testUpdateViviendaEntityNoCoordX(){
        assertThrows(IllegalOperationException.class, () -> {
            ViviendaEntity entity = viviendaList.get(0);
            ViviendaEntity pojoEntity = factory.manufacturePojo(ViviendaEntity.class);
            pojoEntity.setId(entity.getId());
            pojoEntity.setCoordX((double) 0);
            viviendaService.updateVivienda(entity.getId(), pojoEntity);
        });

    }

    @Test
    public void testUpdateViviendaEntityNoCoordY(){
        assertThrows(IllegalOperationException.class, () -> {
            ViviendaEntity entity = viviendaList.get(0);
            ViviendaEntity pojoEntity = factory.manufacturePojo(ViviendaEntity.class);
            pojoEntity.setId(entity.getId());
            pojoEntity.setCoordY((double) 0);
            viviendaService.updateVivienda(entity.getId(), pojoEntity);
        });

    }

    @Test
    public void testUpdateViviendaEntityNoTipo(){
        assertThrows(IllegalOperationException.class, () -> {
            ViviendaEntity entity = viviendaList.get(0);
            ViviendaEntity pojoEntity = factory.manufacturePojo(ViviendaEntity.class);
            pojoEntity.setId(entity.getId());
            pojoEntity.setEstrato(null);;
            viviendaService.updateVivienda(entity.getId(), pojoEntity);
        });

    }

    //////////////////////
    /*
     * TESTS PARA BORRAR VIVIENDA
     */
    
     @Test
     void testDeleteVivienda() throws EntityNotFoundException, IllegalOperationException {
             ViviendaEntity entity = viviendaList.get(1);
             viviendaService.deleteVivienda(entity.getId());
             ViviendaEntity deleted = entityManager.find(ViviendaEntity.class, entity.getId());
             assertNull(deleted);
     }

     @Test
    void testDeleteInvalidVivienda() {
        assertThrows(EntityNotFoundException.class, ()->{
                viviendaService.deleteVivienda(0L);
        });
    }


}
