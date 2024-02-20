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
        private ServicioService servicioService;

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
                entityManager.getEntityManager().createQuery("delete from BookEntity");
                entityManager.getEntityManager().createQuery("delete from EditorialEntity");
                entityManager.getEntityManager().createQuery("delete from AuthorEntity");
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
        private void testCreateServicio() throws EntityNotFoundException, IllegalOperationException {
            ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
            ServicioEntity result = ServicioService.crearServicio(newEntity);
            assertNotNull(result);
            ServicioEntity entity = entityManager.find(ServicioEntity.class, result.getId());
        }

        @Test   
        void testCreateServicioNombreinvalido() {
                assertThrows(IllegalOperationException.class, () -> {
                ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
                newEntity.setNombre("");
                servicioService.crearServicio(newEntity);
            });
        }   

        @Test
        void testCreateServicioID() {
                assertThrows(IllegalOperationException.class, () -> {
                ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
                newEntity.setId(servicioList.get(0).getId());
                servicioService.crearServicio(newEntity);
        });
    }

        @Test
        void testGetServicio() throws EntityNotFoundException {
            ServicioEntity entity = servicioList.get(0);
            ServicioEntity resultEntity = servicioService.getServicio(entity.getId());
            assertNotNull(resultEntity);
            assertEquals(entity.getId(), resultEntity.getId());
            assertEquals(entity.getCostoAdicional(), resultEntity.getCostoAdicional());
            assertEquals(entity.getNombre(), resultEntity.getNombre());
            assertEquals(entity.getViviendasServicio(), resultEntity.getViviendasServicio());
    }

        @Test
        void testUpdateServicio() throws EntityNotFoundException, IllegalOperationException {
            ServicioEntity entity = servicioList.get(0);
            ServicioEntity pojoEntity = factory.manufacturePojo(ServicioEntity.class);
            pojoEntity.setId(entity.getId());
            servicioService.updateServicio(entity.getId(), pojoEntity);

            ServicioEntity resp = entityManager.find(ServicioEntity.class, entity.getId());
            assertEquals(pojoEntity.getId(), resp.getId());
            assertEquals(pojoEntity.getCostoAdicional(), resp.getCostoAdicional());
            assertEquals(pojoEntity.getNombre(), resp.getNombre());
            assertEquals(pojoEntity.getViviendasServicio(), resp.getViviendasServicio());
        }

        @Test
        void testDeleteServicio() throws EntityNotFoundException, IllegalOperationException {
            ServicioEntity entity = servicioList.get(1);
            servicioService.deleteServicio(entity.getId());
            ServicioEntity deleted = entityManager.find(ServicioEntity.class, entity.getId());
            assertNull(deleted);

        }

        @Test
        void testDeleteServicioInvalido() {
            assertThrows(EntityNotFoundException.class, ()->{
                servicioService.deleteServicio(0L);
        });
}

}
    
    

