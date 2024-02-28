package co.edu.uniandes.dse.Vivienda.controllers;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import co.edu.uniandes.dse.Vivienda.entities.ComentarioEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.Vivienda.services.ComentarioService;
import org.modelmapper.TypeToken;

import co.edu.uniandes.dse.Vivienda.dto.ComentarioDTO;
import co.edu.uniandes.dse.Vivienda.dto.ComentarioDetailDTO;
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
}
