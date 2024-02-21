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

import co.edu.uniandes.dse.Vivienda.entities.LugarEntity;
import co.edu.uniandes.dse.Vivienda.entities.ServicioEntity;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity.posiblesEstratos;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity.tipoVivienda;
import co.edu.uniandes.dse.Vivienda.exceptions.*;
import uk.co.jemos.podam.api.*;



@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(ServicioService.class)

public class ServicioServiceTest {

    @Autowired
        private ServicioService servicio;

        @Autowired
        private TestEntityManager entityManager;

        private PodamFactory factory = new PodamFactoryImpl();

        private List<ServicioEntity> servicioList = new ArrayList<>();
        

        /**
         * Configuración inicial de la prueba.
         */
        @BeforeEach
        void setUp() {
                clearData();
                insertData();
        }

        /**
         * Limpia las tablas que están implicadas en la prueba.
         */
        private void clearData() {
            entityManager.getEntityManager().createQuery("delete from LugarEntity");
                
        }

        private void insertData(){
            for (int i = 0; i<3 ; i++){
                ServicioEntity servicioEntity = factory.manufacturePojo(ServicioEntity.class);
                entityManager.persist(servicioEntity);
                servicioList.add(servicioEntity);
            }

            ViviendaEntity viviendaEntity = factory.manufacturePojo(ViviendaEntity.class);
            entityManager.persist(viviendaEntity);
            viviendaEntity.getServicios().add(servicioList.get(0));
            //servicioList.get(0).getViviendasServicio().add(viviendaEntity);
        }
    

        @Test
        void testCreateServicio() throws EntityNotFoundException, IllegalOperationException {
            ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
            ServicioEntity result = servicio.crearServicio(newEntity);
            assertNotNull(result);
            ServicioEntity entity = entityManager.find(ServicioEntity.class, result.getId());
            assertEquals(newEntity.getId(), entity.getId());
            assertEquals(newEntity.getCostoAdicional(), entity.getCostoAdicional());
            assertEquals(newEntity.getNombre(), entity.getNombre());
            
        }
        @Test   
        void testCreateServicioNombreVacio() {
                assertThrows(IllegalOperationException.class, () -> {
                ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
                newEntity.setNombre("");
                servicio.crearServicio(newEntity);
            });
        }   

        @Test
        void testCreateServicioNombreinvalido() {
            assertThrows(IllegalOperationException.class, () -> {
            ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
            newEntity.setNombre("hola");
            servicio.crearServicio(newEntity);
        });
        }   
        @Test
        void testDeleteServicioInvalido() {
            assertThrows(EntityNotFoundException.class, ()->{
                servicio.deleteServicio(0L );
        });
        }
        @Test
        void testUpdateServicio() throws EntityNotFoundException, IllegalOperationException {
            ServicioEntity entity = servicioList.get(0);
            ServicioEntity pojoEntity = factory.manufacturePojo(ServicioEntity.class);
            pojoEntity.setId(entity.getId());
            servicio.updateServicio(entity.getId(), pojoEntity);

            ServicioEntity resp = entityManager.find(ServicioEntity.class, entity.getId());
            assertEquals(pojoEntity.getId(), resp.getId());
            assertEquals(pojoEntity.getCostoAdicional(), resp.getCostoAdicional());
            assertEquals(pojoEntity.getNombre(), resp.getNombre());
            assertEquals(pojoEntity.getTipo(), resp.getTipo());

        }   
        @Test
        void testGetLugar() throws EntityNotFoundException {
            ServicioEntity entity = servicioList.get(0);
            ServicioEntity resultEntity = servicio.getServicio(entity.getId());
            assertNotNull(resultEntity);
            assertEquals(entity.getId(), resultEntity.getId());
            assertEquals(entity.getCostoAdicional(), resultEntity.getCostoAdicional());
            assertEquals(entity.getNombre(), resultEntity.getNombre());
            assertEquals(entity.getTipo(), resultEntity.getTipo());
        
        }
        @Test 
        void testCreateServiciocostoNegativo() {
            assertThrows(IllegalOperationException.class, () -> {
            ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
                float costo = -(newEntity.getCostoAdicional());
                newEntity.setCostoAdicional(costo);
                servicio.crearServicio(newEntity);
            });
        }
        @Test
        void testCreateServicioCostoNull() {
            assertThrows(IllegalOperationException.class, () -> {
            ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
                newEntity.setCostoAdicional(null);
                servicio.crearServicio(newEntity);
            });
        }




    
}


    
     

 