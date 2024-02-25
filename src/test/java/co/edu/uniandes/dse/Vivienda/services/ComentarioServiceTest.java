package co.edu.uniandes.dse.Vivienda.services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import co.edu.uniandes.dse.Vivienda.entities.ComentarioEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(ComentarioService.class)
@Transactional
public class ComentarioServiceTest{
@Autowired
private ComentarioService comentarioService;
@Autowired
private TestEntityManager entityManager; 
private List<ComentarioEntity> comentarioList = new ArrayList<>();


private PodamFactory factory = new PodamFactoryImpl();  
private List<ComentarioEntity> comentariolist= new ArrayList<>();
@BeforeEach
void setUp() {
    clearData();
    insertData();
        }
private void clearData() {
        entityManager.getEntityManager().createQuery("delete from ComentarioEntity");
}
private void insertData(){
    
        ComentarioEntity comentarioEntity = factory.manufacturePojo(ComentarioEntity.class);
        
        entityManager.persist(comentarioEntity);
        comentarioList.add(comentarioEntity);


}
@Test
void testCreateComentario() throws EntityNotFoundException, IllegalOperationException{
    ComentarioEntity newEntity= factory.manufacturePojo(ComentarioEntity.class);
    
    newEntity.setTexto("Texto de ejemplo");
    newEntity.setTitulo("El ejemplo");
    newEntity.setNombre("comentario");
    ComentarioEntity result = comentarioService.crear(newEntity);
    assertNotNull(result);


}
@Test
void testgetComentarios(){
    List<ComentarioEntity> list = comentarioService.getcomentarios();
    assertEquals(comentarioList.size(),list.size());
    for (ComentarioEntity entity :list){
        boolean found =false;
        for (ComentarioEntity storedEntity : comentarioList){
            if (entity.getId().equals(storedEntity.getId())){
                found = true;

            }
        

            
        }
        assertTrue(found);
    }

}
@Test
void testGetComentario() throws EntityNotFoundException{
    ComentarioEntity entity = comentarioList.get(0);
    ComentarioEntity resultEntity = comentarioService.getComentario(entity.getId());
    assertNotNull(resultEntity);
    assertEquals(entity.getId(), resultEntity.getId());
    assertEquals(entity.getNombre(), resultEntity.getNombre());
    assertEquals(entity.getTexto(), resultEntity.getTexto());
    assertEquals(entity.getTitulo(), resultEntity.getTitulo());
}
@Test
void testDeleteComentario() throws EntityNotFoundException, IllegalOperationException{
    ComentarioEntity entity = comentarioList.get(0);
    comentarioService.deleteComentario(entity.getId());
    ComentarioEntity deleted = entityManager.find(ComentarioEntity.class, entity.getId());
    assertNull(deleted);
    }
@Test
void testUpdateAuthor() throws EntityNotFoundException, IllegalOperationException {
		ComentarioEntity comentarioEntity = comentarioList.get(0);
		ComentarioEntity pojoEntity = factory.manufacturePojo(ComentarioEntity.class);

		pojoEntity.setId(comentarioEntity.getId());

		comentarioService.updateComentario(comentarioEntity.getId(), pojoEntity);

		ComentarioEntity response = entityManager.find(ComentarioEntity.class, comentarioEntity.getId());

		assertEquals(pojoEntity.getId(), response.getId());
		assertEquals(pojoEntity.getNombre(), response.getNombre());
		assertEquals(pojoEntity.getTexto(), response.getTexto());
		assertEquals(pojoEntity.getTitulo(), response.getTitulo());
        assertEquals(pojoEntity.getCalificacion(), response.getCalificacion());
        
	}
}