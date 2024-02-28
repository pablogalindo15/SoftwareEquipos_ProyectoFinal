package co.edu.uniandes.dse.Vivienda.controllers;

import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.Vivienda.dto.PropietarioDTO;
import co.edu.uniandes.dse.Vivienda.entities.PropietarioEntity;
import co.edu.uniandes.dse.Vivienda.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.Vivienda.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.Vivienda.services.PropietarioService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;


// Clase que representa el recurso "Propietario" //

@RestController
@RequestMapping("/propietarios")
public class PropietarioController {
  
    @Autowired
    private PropietarioService propietarioService;

    @Autowired
    private ModelMapper modelMapper; 



@PostMapping    //Tag que vuelve el metodo un Post
@ResponseStatus(code = HttpStatus.CREATED)    // Tag que devuelve un codigo cuando todo sale bien
public PropietarioDTO post(@RequestBody PropietarioDTO propietarioDTO) throws IllegalOperationException, EntityNotFoundException{    // Tag que hace que el parametro sea el mismo de Postman
    PropietarioEntity propietarioEntity = propietarioService.createPropietario(modelMapper.map(propietarioDTO, PropietarioEntity.class));
    return modelMapper.map(propietarioEntity, PropietarioDTO.class);
}       

}
