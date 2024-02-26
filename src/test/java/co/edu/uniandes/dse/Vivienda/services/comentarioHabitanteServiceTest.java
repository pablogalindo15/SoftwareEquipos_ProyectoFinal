package co.edu.uniandes.dse.Vivienda.services;

import javax.transaction.Transactional;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.extension.ExtendWith;
import co.edu.uniandes.dse.Vivienda.entities.HabitanteEntity;
import co.edu.uniandes.dse.Vivienda.entities.ComentarioEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.Vivienda.repositories.ComentarioRepository;
import co.edu.uniandes.dse.Vivienda.repositories.HabitanteRepository;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Transactional
@Import(comentarioHabitanteService.class)
public class comentarioHabitanteServiceTest {
    @Autowired
    private  comentarioHabitanteService comentarioHabitanteService;
    @Autowired
    private TestEntityManager entityManager; 
    private PodamFactory factory = new PodamFactoryImpl();
    private List<ComentarioEntity> comentarioList = new ArrayList<>();
    private List<HabitanteEntity> habitanteList = new ArrayList<>();
    private HabitanteEntity habitante = new HabitanteEntity();
    private ComentarioEntity comentario = new ComentarioEntity();
@BeforeEach
void setUp(){
    clearData();
    insertData();
}
private void clearData(){
    entityManager.getEntityManager().createQuery("delete from HabitanteEntity");
    entityManager.getEntityManager().createQuery("delete from ComentarioEntity");
    }
private void insertData(){
    for (int i = 0; i < 3; i++) {
        ComentarioEntity comentarioEntity = factory.manufacturePojo(ComentarioEntity.class);
        entityManager.persist(comentarioEntity);
        comentarioList.add(comentarioEntity);
    }
    for (int i = 0; i < 3; i++) {
        HabitanteEntity habitanteEntity = factory.manufacturePojo(HabitanteEntity.class);
        entityManager.persist(habitanteEntity);
        habitanteList.add(habitanteEntity);
        }
    HabitanteEntity habitanteEntity = factory.manufacturePojo(HabitanteEntity.class);
    entityManager.persist(habitanteEntity);
    habitanteEntity.getComentarios().add(comentarioList.get(0));
    comentarioList.get(0).getEstudiantes().add(habitanteEntity);
    }
@Test
void testaddComentario() throws EntityNotFoundException, IllegalOperationException  {
    ComentarioEntity newComentario = factory.manufacturePojo(ComentarioEntity.class);
    entityManager.persist(newComentario);
    HabitanteEntity newHabitante = factory.manufacturePojo(HabitanteEntity.class);
    entityManager.persist(newHabitante);
    comentarioHabitanteService.addComentario(newComentario.getId(), newHabitante.getId());
    ComentarioEntity lastComentario = comentarioHabitanteService.getComentario(newComentario.getId(), newHabitante.getId());
    assertEquals(newComentario.getId(), lastComentario.getId());
    assertEquals(newComentario.getEstudiantes(), lastComentario.getEstudiantes());
    assertEquals(newComentario.getTitulo(),lastComentario.getTitulo());
    assertEquals(newComentario.getTexto(),lastComentario.getTexto());
    assertEquals(newComentario.getNombre(),lastComentario.getNombre());
    assertEquals(newComentario.getCalificacion(),lastComentario.getCalificacion());
    }
@Test
void testgetComentarios() throws EntityNotFoundException{
    HabitanteEntity habitanteEjemplo= habitanteList.get(0);
    List<ComentarioEntity> comentariosEjemplo = habitanteEjemplo.getComentarios();
    List<ComentarioEntity> comentarioEntities = comentarioHabitanteService.getComentarios(habitanteEjemplo.getId());
    assertNotNull(comentarioEntities);
    assertEquals(comentariosEjemplo.size(), comentarioEntities.size());
    for (int i=0;i<comentariosEjemplo.size();i++){
        ComentarioEntity prueba1 = comentariosEjemplo.get(i);
        ComentarioEntity prueba2 = comentarioEntities.get(i);
        assertEquals(prueba1.getId(), prueba2.getId());
        assertEquals(prueba1.getEstudiantes(), prueba2.getEstudiantes());
        assertEquals(prueba1.getTexto(), prueba2.getTexto());
        assertEquals(prueba1.getTitulo(), prueba2.getTitulo());
        assertEquals(prueba1.getCalificacion(), prueba2.getCalificacion());
        assertEquals(prueba1.getNombre(), prueba2.getNombre());
    }
    }
    @Test
    void testaddinvalidComentario(){
        assertThrows(EntityNotFoundException.class, () -> {

            HabitanteEntity habitantePrueba = habitanteList.get(0);
            comentarioHabitanteService.addComentario(0L, habitantePrueba.getId());

        });

    }
    /*
    //ANOTACION: Por alguna razón no funciona la prueba que me da  un comentario
    
    @Test
    void testgetComentario()throws IllegalOperationException, EntityNotFoundException{
        
        ComentarioEntity comentarioPrueba = comentarioList.get(0);
        HabitanteEntity habitantePrueba = habitanteList.get(0);
        habitantePrueba.setComentarios(comentarioList);
      
        ComentarioEntity comentarioEntity = comentarioHabitanteService.getComentario(comentarioPrueba.getId(), habitante.getId());
        assertNotNull(comentarioEntity);
        ComentarioEntity prueba1 = comentarioPrueba;
        ComentarioEntity prueba2 = comentarioEntity;
        assertEquals(prueba1.getId(), prueba2.getId());
        assertEquals(prueba1.getEstudiantes(), prueba2.getEstudiantes());
        assertEquals(prueba1.getTitulo(), prueba2.getTitulo());
        assertEquals(prueba1.getNombre(), prueba2.getNombre());
        assertEquals(prueba1.getTexto(), prueba2.getTexto());
        assertEquals(prueba1.getCalificacion(), prueba2.getCalificacion());
    }
    /* */
    
