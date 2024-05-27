package co.edu.uniandes.dse.Vivienda.services;
import javax.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import co.edu.uniandes.dse.Vivienda.entities.ComentarioEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.Vivienda.repositories.ComentarioRepository;
import co.edu.uniandes.dse.Vivienda.repositories.HabitanteRepository;



@Service
public class ComentarioService {
    @Autowired
    private ComentarioRepository comentarioRepository;
    @Autowired
    private HabitanteRepository habitanteRepository;
    @Transactional
    public ComentarioEntity crear(ComentarioEntity nuevo) throws EntityNotFoundException,IllegalOperationException{
        if (nuevo.getTexto()== null){
                throw new IllegalOperationException("El comentario no tiene texto");
        }
        return comentarioRepository.save(nuevo);
    }

    public List<ComentarioEntity> getcomentarios(){
        return comentarioRepository.findAll();
    }
    
    public ComentarioEntity getComentario(Long comentarioid) throws EntityNotFoundException{
        java.util.Optional<ComentarioEntity> comentarioEntity = comentarioRepository.findById(comentarioid);
        if (comentarioEntity.isEmpty()){
                throw new EntityNotFoundException("no se encontró el comentario");
        }
        return comentarioEntity.get();
    }
    public ComentarioEntity updateComentario(Long comentarioid, ComentarioEntity comentario) throws EntityNotFoundException, IllegalOperationException{
            
        java.util.Optional<ComentarioEntity> comentarioEntity = comentarioRepository.findById(comentarioid);
        if (comentarioEntity.isEmpty()){
                throw new EntityNotFoundException("no se encontó el libro");
        }
        comentario.setId(comentarioid);
        return comentarioRepository.save(comentario);
        
    }
    public void deleteComentario(Long comentarioid) throws EntityNotFoundException, IllegalOperationException {
        java.util.Optional<ComentarioEntity> comentarioEntity = comentarioRepository.findById(comentarioid);
        if (comentarioEntity.isEmpty()){
            throw new EntityNotFoundException("no se encontó el libro");
    }
    comentarioRepository.deleteById(comentarioid);
    }
    
    public Page<ComentarioEntity> getComentariosPaginados(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return comentarioRepository.findAll(pageable);
    }
    
}
