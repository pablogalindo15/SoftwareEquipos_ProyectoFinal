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
import co.edu.uniandes.dse.Vivienda.services.ServicioViviendaService;
import co.edu.uniandes.dse.Vivienda.dto.ViviendaDetailDTO;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;

@RestController
@RequestMapping("/servicios")

public class ServicioViviendaController {

    @Autowired
    private ServicioViviendaService servicioViviendaService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/{servicioId}/viviendas/{viviendaId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ViviendaDetailDTO getVivienda(@PathVariable("servicioId") Long servicioId, @PathVariable("viviendaId") Long viviendaId)
            throws EntityNotFoundException, IllegalOperationException {
        ViviendaEntity viviendaEntity = servicioViviendaService.getVivienda(servicioId, viviendaId);
        return modelMapper.map(viviendaEntity, ViviendaDetailDTO.class);
    }

    @GetMapping(value = "/{servicioId}/viviendas")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ViviendaDetailDTO> getViviendas(@PathVariable("servicioId") Long servicioId) throws EntityNotFoundException {
        List<ViviendaEntity> viviendaEntity = servicioViviendaService.getViviendas(servicioId);
        return modelMapper.map(viviendaEntity, new TypeToken<List<ViviendaDetailDTO>>() {
        }.getType());
    }

    @PostMapping(value = "/{servicioId}/viviendas/{viviendaId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ViviendaDetailDTO addVivienda(@PathVariable("servicioId") Long servicioId, @PathVariable("viviendaId") Long viviendaId)
            throws EntityNotFoundException {
        ViviendaEntity viviendaEntity = servicioViviendaService.addVivienda(servicioId, viviendaId);
        return modelMapper.map(viviendaEntity, ViviendaDetailDTO.class);
    }


    @DeleteMapping(value = "/{servicioId}/viviendas/{viviendaId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeVivienda(@PathVariable("servicioId") Long servicioId, @PathVariable("viviendaId") Long viviendaId)
            throws EntityNotFoundException {
        servicioViviendaService.removeVivienda(servicioId, viviendaId);
    }
}

