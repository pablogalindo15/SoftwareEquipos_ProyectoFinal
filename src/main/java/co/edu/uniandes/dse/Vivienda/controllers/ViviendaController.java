package co.edu.uniandes.dse.Vivienda.controllers;

import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.Vivienda.dto.ViviendaDTO;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.Vivienda.services.ViviendaService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
@RequestMapping("/viviendas")
public class ViviendaController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ViviendaService viviendaService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ViviendaDTO create(@RequestBody ViviendaDTO viviendaDTO) throws EntityNotFoundException, IllegalOperationException{
        ViviendaEntity viviendaEntity = viviendaService.createVivienda(modelMapper.map(viviendaDTO, ViviendaEntity.class));
        return modelMapper.map(viviendaEntity, ViviendaDTO.class);

    }
}
