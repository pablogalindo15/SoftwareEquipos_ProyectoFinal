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

import co.edu.uniandes.dse.Vivienda.dto.ViviendaDTO;
import co.edu.uniandes.dse.Vivienda.dto.ViviendaDetailDTO;
import co.edu.uniandes.dse.Vivienda.entities.ViviendaEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.Vivienda.services.LugarViviendaService;
@RestController
@RequestMapping("/lugares")

public class LugarViviendaController {

    @Autowired
	private LugarViviendaService lugarViviendaService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping(value = "/{lugarId}/vivienda/{viviendaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ViviendaDetailDTO getBook(@PathVariable("lugarId") Long lugarId, @PathVariable("viviendaId") Long viviendaId)
			throws EntityNotFoundException, IllegalOperationException {
		ViviendaEntity viviendaEntity = lugarViviendaService.getVivienda(lugarId, viviendaId);
		return modelMapper.map(viviendaEntity, ViviendaDetailDTO.class);
	}

	@GetMapping(value = "/{lugarId}/viviendas")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ViviendaDetailDTO> getViviendas(@PathVariable("lugarId") Long lugarId) throws EntityNotFoundException {
		List<ViviendaEntity> viviendaEntity = lugarViviendaService.getViviendas(lugarId);
		return modelMapper.map(viviendaEntity, new TypeToken<List<ViviendaDetailDTO>>() {
		}.getType());
	}

	@PostMapping(value = "/{lugarId}/viviendas/{viviendaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ViviendaDetailDTO addVivienda(@PathVariable("lugarId") Long lugarId, @PathVariable("viviendaId") Long viviendaId)
			throws EntityNotFoundException {
		ViviendaEntity viviendaEntity = lugarViviendaService.addVivienda(lugarId, viviendaId);
		return modelMapper.map(viviendaEntity, ViviendaDetailDTO.class);
	}

	@PutMapping(value = "/{lugarId}/viviendas")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ViviendaDetailDTO> replaceViviendas(@PathVariable("lugarId") Long lugarId, @RequestBody List<ViviendaDTO> viviendas)
			throws EntityNotFoundException {
		List<ViviendaEntity> entities = modelMapper.map(viviendas, new TypeToken<List<ViviendaEntity>>() {
		}.getType());
		List<ViviendaEntity> booksList = lugarViviendaService.addViviendas(lugarId, entities);
		return modelMapper.map(booksList, new TypeToken<List<ViviendaDetailDTO>>() {
		}.getType());

	}

	@DeleteMapping(value = "/{lugarId}/viviendas/{viviendaId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removeBook(@PathVariable("lugarId") Long lugarId, @PathVariable("viviendaId") Long viviendaId)
			throws EntityNotFoundException {
		lugarViviendaService.removeVivienda(lugarId, viviendaId);
	}
}