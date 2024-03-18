package co.edu.uniandes.dse.Vivienda.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.Vivienda.dto.PropietarioDTO;
import co.edu.uniandes.dse.Vivienda.dto.ViviendaDTO;
import co.edu.uniandes.dse.Vivienda.dto.ViviendaDetailDTO;
import co.edu.uniandes.dse.Vivienda.entities.PropietarioEntity;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.Vivienda.services.PropietarioViviendaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/propietarios")
public class PropietarioViviendaController {

    @Autowired
    private PropietarioViviendaService propietarioViviendaService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "/{propietarioId}/viviendas/{viviendaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public PropietarioDTO addVivienda(@PathVariable("viviendaId") Long propietarioId, @PathVariable("viviendaId") Long viviendaId)
			throws EntityNotFoundException {
		PropietarioEntity propietarioEntity = propietarioViviendaService.addViviendaToPropietario(viviendaId, propietarioId);
		return modelMapper.map(propietarioEntity, PropietarioDTO.class);
	}

	@GetMapping(value = "/{propietarioId}/viviendas")
	public String getViviendas(@PathVariable("propietarioId") Long propietarioId) throws EntityNotFoundException {
		List<ViviendaEntity> viviendaList = propietarioViviendaService.getViviendas(propietarioId);
		return modelMapper.map(viviendaList, new TypeToken<List<ViviendaDetailDTO>>() {
		}.getType());
	}

	@GetMapping(value = "/{propietarioId}/viviendas/{viviendaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ViviendaDetailDTO getVivienda(@PathVariable("propietarioId") Long propietarioId, @PathVariable("viviendaId") Long viviendaId)
			throws EntityNotFoundException, IllegalOperationException {
		ViviendaEntity viviendaEntity = propietarioViviendaService.getVivienda(propietarioId, viviendaId);
		return modelMapper.map(viviendaEntity, ViviendaDetailDTO.class);
	}

	@PutMapping(value = "/{propietarioId}/viviendas")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ViviendaDetailDTO> replaceBooks(@PathVariable("propietarioId") Long propietariosId,
			@RequestBody List<ViviendaDetailDTO> viviendas) throws EntityNotFoundException {
		List<ViviendaEntity> viviendasList = modelMapper.map(viviendas, new TypeToken<List<ViviendaEntity>>() {
		}.getType());
		List<ViviendaEntity> result = propietarioViviendaService.replaceViviendas(propietariosId, viviendasList);
		return modelMapper.map(result, new TypeToken<List<ViviendaDetailDTO>>() {
		}.getType());
	}
	
}
