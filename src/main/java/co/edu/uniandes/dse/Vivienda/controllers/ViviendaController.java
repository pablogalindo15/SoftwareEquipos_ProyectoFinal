package co.edu.uniandes.dse.Vivienda.controllers;

import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.Vivienda.dto.PropietarioDTO;
import co.edu.uniandes.dse.Vivienda.dto.ViviendaDTO;
import co.edu.uniandes.dse.Vivienda.dto.ViviendaDetailDTO;
import co.edu.uniandes.dse.Vivienda.entities.PropietarioEntity;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.Vivienda.services.PropietarioService;
import co.edu.uniandes.dse.Vivienda.services.ViviendaService;

import java.util.Collection;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



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

    @GetMapping(value = "{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ViviendaDTO findOne(@PathVariable("id")Long id) throws EntityNotFoundException, IllegalOperationException{
        ViviendaEntity viviendaEntity = viviendaService.getVivienda(id);
        return modelMapper.map(viviendaEntity, ViviendaDetailDTO.class);

    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Collection<ViviendaDetailDTO> findAll() {
        Collection<ViviendaEntity> viviendas = viviendaService.getViviendas();
        return modelMapper.map(viviendas, new TypeToken<Collection<ViviendaDetailDTO>>(){

        }.getType());
    }

    @PutMapping(value = "{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ViviendaDTO update(@PathVariable("id")Long id, @RequestBody ViviendaDTO viviendaDTO) throws EntityNotFoundException, IllegalOperationException{
        ViviendaEntity viviendaEntity = viviendaService.updateVivienda(id, modelMapper.map(viviendaDTO, ViviendaEntity.class));
        return modelMapper.map(viviendaEntity, ViviendaDTO.class);
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable("id")Long id) throws EntityNotFoundException, IllegalOperationException{
        viviendaService.deleteVivienda(id);;

    }
    
}
