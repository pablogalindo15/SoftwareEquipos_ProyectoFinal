package co.edu.uniandes.dse.Vivienda.controllers;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import co.edu.uniandes.dse.Vivienda.dto.ComentarioDetailDTO;

import co.edu.uniandes.dse.Vivienda.services.comentarioHabitanteService;

import co.edu.uniandes.dse.Vivienda.entities.ComentarioEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;

@RestController
@RequestMapping("/habitantes")

public class ComentarioHabitanteController {

    @Autowired
    private comentarioHabitanteService comentarioHabitanteService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/{habitanteId}/comentario/{comentarioId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ComentarioDetailDTO getComentario(@PathVariable("comentarioId") Long comentarioId, @PathVariable("habitanteId") Long habitanteId)
            throws EntityNotFoundException, IllegalOperationException {
        ComentarioEntity comentarioEntity = comentarioHabitanteService.getComentario(comentarioId, habitanteId);
        return modelMapper.map(comentarioEntity, ComentarioDetailDTO.class);
    }

    @GetMapping(value = "/{habitanteId}/comentario")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ComentarioDetailDTO> getComentarios(@PathVariable("habitanteId") Long habitanteId) throws EntityNotFoundException {
        List<ComentarioEntity> comentarioEntity = comentarioHabitanteService.getComentarios(habitanteId);
        return modelMapper.map(comentarioEntity, new TypeToken<List<ComentarioDetailDTO>>() {
        }.getType());
    }

    @PostMapping(value = "/{habitanteId}/comentario/{comentarioId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ComentarioDetailDTO addComentario(@PathVariable("habitanteId") Long habitanteId, @PathVariable("comentarioId") Long comentarioId)
            throws EntityNotFoundException {
        ComentarioEntity comentarioEntity = comentarioHabitanteService.addComentario(comentarioId, habitanteId);
        return modelMapper.map(comentarioEntity, ComentarioDetailDTO.class);
    }


    @DeleteMapping(value = "/{habitanteId}/comentario/{comentarioId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeComentario(@PathVariable("habitanteId") Long habitanteId, @PathVariable("comentarioId") Long comentarioId)
            throws EntityNotFoundException {
        comentarioHabitanteService.removeComentario(habitanteId, comentarioId);
    }
}

