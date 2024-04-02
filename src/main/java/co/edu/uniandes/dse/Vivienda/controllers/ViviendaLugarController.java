package co.edu.uniandes.dse.Vivienda.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.Vivienda.dto.LugarDTO;
import co.edu.uniandes.dse.Vivienda.dto.LugarDetailDTO;
import co.edu.uniandes.dse.Vivienda.entities.LugarEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.Vivienda.services.ViviendaLugarService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/viviendas")
public class ViviendaLugarController {

    @Autowired
    private ViviendaLugarService viviendaLugarService;

    @Autowired
    private ModelMapper modelMapper;

    
    @PostMapping("/{viviendaId}/lugares/{lugarId}")
    public LugarDetailDTO addLugar(@PathVariable("viviendaId") Long viviendaId, @PathVariable("lugarId") Long lugarId) throws EntityNotFoundException {
        LugarEntity lugarEntity = viviendaLugarService.addLugar(viviendaId, lugarId);
        return modelMapper.map(lugarEntity, LugarDetailDTO.class);
    }

    @GetMapping("/{viviendaId}/lugares/{lugarId}")
    @ResponseStatus(code = HttpStatus.OK)
    public LugarDetailDTO getLugar(@PathVariable("viviendaId") Long viviendaId, @PathVariable("lugarId") Long lugarId) throws EntityNotFoundException, IllegalOperationException {
            LugarEntity lugarEntity= viviendaLugarService.getLugar(viviendaId, lugarId);
        return modelMapper.map(lugarEntity, LugarDetailDTO.class);
    }

    @GetMapping("/{viviendaId}/lugares")
    @ResponseStatus(code = HttpStatus.OK)
    public List<LugarEntity> getLugares(@PathVariable("viviendaId") Long viviendaId) throws EntityNotFoundException {
        List<LugarEntity> lugarEntity = viviendaLugarService.getLugares(viviendaId);
        return modelMapper.map(lugarEntity,  new TypeToken<List<LugarDetailDTO>>() {
		}.getType());
    }
    

    @PutMapping("/{viviendaId}/lugares")
    public List<LugarDetailDTO> replaceLugar(@PathVariable("viviendaId") Long viviendaId, @RequestBody List<LugarDTO> lugares) throws EntityNotFoundException {

        List<LugarEntity> entities = modelMapper.map(lugares, new TypeToken<List<LugarEntity>>() {
		}.getType());

        List<LugarEntity> lugaresList = viviendaLugarService.replaceLugares(viviendaId, entities);

        return modelMapper.map(lugaresList,  new TypeToken<List<LugarDetailDTO>>() {
		}.getType());
    }

    @DeleteMapping("/{viviendaId}/lugares/{lugarId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeLugar(@PathVariable("viviendaId") Long viviendaId, @PathVariable("lugarId") Long lugarId) throws EntityNotFoundException{
        viviendaLugarService.removeLugar(viviendaId, lugarId);
    }

    
}
