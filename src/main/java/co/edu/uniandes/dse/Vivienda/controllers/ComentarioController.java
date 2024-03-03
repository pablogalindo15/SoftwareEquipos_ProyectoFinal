package co.edu.uniandes.dse.Vivienda.controllers;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import co.edu.uniandes.dse.Vivienda.entities.ComentarioEntity;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.Vivienda.services.ComentarioService;
import org.modelmapper.TypeToken;

import co.edu.uniandes.dse.Vivienda.dto.ComentarioDTO;
import co.edu.uniandes.dse.Vivienda.dto.ComentarioDetailDTO;
import co.edu.uniandes.dse.Vivienda.dto.ViviendaDTO;
import co.edu.uniandes.dse.Vivienda.dto.ViviendaDetailDTO;
@RestController
@RequestMapping("/comentario")
public class ComentarioController {
@Autowired
private ComentarioService comentarioService;
@Autowired
private ModelMapper modelMapper;
    
@GetMapping
@ResponseStatus(code = HttpStatus.OK)
public List<ComentarioDetailDTO> findall(){
    List<ComentarioEntity> comentarios = comentarioService.getcomentarios();
    return modelMapper.map(comentarios,new TypeToken<List<ComentarioDetailDTO>>(){

    }.getType());
}
    @PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ComentarioDTO create(@RequestBody ComentarioDTO comentarioDTO) throws IllegalOperationException, EntityNotFoundException {
		 ComentarioEntity comentarioEntity = comentarioService.crear(modelMapper.map( comentarioDTO, ComentarioEntity.class));
		return modelMapper.map(comentarioEntity, ComentarioDTO.class);
	}
@GetMapping(value = "{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ComentarioDTO findOne(@PathVariable("id")Long id) throws EntityNotFoundException, IllegalOperationException{
        ComentarioEntity comentarioEntity = comentarioService.getComentario(id);
        return modelMapper.map(comentarioEntity, ComentarioDetailDTO.class);

    }
	@PutMapping(value = "{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ComentarioDTO update(@PathVariable("id")Long id, @RequestBody ComentarioDTO comentarioDTO) throws EntityNotFoundException, IllegalOperationException{
        ComentarioEntity comentarioEntity = comentarioService.updateComentario(id, modelMapper.map(comentarioDTO, ComentarioEntity.class));
        return modelMapper.map(comentarioEntity, ComentarioDTO.class);
    }
	@DeleteMapping(value = "{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable("id")Long id) throws EntityNotFoundException, IllegalOperationException{
        comentarioService.deleteComentario(id);;

    }
}
