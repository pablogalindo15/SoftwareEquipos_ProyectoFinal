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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.Vivienda.dto.ServicioDTO;
import co.edu.uniandes.dse.Vivienda.dto.ServicioDetailDTO;
import co.edu.uniandes.dse.Vivienda.entities.ServicioEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.Vivienda.services.ViviendaServicioService;

@RestController
@RequestMapping("/viviendas")
public class ViviendaServicioController {

    @Autowired
    private ViviendaServicioService viviendaServicioService;

    @Autowired
    private ModelMapper modelMapper;
    
    @PostMapping("/{viviendaId}/servicios/{servicioId}")
    public ServicioDetailDTO addServicio(@PathVariable("viviendaId") Long viviendaId, @PathVariable("servicioId") Long servicioId) throws EntityNotFoundException {
        ServicioEntity servicioEntity = viviendaServicioService.addServicio(viviendaId, servicioId);
        return modelMapper.map(servicioEntity, ServicioDetailDTO.class);
    }

    @GetMapping("/{viviendaId}/servicios/{servicioId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ServicioDetailDTO getServicio(@PathVariable("viviendaId") Long viviendaId, @PathVariable("servicioId") Long servicioId) throws EntityNotFoundException, IllegalOperationException {
            ServicioEntity servicioEntity= viviendaServicioService.getServicio(viviendaId, servicioId);
        return modelMapper.map(servicioEntity, ServicioDetailDTO.class);
    }

    @GetMapping("/{viviendaId}/servicios")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ServicioEntity> getServicios(@PathVariable("viviendaId") Long viviendaId) throws EntityNotFoundException {
        List<ServicioEntity> servicioEntity = viviendaServicioService.getServicios(viviendaId);
        return modelMapper.map(servicioEntity,  new TypeToken<List<ServicioDetailDTO>>() {
		}.getType());
    }

    
    @PutMapping("/{viviendaId}/servicios")
    public List<ServicioDetailDTO> replaceServicios(@PathVariable("viviendaId") Long viviendaId, @RequestBody List<ServicioDTO> servicios) throws EntityNotFoundException {

        List<ServicioEntity> entities = modelMapper.map(servicios, new TypeToken<List<ServicioEntity>>() {
		}.getType());

        List<ServicioEntity> serviciosList = viviendaServicioService.replaceServicios(viviendaId, entities);

        return modelMapper.map(serviciosList,  new TypeToken<List<ServicioDetailDTO>>() {
		}.getType());
    }
    
    @DeleteMapping("/{viviendaId}/servicios/{servicioId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeServicio(@PathVariable("viviendaId") Long viviendaId, @PathVariable("servicioId") Long servicioId) throws EntityNotFoundException{
        viviendaServicioService.removeServicio(viviendaId, servicioId);
    }

}
