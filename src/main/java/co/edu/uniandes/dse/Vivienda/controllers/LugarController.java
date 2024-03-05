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


import co.edu.uniandes.dse.Vivienda.dto.LugarDTO;
import co.edu.uniandes.dse.Vivienda.dto.LugarDetailDTO;
import co.edu.uniandes.dse.Vivienda.entities.LugarEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.Vivienda.services.LugarService;

@RestController
@RequestMapping("/lugares")
public class LugarController {
    @Autowired
	private LugarService lugarService;

	@Autowired
	private ModelMapper modelMapper;

    @PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public LugarDTO create(@RequestBody LugarDTO lugarDTO) throws IllegalOperationException,EntityNotFoundException {
		LugarEntity lugarEntity = lugarService.createLugar(modelMapper.map(lugarDTO, LugarEntity.class));
		return modelMapper.map(lugarEntity, LugarDTO.class);
	}
	@GetMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public LugarDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
		LugarEntity lugarEntity = lugarService.getLugar(id);
		return modelMapper.map(lugarEntity, LugarDetailDTO.class);
	}
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<LugarDetailDTO> findAll() {
		List<LugarEntity> lugares = lugarService.getLugares();
		return modelMapper.map(lugares, new TypeToken<List<LugarDetailDTO>>() {
		}.getType());
	}

	@PutMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public LugarDTO update(@PathVariable("id") Long id, @RequestBody LugarDTO lugarDTO)
			throws EntityNotFoundException, IllegalOperationException {
		LugarEntity lugarEntity = lugarService.updateLugar(id, modelMapper.map(lugarDTO, LugarEntity.class));
		return modelMapper.map(lugarEntity, LugarDTO.class);
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
		lugarService.deleteLugar(id);
	}

	

    
}
