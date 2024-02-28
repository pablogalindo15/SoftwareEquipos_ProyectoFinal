package co.edu.uniandes.dse.Vivienda.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.Vivienda.dto.LugarDTO;
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


    
}
