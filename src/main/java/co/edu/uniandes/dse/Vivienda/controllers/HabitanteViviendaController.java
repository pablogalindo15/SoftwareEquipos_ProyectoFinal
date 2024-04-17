package co.edu.uniandes.dse.Vivienda.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import co.edu.uniandes.dse.Vivienda.dto.ViviendaDetailDTO;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import org.springframework.http.HttpStatus;
import co.edu.uniandes.dse.Vivienda.services.HabitanteViviendaService;
import org.springframework.web.bind.annotation.RequestBody;
import co.edu.uniandes.dse.Vivienda.dto.ViviendaDTO;



@RestController
@RequestMapping("/habitantes")
public class HabitanteViviendaController {
    
    @Autowired
    private HabitanteViviendaService habitanteViviendaService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/{HabitanteId}/viviendas/{viviendaId}")
    public ViviendaDetailDTO addVivienda(@PathVariable("habitanteId") Long habitanteId, @PathVariable("viviendaId") Long viviendaId) throws EntityNotFoundException, IllegalOperationException {
        ViviendaEntity viviendaEntity = habitanteViviendaService.addVivienda(habitanteId, viviendaId);
        return modelMapper.map(viviendaEntity, ViviendaDetailDTO.class);

    }

    @GetMapping("/{habitanteId}/viviendas/{viviendaId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ViviendaDetailDTO getVivienda(@PathVariable("habitanteId") Long habitanteId, @PathVariable("viviendaId") Long viviendaId) throws EntityNotFoundException, IllegalOperationException {
            ViviendaEntity viviendaEntity= habitanteViviendaService.getVivienda(habitanteId);
        return modelMapper.map(viviendaEntity, ViviendaDetailDTO.class);
    }

    @GetMapping("/{habitanteId}/viviendas")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ViviendaEntity> getViviendas(@PathVariable("habitanteId") Long habitanteId) throws EntityNotFoundException {
        List<ViviendaEntity> viviendaEntity = habitanteViviendaService.getViviendas(habitanteId);
        
        return modelMapper.map(viviendaEntity,  new TypeToken<List<ViviendaDetailDTO>>() {
        }.getType());
    
    }

    @PutMapping("/{habitanteId}/viviendas")
    public List<ViviendaDetailDTO> replaceVivienda(@PathVariable("habitanteId") Long habitanteId, @RequestBody List<ViviendaDTO> viviendas) throws EntityNotFoundException {

        List<ViviendaEntity> entities = modelMapper.map(viviendas, new TypeToken<List<ViviendaEntity>>() {
        }.getType());

        List<ViviendaEntity> viviendasList = habitanteViviendaService.replaceViviendas(habitanteId, entities);

        return modelMapper.map(viviendasList,  new TypeToken<List<ViviendaDetailDTO>>() {
        }.getType());
    }

}