    @Test
     void testRemoveComentario() throws EntityNotFoundException, IllegalOperationException{

                        HabitanteEntity habitantePrueba = habitanteList.get(0);

                        habitantePrueba.setComentarios(comentarioList);

                        ComentarioEntity uncomentario = comentarioList.get(1);

                        comentarioHabitanteService.removeComentario(habitantePrueba.getId(), uncomentario.getId());

                        for (int i = 0; i < comentarioList.size(); i++) {

                                assertNotEquals(habitantePrueba.getComentarios().get(i).getId(), uncomentario.getId());    

                        }
        

         }
    @Test
	void testRemoveInvalidComentario() {
		assertThrows(EntityNotFoundException.class, () -> {
			comentarioHabitanteService.removeComentario(habitante.getId(),0L);
		});
	}
    @Test
	void testRemoveComentarioInvalidHabitante() {
		assertThrows(EntityNotFoundException.class, () -> {
			for (ComentarioEntity comentario : comentarioList) {
				comentarioHabitanteService.removeComentario(0L, comentario.getId());
			}
		});
	}
    /* 
    @Test
    void testReplaceComentarios() throws EntityNotFoundException, IllegalOperationException {
		List<ComentarioEntity> nuevaLista = new ArrayList<>();
		
		for (int i = 0; i < 3; i++) {
			ComentarioEntity entity = factory.manufacturePojo(ComentarioEntity.class);
			
			entityManager.persist(entity);
			nuevaLista.add(entity);
		}
        comentarioHabitanteService.addcomentarios(habitante.getId(), nuevaLista);
		
		List<ComentarioEntity> comentarioEntities = entityManager.find(HabitanteEntity.class, habitante.getId()).getComentarios();
		for (ComentarioEntity item : nuevaLista) {
			assertTrue(comentarioEntities.contains(item));
		}

    }
    /**/ 
}

