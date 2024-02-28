package co.edu.uniandes.dse.Vivienda.services;

import java.util.Optional;
import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.Vivienda.entities.ComentarioEntity;
import co.edu.uniandes.dse.Vivienda.entities.HabitanteEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.Vivienda.repositories.ComentarioRepository;
import co.edu.uniandes.dse.Vivienda.repositories.HabitanteRepository;
@Service
public class comentarioHabitanteService {
    @Autowired
    private HabitanteRepository habitanteRepository;
    @Autowired
    private ComentarioRepository comentarioRepository;
    @Transactional
        public ComentarioEntity addComentario(Long comentarioid, Long habitanteid ) throws EntityNotFoundException{
                Optional<ComentarioEntity> comentarioEntity = comentarioRepository.findById(comentarioid);
                if(comentarioEntity.isEmpty()){
                throw new EntityNotFoundException("no se encontró");
                }
                Optional<HabitanteEntity> habitanteEntity = habitanteRepository.findById(habitanteid);
                if(habitanteEntity.isEmpty()){
                throw new EntityNotFoundException("no se encontró");
                }
                habitanteEntity.get().getComentarios().add(comentarioEntity.get());
                comentarioEntity.get().getEstudiantes().add(habitanteEntity.get());
        return comentarioEntity.get();
        } 
        public ComentarioEntity getComentario(Long comentarioid, Long habitanteid)  throws EntityNotFoundException, IllegalOperationException{
                Optional<ComentarioEntity> comentarioEntity = comentarioRepository.findById(comentarioid);
                Optional<HabitanteEntity> habitanteEntity = habitanteRepository.findById(habitanteid);
                if (comentarioEntity.isEmpty()){
                        throw new EntityNotFoundException("no se encontró");
                }
                if (habitanteEntity.isEmpty()){
                        throw new EntityNotFoundException("no se encontró");
                }
                if(!comentarioEntity.get().getEstudiantes().contains(habitanteEntity.get())){
                        throw new EntityNotFoundException("no se encontró el vaino");
                }
                return comentarioEntity.get();
        }
        public List<ComentarioEntity> getComentarios(Long habitanteid) throws EntityNotFoundException{
                Optional<HabitanteEntity> habitanteEntity = habitanteRepository.findById(habitanteid);
                if (habitanteEntity.isEmpty()){
                        throw new EntityNotFoundException("no se encontró");
                }
                return habitanteEntity.get().getComentarios();
        }   
        public void removeComentario(Long habitanteId, Long comentarioId) throws EntityNotFoundException {
		
		Optional<ComentarioEntity> comentarioEntity = comentarioRepository.findById(comentarioId);
		if (comentarioEntity.isEmpty())
			throw new EntityNotFoundException("no se encontró");

		Optional<HabitanteEntity> habitanteEntity = habitanteRepository.findById(habitanteId);
		if (habitanteEntity.isEmpty())
			throw new EntityNotFoundException("no hay");

		comentarioEntity.get().getEstudiantes().remove(habitanteEntity.get());
		habitanteEntity.get().getComentarios().remove(comentarioEntity.get());
		
	}
        public List<ComentarioEntity> addcomentarios(Long habitanteId, List<ComentarioEntity> comentarios) throws EntityNotFoundException {
		
		Optional<HabitanteEntity> habitanteEntity = habitanteRepository.findById(habitanteId);
		if (habitanteEntity.isEmpty())
			throw new EntityNotFoundException("no hay");

		for (ComentarioEntity comentario : comentarios) {
			Optional<ComentarioEntity> comentarioEntity = comentarioRepository.findById(comentario.getId());
			if (comentarioEntity.isEmpty())
				throw new EntityNotFoundException("no hay");

			if (!comentarioEntity.get().getEstudiantes().contains(habitanteEntity.get()))
				comentarioEntity.get().getEstudiantes().add(habitanteEntity.get());
		}
		
		habitanteEntity.get().setComentarios(comentarios);
		return habitanteEntity.get().getComentarios();
   
        }
}
